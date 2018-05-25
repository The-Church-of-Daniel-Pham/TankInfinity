package com.tank.interfaces;

import com.badlogic.gdx.math.Polygon;

public interface Collidable {
	public Polygon getHitbox();
	public void checkCollision(Collidable other);
}
