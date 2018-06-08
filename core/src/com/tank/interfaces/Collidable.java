/**
 * @author The Church of Daniel Pham
 * Description:
 * This interface provides an outline of objects that collide
 * in TankInifinity, notably tanks and projectiles, as well as
 * a basic framework to deal with collisions.
 */
package com.tank.interfaces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;

public interface Collidable {
	/**
	 * Compares the hitboxes of this instance with each hitbox in the given array
	 * and creates CollisionEvents for each collision detected
	 * 
	 * @param other
	 *            the array of other Collidables (which have hitboxes) to compare
	 *            with this instance
	 */
	public void checkCollisions(ArrayList<Collidable> other);

	/**
	 * 
	 * @param x
	 *            the x coordinate, in pixels
	 * @param y
	 *            the y coordinate, in pixels
	 * @param direction
	 *            the direction the Collidable faces, in degrees
	 * @return a rectangle whose screen coordinates represents the Collidable's
	 *         hitbox, which will be used to determine collisions
	 */
	public Polygon getHitboxAt(float x, float y, float direction);

	/**
	 * 
	 * @return the hitbox of the Collidable
	 */
	public Polygon getHitbox();

	/**
	 * Typically used as a parameter in checkCollisions(...)
	 * 
	 * @return The Collidables that this Collidable may collide with and thus need
	 *         to be checked
	 */
	public ArrayList<Collidable> getNeighbors();
/**
 * Used for debugging
 * @param batch
 * @param a
 */
	public void drawVertices(Batch batch, float a);
}
