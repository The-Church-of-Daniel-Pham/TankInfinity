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
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.interfaces.Destructible;
import com.tank.stats.Stats;

public abstract class AbstractProjectile extends Actor implements Collidable, Destructible {
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
	public AbstractProjectile(Texture t, Stats stat, AbstractVehicle src, float x, float y) {
		tex = t;
		stats = stat;
		source = src;
		setX(x);
		setY(y);
	}

	/**
	 * The act method is shared by all Actors. It tells what the actor is going to
	 * do.
	 * 
	 * @param delta
	 *            Time since last called.
	 */
	public void act(float delta) {
		super.setX(getX() + velocity.x);
		super.setY(getY() + velocity.y);
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
		batch.draw(tex, super.getX()-super.getOriginX(), super.getY()-super.getOriginY(),super.getOriginX(),super.getOriginY(), tex.getWidth(),
				tex.getHeight(), super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, tex.getWidth(),
				tex.getHeight(), false, false);
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
	public abstract Polygon getHitbox();

	/**
	 * From the Collidable interface. The checkCollision method handles all
	 * collisions to this object. This is handled differently for each subclass
	 * 
	 * @param other
	 *            The other object this object collides with
	 */
	public abstract void checkCollision(Collidable other);

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
