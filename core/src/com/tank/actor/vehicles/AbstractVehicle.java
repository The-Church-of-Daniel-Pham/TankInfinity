package com.tank.actor.vehicles;

/**
 * @author 
 * 
 * The AbstractVehicle class is the superclass of all the tanks in the game, player and enemy. Vehicles are the main
 * characters in the game, with the player tank being controllable by the players and enemy tanks having their own
 * AI.
 */
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.map.tiles.WallTile;
import com.tank.actor.ui.MovingText;
import com.tank.animations.DeathExplosion;
import com.tank.interfaces.Collidable;
import com.tank.interfaces.Destructible;
import com.tank.interfaces.Teamable;
import com.tank.media.MediaSound;
import com.tank.stage.Level;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public abstract class AbstractVehicle extends Actor implements Collidable, Destructible, Teamable {
	/**
	 * List of all abstract vehicles in existence
	 */
	public static ArrayList<AbstractVehicle> vehicleList = new ArrayList<AbstractVehicle>();
	/**
	 * The health of the vehicle
	 */
	protected int health;
	/**
	 * The maxHealth of the vehicle
	 */
	//protected int maxHealth;
	/**
	 * The stats of the vehicle
	 */
	protected Stats stats;
	/**
	 * The velocity of the vehicle
	 */
	protected Vector2 velocity;
	/**
	 * The secondary velocity of the vehicle (like knockback)
	 */
	protected Vector2 secondaryVelocity;
	/**
	 * The angular velocity of the vehicle
	 */
	protected float angularVelocity;
	/**
	 * The hitbox of the Vehicle's current position
	 */
	protected Polygon hitbox;
	/**
	 * The hitbox of the Vehicle, presumably at a position to which the Vehicle is
	 * trying to move
	 */
	protected Polygon testHitbox;
	/**
	 * stores the collision events that have been recorded in the current frame
	 */
	protected ArrayList<CollisionEvent> collisions;
	protected Texture debug = Assets.manager.get(Assets.vertex);
	private MediaSound damageSound;
	private static float DAMAGE_VOLUME = 1f;
	
	protected int bulletCount;
	protected float speedModifier;
	protected float mass;

	/**
	 * 
	 * @param x
	 *            initial x position of Vehicle
	 * @param y
	 *            initial y position of Vehicle
	 */
	public AbstractVehicle(float x, float y) {
		damageSound = new MediaSound(Assets.manager.get(Assets.tank_damage), DAMAGE_VOLUME);
		setX(x);
		setY(y);
		stats = new Stats();
		makeBaselineStats();
		velocity = new Vector2(0, 0);
		secondaryVelocity = new Vector2(0, 0);
		angularVelocity = 0;
		vehicleList.add(this);
		bulletCount = 0;
		collisions = new ArrayList<CollisionEvent>();
		speedModifier = 1.0f;
		mass = 200;
	}
	
	public void makeBaselineStats() {
		stats.addStat("Damage", 35);
		stats.addStat("Spread", 40);
		stats.addStat("Accuracy", 50);
		stats.addStat("Stability", 50);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 75);
		stats.addStat("Lifetime", 60);
		stats.addStat("Fire Rate", 30);
		stats.addStat("Max Projectile", 2);

		stats.addStat("Max Health", 100);
		health = 100;
		stats.addStat("Armor", 15);

		stats.addStat("Traction", 100);
		stats.addStat("Acceleration", 120);
		stats.addStat("Angular Acceleration", 120);

		stats.addStat("Projectile Durability", 1);
	}

	protected abstract void initializeHitbox();

	/**
	 * Set Vehicle statistics unique to each tank type
	 */
	public void setStat(String stat, int val) {
		stats.addStat(stat, val);
	}

	public int getStatValue(String stat) {
		return stats.getStatValue(stat);
	}

	/**
	 * The act method is shared by all Actors. It tells what the actor is going to
	 * do.
	 * 
	 * @param delta
	 *            Time since last called.
	 */
	public void act(float delta) {
		move(delta);
	}

	/**
	 * The draw method is shared by all Actors. This is called to draw the actor
	 * onto the stage.
	 * 
	 * @param batch
	 *            The object used to draw the textures and objects onto the screen
	 * @param a
	 *            The "transparency" of the object
	 */
	public void draw(Batch batch, float a) {

	}

	public void drawVertices(Batch batch, float a) {
		for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
			batch.draw(debug, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1], 0, 0,
					debug.getWidth(), debug.getHeight(), 1, 1, 0, 0, 0, debug.getWidth(), debug.getHeight(), false,
					false);
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public float getAngularVelocity() {
		return angularVelocity;
	}
	
	public Vector2 getSecondaryVelocity() {
		return secondaryVelocity;
	}
	
	public Vector2 getTotalVelocity() {
		return velocity.cpy().add(secondaryVelocity);
	}

	/**
	 * Called each frame. Changes tank rotation/position based on the value of
	 * angularVelocity and velocity, respectively
	 * 
	 * @param delta
	 *            Time since last called.
	 */
	public boolean move(float delta) {
		if (isDestroyed())
			return false;
		float tAngle = getRotation() + delta * angularVelocity;
		if (secondaryVelocity.len2() < 400f) {
			secondaryVelocity.x = 0;
			secondaryVelocity.y = 0;
		}
		float tX = getX() + (velocity.x + secondaryVelocity.x) * delta;
		float tY = getY() + (velocity.y + secondaryVelocity.y) * delta;
		boolean moved = false;

		// translation
		if (velocity.cpy().add(secondaryVelocity).len() > 15) {
			if (canMoveTo(tX, tY, getRotation())) {
				super.setPosition(tX, tY);
				hitbox = testHitbox;
				moved = true;
			} else if (canMoveTo(tX, getY(), getRotation())) {
				super.setX(tX);
				if (Math.abs((velocity.x + secondaryVelocity.x) * 30.0) > Math.abs(velocity.y + secondaryVelocity.y)) moved = true;
				hitbox = testHitbox;
			} else if (canMoveTo(getX(), tY, getRotation())) {
				super.setY(tY);
				if (Math.abs((velocity.y + secondaryVelocity.y) * 30.0) > Math.abs(velocity.x + secondaryVelocity.x)) moved = true;
				hitbox = testHitbox;
			} else {
				velocity.scl((float) Math.pow(0.01f, delta));
			}
		}

		// rotation
		if (Math.abs(angularVelocity) > 4) {
			if (canMoveTo(getX(), getY(), tAngle)) {
				velocity.rotate(tAngle - getRotation());
				setRotation(tAngle);
				hitbox = testHitbox;
				moved = true;
			} else {
				angularVelocity *= (float) Math.pow(0.01f, delta);
			}
		}
		return moved;
	}

	public boolean canMoveTo(float x, float y, float orientation) {
		testHitbox = getHitboxAt(x, y, orientation);
		checkCollisions(getNeighbors());
		return collisions.size() == 0;
	}

	public void applyFriction(float delta) {
		float traction = stats.getStatValue("Traction");
		velocity.scl((float) Math.pow(0.05f * (1.0f - ((traction) / (traction + 70.0f))), delta));
		secondaryVelocity.scl((float) Math.pow(0.1f * (1.0f - ((traction) / (traction + 70.0f))), delta));
		angularVelocity *= (float) Math.pow(0.025f * (1.0f - ((traction) / (traction + 70.0f))), delta);
		
		if (speedModifier > 1.0f) speedModifier = Math.max(speedModifier - 0.05f * delta, 1.0f);
		else if (speedModifier < 1.0f) speedModifier = Math.min(speedModifier + 0.05f * delta, 1.0f);
	}

	public void applyForce(Vector2 acceleration) {
		velocity = velocity.add(acceleration);
	}

	public void applyForce(float mag, float dir) {
		Vector2 v = new Vector2(mag, 0);
		v.setAngle(dir);
		velocity.add(v);
	}
	
	public void applySecondaryForce(Vector2 acceleration) {
		secondaryVelocity = secondaryVelocity.add(acceleration);
	}

	public void applySecondaryForce(float mag, float dir) {
		Vector2 v = new Vector2(mag, 0);
		v.setAngle(dir);
		secondaryVelocity.add(v);
	}

	public void applyAngularForce(float acceleration) {
		angularVelocity += acceleration;
	}

	/**
	 * From the Collidable interface. The getHitbox method is used to get the
	 * polygons for the collision of the object. This will be overwritten by
	 * subclasses.
	 * 
	 * @return The hitbox(s) of the projectile
	 */
	public abstract Polygon getHitboxAt(float x, float y, float direction);

	/**
	 * From the Collidable interface. The checkCollision method handles all
	 * collisions to this object. This is handled differently for each subclass.
	 * Uses testHitbox to check collisions.
	 * 
	 * @param other
	 *            The other objects this object may collide with
	 */
	public void checkCollisions(ArrayList<Collidable> other) {
		collisions.clear(); // remove collisions calculated from a different frame
		float[] testVertices = testHitbox.getVertices(); // vertices of this instance's hitbox
		// check each Collidable object against this instance
		for (Collidable c : other) {
			float[] cTestVertices = c.getHitbox().getVertices(); // vertices of a Collidable object that may collide
																	// with this instance
			for (int i = 0; i < testVertices.length / 2; i++) {
				// check for wall collision by checking if the corners of this instance are
				// contained within another Collidable object
				if (c.getHitbox().contains(testVertices[i * 2], testVertices[i * 2 + 1])) {
					// generate the wall associated with the collision
					Vector2 wall = CollisionEvent.getWallVector(testHitbox, c.getHitbox(), i * 2);
					// create new wall collision event
					collisions.add(new CollisionEvent(c, CollisionEvent.WALL_COLLISION, wall,
							new Vector2(testVertices[i * 2], testVertices[i * 2 + 1])));
					if (c instanceof AbstractVehicle) {
						((AbstractVehicle)c).applySecondaryForce(getTotalVelocity().scl(
								(mass / ((AbstractVehicle)c).getMass()) * 0.15f
								));
						velocity.scl(1.0f - (mass / ((AbstractVehicle)c).getMass()) * 0.15f);
						secondaryVelocity.scl(1.0f - (mass / ((AbstractVehicle)c).getMass()) * 0.15f);
					}
					break;
				}
				// check for corner collision by checking if the corners of another Collidable
				// object are contained within this instance
				if (testHitbox.contains(cTestVertices[i * 2], cTestVertices[i * 2 + 1])) {
					// create new corner collision event
					Vector2 wall = CollisionEvent.getWallVector(c.getHitbox(), testHitbox, i * 2);
					collisions.add(new CollisionEvent(c, CollisionEvent.CORNER_COLLISION, wall,
							new Vector2(cTestVertices[i * 2], cTestVertices[i * 2 + 1])));
					if (c instanceof AbstractVehicle) {
						((AbstractVehicle)c).applySecondaryForce(getTotalVelocity().scl(
								(mass / ((AbstractVehicle)c).getMass()) * 0.15f
								));
						velocity.scl(1.0f - (mass / ((AbstractVehicle)c).getMass()) * 0.15f);
						secondaryVelocity.scl(1.0f - (mass / ((AbstractVehicle)c).getMass()) * 0.15f);
					}
					break;
				}
			}
		}
	}
	
	public void accelerateForward(float delta) {
		applyForce(delta * (float)Math.pow(stats.getStatValue("Acceleration"), 0.3) * speedModifier  * 330f, getRotation());
	}
	
	public void accelerateBackward(float delta) {
		applyForce(delta * (float)Math.pow(stats.getStatValue("Acceleration"), 0.3) * speedModifier  * 330f, getRotation() + 180);
	}
	
	public void turnLeft(float delta) {
		applyAngularForce(delta * stats.getStatValue("Angular Acceleration") * speedModifier  * 2.5f);
	}
	
	public void turnRight(float delta) {
		applyAngularForce(-1 * delta * stats.getStatValue("Angular Acceleration") * speedModifier  * 2.5f);
	}
	
	public void applySlow(float amount) {
		speedModifier -= amount;
		if (speedModifier < 0.025f) speedModifier = 0.025f;
	}
	
	public void applySpeedUp(float amount) {
		speedModifier += amount;
	}
	
	public float getMass() {
		return mass;
	}
	
	public float randomShootAngle() {
		float spreadRange = 30f * (1.0f - (stats.getStatValue("Spread") / (stats.getStatValue("Spread") + 100.0f)));
		double accuracy = 1.0 + 0.125 * Math.sqrt(stats.getStatValue("Accuracy"));
		float velocityLength = getTotalVelocity().len();
		System.out.println(velocityLength);
		accuracy *= 1.0 - (velocityLength / (velocityLength + (stats.getStatValue("Stability") * 8.0)));
		spreadRange *= (((stats.getStatValue("Stability") * 8.0) + velocityLength) / (stats.getStatValue("Stability") * 8.0));
		if (accuracy < 0.5) accuracy = 0.5;
		if (spreadRange > 50f) spreadRange = 50f;
		float randomAngle = spreadRange * (float)Math.pow(Math.random(), accuracy);
		if (Math.random() < 0.5) randomAngle *= -1;
		return randomAngle;
	}
	
	public Stats createBulletStats() {
		Stats bulletStats = new Stats();
		bulletStats.addStat("Damage", stats.getStatValue("Damage"));
		bulletStats.addStat("Projectile Speed", (int)(75 * Math.sqrt(stats.getStatValue("Projectile Speed"))));
		bulletStats.addStat("Projectile Durability", stats.getStatValue("Projectile Durability"));
		bulletStats.addStat("Max Bounce", stats.getStatValue("Max Bounce"));
		bulletStats.addStat("Lifetime", stats.getStatValue("Lifetime"));
		return bulletStats;
	}
	
	public void changeBulletCount(int change) {
		bulletCount += change;
		if (bulletCount < 0) bulletCount = 0;
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	/**
	 * From the Colllidable interface. Returns and ArrayList of nearby bricks and
	 * all other Collidable non-MapTile instances
	 */
	public ArrayList<Collidable> getNeighbors() {
		ArrayList<Collidable> neighbors = new ArrayList<Collidable>();
		int[] gridCoords = ((Level) getStage()).getMap().getTileAt(getX(), getY());
		ArrayList<WallTile> a = ((Level) getStage()).getMap().getWallNeighbors(gridCoords[0], gridCoords[1]);
		neighbors.addAll(a);
		neighbors.addAll(vehicleList);
		neighbors.remove(this);
		// get AbstractMapTiles from current row/col with radius 1
		// add instances of WallTile to neighbors
		// place all items in vehicleList into neighbors, them remove this instance from
		// neighbors
		// don't worry about bullet collisions; the bullet itself worries about tanks
		return neighbors;
	}

	/**
	 * From the Destructible interface. The damage method is used to handle the
	 * object getting hit.
	 * 
	 * @param source
	 *            The other actor that hit this object, in any
	 * @param damage
	 *            The damage dealt
	 */
	public void damage(Actor source, int damage) {
		if (damage > 0) {
			damage =  Math.max(1, damage - (int)Math.pow(getStatValue("Armor"), 0.8));
			health -= damage;
			if (getStage() != null)
				getStage().addActor(new MovingText("-" + damage, Color.RED, 1.5f, new Vector2(0, 200),
						getX() + (float)(100f * Math.random()) - 50f,
						getY() + (float)(100f * Math.random()) - 50f));
			if (health <= 0 && !isDestroyed())
				destroy();
		}
	}

	/**
	 * From the Destructible interface The heal method is used to handle the object
	 * getting restored.
	 * 
	 * @param source
	 *            The other actor that healed this object, if any
	 * @param heal
	 *            The amount healed
	 */
	public void heal(Actor source, int heal) {
		int maxHealth = stats.getStatValue("Max Health");
		heal = Math.min(heal, maxHealth - health);
		health += heal;
		if (getStage() != null)
			getStage().addActor(new MovingText("+" + heal, Color.GREEN, 1.5f, new Vector2(0, 200),
					getX() + (float)(100f * Math.random()) - 50f,
					getY() + (float)(100f * Math.random()) - 50f));
	}

	public int getMaxHealth() {
		return stats.getStatValue("Max Health");
	}

	public int getHealth() {
		return health;
	}

	/**
	 * From the Destructible interface The destroy method is used to handle removing
	 * the object from the stage
	 */
	public void destroy() {
		//damageSound.play();
		getStage().addActor(new DeathExplosion(getX(), getY()));
		vehicleList.remove(this);
		remove();
	}

	/**
	 * From the Destructible interface The isDestroyed method is used to tell if the
	 * object is destroyed or not
	 * 
	 * @return true if destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return (getStage() == null);
	}

	/**
	 * From the Teamable interface The getTeam method is used to tell what team the
	 * object is in to tell if they can harm each other.
	 * 
	 * @return The team the object is in
	 */
	public String getTeam() {
		return null;
	}
}
