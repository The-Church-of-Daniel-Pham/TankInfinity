package com.ttr.actor;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public abstract class DynamicCollider extends Collider {
	public Polygon lastHitBrick;

	// vertex must be of the DynamicCollider's hitbox
	// If the program detects that a brick's corner will be inside the
	// DynamicCollider,
	// its magnitude is equal to zero to indicate a corner collision
	public Vector2 lastCollidingVertex = new Vector2(); // x&y pos

	public boolean collidesAt(float x, float y, float orientation) {
		if (super.getNoclip()) {
			return false;
		}
		// set-up hitboxes
		super.setHitbox(x, y, orientation);
		super.setNeighboringBricksHitboxes(x, y, orientation);
		// detect collision(s)
		for (Polygon brickHitbox : super.getNeighboringBricksHitboxes()) {
			for(int i = 0; i < getHitbox().getVertices().length/2; i++) {
				if (brickHitbox.contains(super.getHitbox().getVertices()[i * 2],
						super.getHitbox().getVertices()[i * 2 + 1])) {
					lastHitBrick = brickHitbox;
					//must use pre-collision x and y pos
					lastCollidingVertex.x = currentHitbox.getVertices()[i * 2];
					lastCollidingVertex.y = currentHitbox.getVertices()[i * 2 + 1];

					return true;
				}
			}
			for (int i = 0; i < 4; i++) {
				
				if (super.getHitbox().contains(brickHitbox.getVertices()[i * 2],
						brickHitbox.getVertices()[i * 2 + 1])) {
					lastHitBrick = brickHitbox;
					lastCollidingVertex.x = 0f;
					lastCollidingVertex.y = 0f;
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollisionVertical() {
		float minY, maxY;
		//minX = lastHitBrick.getVertices()[0];
		//maxX = lastHitBrick.getVertices()[2];
		minY = lastHitBrick.getVertices()[1];
		maxY = lastHitBrick.getVertices()[5];
//		System.out.println("y vertex: " + lastCollidingVertex.y);
//		System.out.println("minY: " + minY + " maxY: " + maxY);
		if(lastCollidingVertex.y < maxY && lastCollidingVertex.y > minY) {
//			System.out.println("returned true (vert)");
			return true;
		}
		else {
//			System.out.println("returned false (horizontal)");
			return false;
		}
			
		
	}
}
