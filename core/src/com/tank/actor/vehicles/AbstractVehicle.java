package com.tank.actor.vehicles;

/**
 * @author 
 * 
 * The AbstractVehicle class is the superclass of all the tanks in the game, player and enemy. Vehicles are the main
 * characters in the game, with the player tank being controllable by the players and enemy tanks having their own
 * AI.
 */
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.interfaces.Collidable;
import com.tank.interfaces.Destructible;
import com.tank.interfaces.Teamable;
import com.tank.screen.PlayScreen;
import com.tank.stats.Stats;
import com.tank.utils.CollisionEvent;

public abstract class AbstractVehicle extends Actor implements Collidable, Destructible, Teamable {
	/**
	 * used for spawning bullets the correct distance away from Vehicle's center
	 */
	public static final int TANKGUNLENGTH = 135;
	/**
	 * List of all abstract vehicles in existence
	 */
	protected static ArrayList<AbstractVehicle> vehicleList = new ArrayList<AbstractVehicle>();
	/**
	 * The health of the vehicle
	 */
	protected int health;
	/**
	 * The maxHealth of the vehicle
	 */
	protected int maxHealth;
	/**
	 * The stats of the vehicle
	 */
	protected Stats stats;
	/**
	 * The velocity of the vehicle
	 */
	protected Vector2 velocity;
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

	/**
	 * 
	 * @param x
	 *            initial x position of Vehicle
	 * @param y
	 *            initial y position of Vehicle
	 */
	public AbstractVehicle(float x, float y) {
		setX(x);
		setY(y);
		velocity = new Vector2(0, 0);
		angularVelocity = 0;
		setStats();
		vehicleList.add(this);
		collisions = new ArrayList<CollisionEvent>();
	}

	/**
	 * Set Vehicle statistics unique to each tank type
	 */
	abstract protected void setStats();

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

	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * Called each frame. Changes tank rotation/position based on the value of
	 * angularVelocity and velocity, respectively
	 * 
	 * @param delta
	 *            Time since last called.
	 */
	public void move(float delta) {
		velocity.rotate(delta * angularVelocity);
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
		setRotation(getRotation() + delta * angularVelocity);
	}

	public void applyFriction(float delta) {
		velocity.scl((float) Math.pow((100f - stats.getStatValue("Friction")) / 100f, delta));
		angularVelocity *= (float) Math.pow((100f - stats.getStatValue("Angular_Friction")) / 100f, delta);
	}

	public void applyForce(Vector2 acceleration) {
		velocity = velocity.add(acceleration);
	}

	public void applyForce(float mag, float dir) {
		Vector2 v = new Vector2(mag, 0);
		v.setAngle(dir);
		velocity.add(v);
	}

	public void applyAngularForce(float acceleration) {
		angularVelocity += acceleration;
	}

	public void applyLimitedForce(Vector2 acceleration, float lim) {
		Vector2 comp = new Vector2(1, 0);
		comp.setAngle(acceleration.angle());
		if (velocity.dot(comp) < lim) {
			applyForce(acceleration);
		}
	}

	public void applyLimitedForce(float mag, float dir, float lim) {
		Vector2 comp = new Vector2(1, 0);
		comp.setAngle(dir);
		if (velocity.dot(comp) < lim) {
			applyForce(mag, dir);
		}
	}

	public void applyLimitedAngularForce(float acceleration, float lim) {
		if (angularVelocity < lim) {
			applyAngularForce(acceleration);
		}
	}

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
	 * collisions to this object. This is handled differently for each subclass
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
			for (int i = 0; i < 4; i++) {
				// check for wall collision by checking if the corners of this instance are
				// contained within another Collidable object
				if (c.getHitbox().contains(testVertices[i * 2], testVertices[i * 2 + 1])) {
					// generate the wall associated with the collision
					Vector2 wall = CollisionEvent.getWallVector(c,
							new Vector2(hitbox.getVertices()[i * 2], hitbox.getVertices()[i * 2 + 1]),
							new Vector2(testHitbox.getVertices()[i * 2], testHitbox.getVertices()[i * 2 + 1]));
					// create new wall collision event
					collisions.add(new CollisionEvent(c, CollisionEvent.WALL_COLLISION, wall));
				}
				// check for corner collision by checking if the corners of another Collidable
				// object are contained within this instance
				if (testHitbox.contains(cTestVertices[i * 2], cTestVertices[i * 2 + 1])) {
					// create new corner collision event
					collisions.add(new CollisionEvent(c, CollisionEvent.CORNER_COLLISION,
							new Vector2(cTestVertices[i * 2], cTestVertices[i * 2 + 1])));
				}
			}
		}

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
		// get AbstractMapTiles from current row/col with radius 1
		// add instances of WallTile to neighbors
		// place all items in vehicleList into neighbors
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
		health -= damage;
		if (health <= 0 && !isDestroyed())
			destroy();
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
		health += heal;
		if (health > maxHealth)
			health = maxHealth;
	}

	/**
	 * From the Destructible interface The destroy method is used to handle removing
	 * the object from the stage
	 */
	public void destroy() {
		vehicleList.remove(this);
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
