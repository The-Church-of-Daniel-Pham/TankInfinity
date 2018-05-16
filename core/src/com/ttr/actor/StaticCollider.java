package com.ttr.actor;

import com.ttr.stage.Level;

public abstract class StaticCollider extends Collider {

	public StaticCollider(Level level) {
		super(level);
		
	}	@Override
	public void onCollision() {
		// don't move
	}
}
