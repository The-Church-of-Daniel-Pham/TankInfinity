package com.tank.utils;

import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.interfaces.Collidable;

public class CollisionEvent {
	public static final int WALL_COLLISION = 0;
	public static final int CORNER_COLLISION = 1;
	private Vector2 collisionVector;
	private int collisionType;
	private Collidable collidable;

	/**
	 * Encapsulates useful information during a collision
	 * 
	 * @param victim
	 *            the object that was hit
	 * @param type
	 *            whether its wall or corner was hit
	 * @param v
	 *            the vector of the wall, or the coordinates of the corner
	 */
	public CollisionEvent(Collidable victim, int type, Vector2 v) {
		collidable = victim;
		collisionType = type;
		collisionVector = v;
	}

	/**
	 * 
	 * @return depending on collision type, returns the vector of the wall hit or
	 *         the coordinates of the corner hit
	 */
	public Vector2 getCollisionVector() {
		return collisionVector;
	}

	/**
	 * 
	 * @return the collision type; 0 for wall; 1 for corner (use provided constants)
	 */
	public int getCollisionType() {
		return collisionType;
	}

	/**
	 * 
	 * @return the Collidable object that was hit
	 */
	public Collidable getCollidable() {
		return collidable;
	}

	/**
	 * Precondition: x and y are not inside c
	 * 
	 * @param c
	 *            The object whose wall collides with the corner of another. The
	 *            corner is given by (x, y)
	 * @param x
	 *            the x-coordinate of the corner before it collides
	 * @param y
	 *            the y-coordinate of the corner before it collides
	 * @return The Vector2 which represents the wall on 'c' that was hit
	 */
	public static Vector2 getWallVector(Collidable c, float x, float y) {
		float[] h = c.getHitbox().getVertices();
		if (c instanceof AbstractMapTile) {
			if (y <= h[7] && y >= h[1]) {
				if ((new Vector2(x, y)).dst(6, 7) < (new Vector2(x, y)).dst(4, 5))
					return new Vector2(h[0] - h[6], h[1] - h[7]);
				else {
					return new Vector2(h[2] - h[4], h[3] - h[5]);
				}
			} else {
				if ((new Vector2(x, y)).dst(0, 1) < (new Vector2(x, y)).dst(6, 7)) {
					return new Vector2(h[0] - h[2], h[1] - h[3]);
				} else {
					return new Vector2(h[6] - h[4], h[7] - h[5]);
				}
			}
		}
		Vector2 side1ToXY = new Vector2(x - h[0], y - h[1]);
		Vector2 side2ToXY = new Vector2(x - h[6], y - h[7]);
		Vector2 side1 = new Vector2(h[0] - h[2], h[1] - h[3]);
		Vector2 side2 = new Vector2(h[6] - h[4], h[7] - h[5]);
		if (side1ToXY.angle(side1) * side2ToXY.angle(side2) < 0) {
			if ((new Vector2(x, y)).dst(6, 7) < (new Vector2(x, y)).dst(4, 5))
				return new Vector2(h[0] - h[6], h[1] - h[7]);
			else {
				return new Vector2(h[2] - h[4], h[3] - h[5]);
			}
		} else {
			if ((new Vector2(x, y)).dst(0, 1) < (new Vector2(x, y)).dst(6, 7)) {
				return side1;
			} else {
				return side2;
			}

		}
	}
}
