package com.tank.utils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.interfaces.Collidable;

public class CollisionEvent {
	/**
	 * victim's wall collided with perpetrator's corner. i.e. the instance creating
	 * the CollisionEvent hits a wall
	 */
	public static final int WALL_COLLISION = 0;
	/**
	 * victim's corner collided with perpetrator's wall. i.e. the intsance creating
	 * the CollisionEvent hits a corner
	 */
	public static final int CORNER_COLLISION = 1;
	private Vector2 collisionWall;
	private Vector2 collisionCorner;
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
	public CollisionEvent(Collidable victim, int type, Vector2 wall, Vector2 corner) {
		collidable = victim;
		collisionType = type;
		collisionWall = wall;
		collisionCorner = corner;
	}

	/**
	 * 
	 * @return the magnitude/direction of the wall involved in the collision
	 */
	public Vector2 getWall() {
		return collisionWall;
	}

	/**
	 * 
	 * @return the coords of the corner involved in the collision
	 */
	public Vector2 getCorner() {
		return collisionCorner;
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
			if (Intersector.intersectSegments(pre, post, sidePoint1, sidePoint2, new Vector2())) { // last parameter not
																									// used
				return sidePoint2.cpy().sub(sidePoint1);
			}
		}
		return null; // if no collision found
	}

	/**
	 * Precondition: a collision occurs between 'c' and 'w' and the collision is not
	 * corner to corner. Don't expect the right wall in this case.
	 * 
	 * @param c
	 *            The Polygon hitbox whose corner collides
	 * @param w
	 *            The Polygon hitbox whose wall collides with said corner
	 * @param n
	 *            The even index in the Polygon's array of vertices whose value is
	 *            the x position of the colliding corner
	 * @return The Vector2 which represents the magnitude/direction of the wall on
	 *         'w' that was hit. If no collision is found at the corner, null is
	 *         returned.
	 */
	public static Vector2 getWallVector(Polygon c, Polygon w, int n) {
		n+=8;
		// vertices of c
		float[] cF = c.getVertices();
		// vertices of w
		float[] wF = w.getVertices();
		// coords of corner in question
		Vector2 p1p2 = new Vector2(cF[n%8 ], cF[(n +1)%8]);
		// coords of one of the two closest corners
		Vector2 q1 = new Vector2(cF[(n + 2) % 8], cF[(n + 3) % 8]);
		 Vector2 q2 = new Vector2(cF[(n - 2) % 8], cF[(n - 1) % 8]);
		// make wF vertices into Vector2 coordinates
		Vector2[] wPoints = new Vector2[wF.length / 2];
		for (int i = 0; i < wPoints.length; i++) {
			wPoints[i] = new Vector2(wF[i * 2], wF[i * 2 + 1]);
		}
		// check each wall of 'w' against the one of the two walls of 'c' that form the
		// corner of 'c'
		for (int i = 0; i < wPoints.length; i++) {
			// sidePoint1 and sidePoint2 make a ray that represent a wall of 'w'
			Vector2 sidePoint1 = wPoints[i % wPoints.length]; // point 1
			Vector2 sidePoint2 = wPoints[(i + 1) % wPoints.length]; // point 2
			if (Intersector.intersectSegments(p1p2, q1, sidePoint1, sidePoint2, new Vector2()) || Intersector.intersectSegments(p1p2, q2, sidePoint1, sidePoint2, new Vector2())) {
				System.out.println(sidePoint2.cpy().sub(sidePoint1));
				return sidePoint2.cpy().sub(sidePoint1);
			}
		}
		System.out.println("woops");
		return null;
	}
}
