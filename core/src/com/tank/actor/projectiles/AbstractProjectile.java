package com.tank.actor.projectiles;

/**
 * Description: The AbstractProjectile class is the superclass that will be used for all projectiles for the game.
 * Projectiles are anything that tanks shoot or place, with the Bullet being the main projectile and various SubWeapons
 * being other projectiles. This class is extended with every projectile.
 */
import java.util.ArrayList;

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
import com.tank.stage.Level;
import com.tank.stats.Stats;
import com.tank.utils.CollisionEvent;

public abstract class AbstractProjectile extends Actor implements Collidable, Destructible {
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
	 * The hitbox of the Projectile, presumably at a position to which the Projectile is
	 * trying to move
	 */
	protected Polygon testHitbox;
	/**
	 * stores the collision events that have been recorded in the current frame
	 */
	protected ArrayList<CollisionEvent> collisions;
	
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
	 */
	public AbstractProjectile(Texture t, AbstractVehicle src, float x, float y) {
		tex = t;
		source = src;
		setX(x);
		setY(y);
		setStats();
		collisions = new ArrayList<CollisionEvent>();
		projectileList.add(this);
	}
	abstract protected void setStats();
	/**
	 * The act method is shared by all Actors. It tells what the actor is going to
	 * do.
	 * 
	 * @param delta
	 *            Time since last called.
	 */
	public void act(float delta) {
		super.setX(getX() + delta * velocity.x);
		super.setY(getY() + delta * velocity.y);
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
	 * collisions to this object. This is handled differently for each subclass
	 * Uses testHitbox to check collisions.
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

	public ArrayList<Collidable> getNeighbors() {
		//get neighboring bricks. instances of  WallTile get added to neighbors
		//add all vehicles to neighbors
		//add all bullets to neighbors, then remove this instance
		ArrayList<Collidable> neighbors = new ArrayList<Collidable>();
		int[] gridCoords = ((Level)getStage()).getMap().getTileAt(getX(), getY());
		ArrayList<AbstractMapTile> a =((Level)getStage()).getMap().getBrickNeighbors(gridCoords[0], gridCoords[1]);
		for(AbstractMapTile m: a) {
			if(m instanceof WallTile) {
				neighbors.add((WallTile)m);
			}
		}
		neighbors.addAll(AbstractVehicle.vehicleList);
		neighbors.addAll(AbstractProjectile.projectileList);
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
		this.remove();
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
}
