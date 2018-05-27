package com.tank.utils;

import com.badlogic.gdx.math.Intersector;
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
	 * Precondition: the ray formed by pre and post intersect c
	 * 
	 * @param c
	 *            The object whose wall collides with the corner of another. The
	 *            corner is given by (x, y)
	 * @param pre
	 *            the coordinates of the corner before the corner collides
	 * @param post
	 *            the coordinates of the corner after the corner collides
	 * @return The Vector2 which represents the magnitude/direction of the wall on
	 *         'c' that was hit
	 */
	public static Vector2 getWallVector(Collidable c, Vector2 pre, Vector2 post) {
		float[] f = c.getHitbox().getVertices();
		Vector2[] points = new Vector2[f.length / 2];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Vector2(f[i * 2], f[i * 2 + 1]);
		}
		for (int i = 0; i < points.length; i++) {
			// define the coordinates of the ray (point1, point2) that describe one side of
			// c
			Vector2 sidePoint1 = points[i % points.length]; // point 1
			Vector2 sidePoint2 = points[(i + 1) % points.length]; // point 2
			if (Intersector.intersectLines(pre, post, sidePoint1, sidePoint2, new Vector2())) { // last parameter not
																								// used
				return sidePoint2.cpy().sub(sidePoint1);
			}
		}
		return null; //if no collision found
	}
}
