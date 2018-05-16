package com.ttr.actor;

import java.util.ArrayList;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ttr.actor.map.MapTile;
import com.ttr.stage.Level;

public abstract class DynamicCollider extends Collider {
	public ArrayList<MapTile> lastHitBricks; //bricks from the most recent collision
	protected Polygon collisionHitbox; //used for detecting collisions

	// vertex must be of the DynamicCollider's hitbox
	// If the program detects that a brick's corner will be inside the
	// DynamicCollider,
	// its magnitude is equal to zero to indicate a corner collision
	public ArrayList<Vector2> lastCollidingVertices; // x&y pos
	protected ArrayList<MapTile> nearbyBricks;

	public DynamicCollider(Level level) {
		super(level);
		lastCollidingVertices = new ArrayList<Vector2>();
		lastHitBricks = new ArrayList<MapTile>();
		nearbyBricks = new ArrayList<MapTile>();
		currentHitbox = new Polygon(new float[8]);
	}

	public boolean collidesAt(float x, float y, float orientation) {
		if (super.getNoclip()) {
			return false;
		}
		ArrayList<MapTile> lastHitBricks = new ArrayList<MapTile>();
		ArrayList<Vector2> lastCollidingVertices = new ArrayList<Vector2>();
		setCollisionHitbox(x, y, orientation);
		updateNearbyBricks();
		for(MapTile tile: nearbyBricks) {
			for(int i = 0; i < collisionHitbox.getVertices().length/2; i++) {
				if(tile.getHitbox().contains(collisionHitbox.getVertices()[i*2],
						collisionHitbox.getVertices()[i*2+1])) {
					lastCollidingVertices.add(new Vector2(
							getHitbox().getVertices()[i*2],
							getHitbox().getVertices()[i*2+1]));
					if(!lastHitBricks.contains(tile)) {
					lastHitBricks.add(tile);
					}
				}
			}
//TODO keep info about which corner of brick is hit
			for(int i = 0; i < 4; i++) {
				if(collisionHitbox.contains(tile.getHitbox().getVertices()[i*2], tile.getHitbox().getVertices()[i*2 +1])) {
					if(!lastHitBricks.contains(tile)) {
						lastHitBricks.add(tile);
						}	
				}
			}
		}
		if(lastHitBricks.size() > 0 || lastCollidingVertices.size() > 0) {
			this.lastHitBricks = lastHitBricks;
			this.lastCollidingVertices = lastCollidingVertices;
			return true;
		}
		return false;
//		// set-up hitboxes
//		super.setHitbox(x, y, orientation);
//		super.setNeighboringBricksHitboxes(x, y, orientation);
//		// detect collision(s)
//		for (Polygon brickHitbox : super.getNeighboringBricksHitboxes()) {
//			for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
//				if (brickHitbox.contains(super.getHitbox().getVertices()[i * 2],
//						super.getHitbox().getVertices()[i * 2 + 1])) {
//					lastHitBrick = brickHitbox;
//					// must use pre-collision x and y pos
//					lastCollidingVertex.x = currentHitbox.getVertices()[i * 2];
//					lastCollidingVertex.y = currentHitbox.getVertices()[i * 2 + 1];
//
//					return true;
//				}
//			}
//			for (int i = 0; i < 4; i++) {
//
//				if (super.getHitbox().contains(brickHitbox.getVertices()[i * 2],
//						brickHitbox.getVertices()[i * 2 + 1])) {
//					lastHitBrick = brickHitbox;
//					lastCollidingVertex.x = 0f;
//					lastCollidingVertex.y = 0f;
//					return true;
//				}
//			}
//		}
//		return false;
	}

	public void updateNearbyBricks() {
		int[] tile = getLevel().map.getTileAt(getX(), getY());
		int row = tile[0];
		int col = tile[1];
		nearbyBricks = getLevel().map.getBrickNeighbors(row,col);
	}

	public void setCollisionHitbox(float x, float y, float orientation) {
		collisionHitbox = getHitboxAt(x, y, orientation);
	}

	public void updateHitbox() {
		currentHitbox = collisionHitbox;
	}

	public boolean isCollisionVertical() {
		float minY, maxY;
		// minX = lastHitBrick.getVertices()[0];
		// maxX = lastHitBrick.getVertices()[2];
		minY = lastHitBricks.get(0).getHitbox().getVertices()[1];
		maxY = lastHitBricks.get(0).getHitbox().getVertices()[5];
		// System.out.println("y vertex: " + lastCollidingVertex.y);
		// System.out.println("minY: " + minY + " maxY: " + maxY);
		if(lastCollidingVertices.size() == 0) {
			//a bullet grazing a regular old open corner will 
			//result in a collision but no observed bullet vertex collision 
			//(only brick's vertex is detected to have collided)
			return true;
		}
		if (lastCollidingVertices.get(0).y < maxY && lastCollidingVertices.get(0).y > minY) {
			// System.out.println("returned true (vert)");
			return true;
		} else {
			// System.out.println("returned false (horizontal)");
			return false;
		}

	}
}
