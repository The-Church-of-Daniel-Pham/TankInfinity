package com.ttr.actor;

import com.badlogic.gdx.math.Polygon;

public abstract class Collider extends LevelActor {
	public abstract float[] getVertices(float x, float y, float orientation);
	public Polygon getHitbox(float x, float y, float orientation) {
		Polygon hitbox = new Polygon();
		float[] vertices = getVertices(x, y, orientation);
		hitbox.setVertices(vertices);
		return hitbox;
	}
	public abstract void onCollision();
}
