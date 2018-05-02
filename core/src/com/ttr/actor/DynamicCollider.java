package com.ttr.actor;

import com.badlogic.gdx.math.Polygon;

public abstract class DynamicCollider extends Collider{
	public boolean collidesAt(float x, float y, float orientation) {
		if (super.getNoclip()) {
			return false;
		}
		// set-up hitboxes
		super.setHitbox(x, y, orientation);
		super.setNeighboringBricksHitboxes(x, y, orientation);
		// detect collision(s)
		for (Polygon brickHitbox : super.getNeighboringBricksHitboxes()) {
			for (int i = 0; i < 4; i++) {
				if (brickHitbox.contains(super.getHitbox().getVertices()[i * 2], super.getHitbox().getVertices()[i * 2 + 1])) 
				{
					
					return true;
				}
				if (super.getHitbox().contains(brickHitbox.getVertices()[i * 2], brickHitbox.getVertices()[i * 2 + 1])) {
					return true;
				}
			}
		}
		return false;
	}
}
