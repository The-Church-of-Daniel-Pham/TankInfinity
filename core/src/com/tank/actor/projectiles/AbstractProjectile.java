package com.tank.actor.projectiles;

/**
 * Description: The AbstractProjectile class is the superclass that will be used for all projectiles for the game.
 * Projectiles are anything that tanks shoot or place, with the Bullet being the main projectile and various SubWeapons
 * being other projectiles. This class is extended with every projectile.
 */
import java.util.ArrayList;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.map.tiles.WallTile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.interfaces.Destructible;
import com.tank.interfaces.Teamable;
import com.tank.media.MediaSound;
import com.tank.stage.Level;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public abstract class AbstractProjectile extends Actor implements Collidable, Destructible, Teamable {
	/**
	 * List of all projectiles
	 */
	public static ArrayList<AbstractProjectile> projectileList = new ArrayList<AbstractProjectile>();
	/**
	 * The texture of the projectile.
	 */
	protected Texture tex;
	/**
	 * The velocity of the projectile, giving direction and speed
	 */
	protected Vector2 velocity;
	/**
	 * The angular velocity of the vehicle
	 */
	protected float angularVelocity;
	/**
	 * The source of the projectile, from some tank
	 */
	protected AbstractVehicle source;
	/**
	 * The stats of the projectile, which includes damage and such
	 */
	protected Stats stats;
	/**
	 * The hitbox of the Projectile's current position
	 */
	protected Polygon hitbox;
	/**
	 * The hitbox of the Projectile, presumably at a position to which the
	 * Projectile is trying to move
	 */
	protected Polygon testHitbox;
	/**
	 * stores the collision events that have been recorded in the current frame
	 */
	protected ArrayList<CollisionEvent> collisions;
	protected Texture debug = Assets.manager.get(Assets.vertex);
	private MediaSound bounce_sound;

	/**
	 * The AbstractProjectile constructor to define all the standard instance
	 * variables
	 * 
	 * @param t
	 *            The texture of the projectile
	 * @param stat
	 *            The stat of the projectile
	 * @param src
	 *            The source the projectile came from
	 * @param x
	 *            The starting x of the projectile
	 * @param y
	 *            The starting y of the projectile
	 * @param bounce
	 *            The sound for the bounce
	 * @param BOUNCE_VOLUME
	 *            The volume of the bounce
	 */
	public AbstractProjectile(Texture t, AbstractVehicle src, Stats stat, float x, float y, Sound bounce,
			final float BOUNCE_VOLUME) {
		tex = t;
		source = src;
		stats = stat;
		setX(x);
		setY(y);
		initializeHitbox();
		collisions = new ArrayList<CollisionEvent>();
		projectileList.add(this);
		bounce_sound = new MediaSound(bounce, BOUNCE_VOLUME);
	}

	protected abstract void initializeHitbox();

	/**
	 * The act method is shared by all Actors. It tells what the actor is going to
	 * do.
	 * 
	 * @param delta
	 *            Time since last called.
	 */
	@Override
	public void act(float delta) {
		if (!isDestroyed())
			move(delta);
	}

	public void move(float delta) {
		float tAngle = getRotation() + delta * angularVelocity;
		float tX = getX() + velocity.x * delta;
		float tY = getY() + velocity.y * delta;
		if (canMoveTo(tX, tY, tAngle)) {
			velocity.rotate(tAngle - getRotation());
			setRotation(tAngle);
			super.setPosition(tX, tY);
			hitbox = testHitbox;
		} else {
			// ArrayList<Collidable> objectsHit = new ArrayList<Collidable>();
			CollisionEvent cornerE = null; // use later if needed
			int numCornersHit = 0;
			int numWallsHit = 0;
			for (CollisionEvent e : collisions) {
				if (e.getCollidable() instanceof Bullet) {
					((Bullet) e.getCollidable()).destroy();
					destroy();
					break;
				}
				if (e.getCollisionType() == CollisionEvent.CORNER_COLLISION) {
					numCornersHit++;
					cornerE = e; // let's face it: since we're not colliding with another bullet, there's no way
									// we're hitting more than one corner of a tank/tile as a bullet
				} else {
					numWallsHit++;
				}
				// if (!objectsHit.contains(e.getCollidable())) {
				// objectsHit.add(e.getCollidable());
				// }
			}
			if (numCornersHit == 0) { // most likely case; good ol wall collision
				Vector2 wall = collisions.get(0).getWall();
				if (wall != null)
					bounce(collisions.get(0).getWall());
				else// bullet inside tank/tile... can't escape anyways
					destroy();
			} else if (numWallsHit > 0) {// corner to corner
				bounce(velocity.cpy().rotate(90)); // move backwards
			} else { // head on corner or side corner graze
				float[] f = testHitbox.getVertices();
				if (cornerE.getWall().isCollinear(new Vector2(f[0] - f[6], f[1] - f[7]))) { // head on
					bounce(velocity.cpy().rotate(90)); // move backwards
				} else { // graze, use different algorithm to find wall
					bounce(CollisionEvent.getWallVector(cornerE.getCollidable().getHitbox(),
							cornerE.getCorner().sub(velocity.cpy().scl(delta))));
				}
			}
		}
	}

	public boolean canMoveTo(float x, float y, float orientation) {
		testHitbox = getHitboxAt(x, y, orientation);
		checkCollisions(getNeighbors());
		return collisions.size() == 0;
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
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), tex.getWidth(), tex.getHeight(), super.getScaleX(), super.getScaleY(),
				super.getRotation(), 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}

	public void drawVertices(Batch batch, float a) {
		for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
			batch.draw(debug, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1], 0, 0,
					debug.getWidth(), debug.getHeight(), 1, 1, 0, 0, 0, debug.getWidth(), debug.getHeight(), false,
					false);
		}
	}

	/**
	 * The getVelocity method returns the velocity of the projectile
	 * 
	 * @return The velocity of the projectile as a Vector2
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * The updateVelocityAndMove method is called the the act method that updates
	 * the velocity every call.
	 */
	public void updateVelocityAndMove() {

	}

	/**
	 * The applyForce method is used to instantly change the velocity of the
	 * projectile
	 * 
	 * @param acceleration
	 *            The force to add to the velocity
	 */
	public void applyForce(Vector2 acceleration) {
		velocity.add(acceleration);
	}

	/**
	 * The applyForce method is used to instantly change the velocity of the
	 * projectile
	 * 
	 * @param mag
	 *            The magnitude of the force
	 * @param dir
	 *            The direction of the force
	 */
	public void applyForce(float mag, float dir) {

	}

	/**
	 * The bounce method is used to deflect the direction of the projectile when it
	 * bounces off a wall/tank
	 * 
	 * @param wall
	 *            The vector of the wall
	 */
	public void bounce(Vector2 wall) {
		velocity.rotateRad(2 * velocity.angleRad(wall)); // rotate by double to angle that the bullet forms, relative to
		// the wall

		bounce_sound.play();

		super.setRotation(velocity.angle()); // update rotation

	}

	/**
	 * The getStat method is used to get a certain stat of the projectile
	 * 
	 * @param stat
	 *            The stat to look for
	 * @return The value of the stat if there is one
	 */
	public int getStat(String stat) {
		return stats.getStatValue(stat);
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
	 * collisions to this object. This is handled differently for each subclass Uses
	 * testHitbox to check collisions.
	 * 
	 * @param other
	 *            The other object this object collides with
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
				}
				// check for corner collision by checking if the corners of another Collidable
				// object are contained within this instance
				if (testHitbox.contains(cTestVertices[i * 2], cTestVertices[i * 2 + 1])) {
					// create new corner collision event
					Vector2 wall = CollisionEvent.getWallVector(c.getHitbox(), testHitbox, i * 2);
					collisions.add(new CollisionEvent(c, CollisionEvent.CORNER_COLLISION, wall,
							new Vector2(cTestVertices[i * 2], cTestVertices[i * 2 + 1])));
				}
			}
		}
	}

	public ArrayList<Collidable> getNeighbors() {
		// get neighboring bricks. instances of WallTile get added to neighbors
		// add all vehicles not on team to neighbors
		// add all bullets not on team to neighbors, then remove this instance
		ArrayList<Collidable> neighbors = new ArrayList<Collidable>();
		int[] gridCoords = ((Level) getStage()).getMap().getTileAt(getX(), getY());
		ArrayList<AbstractMapTile> a = ((Level) getStage()).getMap().getWallNeighbors(gridCoords[0], gridCoords[1]);
		for (AbstractMapTile m : a) {
			if (m instanceof WallTile) {
				neighbors.add((WallTile) m);
			}
		}
		for (AbstractVehicle v : AbstractVehicle.vehicleList) {
			boolean canCollide = !(v.getTeam() != null && getTeam() != null && getTeam().equals(v.getTeam()));
			if (canCollide)
				neighbors.add(v);
		}
		for (AbstractProjectile p : AbstractProjectile.projectileList) {
			boolean canCollide = !(p.getTeam() != null && getTeam() != null && getTeam().equals(p.getTeam()));
			if (canCollide)
				neighbors.add(p);
		}
		neighbors.remove(this);
		return neighbors;
	}

	public Polygon getHitbox() {
		return hitbox;
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

	}

	/**
	 * From the Destructible interface The destroy method is used to handle removing
	 * the object from the stage
	 */
	public void destroy() {
		projectileList.remove(this);
		super.remove();
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

	public String getTeam() {
		if (source != null)
			return source.getTeam();
		else
			return null;
	}
}
