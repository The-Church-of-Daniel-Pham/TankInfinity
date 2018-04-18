package com.ttr.tank;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Simple projectile, with position and orientation. Continues along straight-line path while within Map bounds.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ttr.map.Map;
import com.ttr.utils.Assets;

public class Bullet extends Actor{
	public float x, y, orientation;
	private Sprite bullet;
	
	public static final float VELOCITY = 800;
	
	public Bullet(float x, float y, float orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		bullet = new Sprite(Assets.manager.get(Assets.bullet));
	}
	
	@Override
	public void act(float delta) {
		y += Math.sin(orientation) * Bullet.VELOCITY * delta;
		x += Math.cos(orientation) * Bullet.VELOCITY * delta;
		// removes out of bounds balls
		if (!Map.isValid(x, y)) {
			remove();
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		bullet.setPosition(x, y);
		bullet.setRotation((float) Math.toDegrees(orientation));
		bullet.draw(batch);
	}
	
	public String toString() {
		return ("(" + x + ", " + y + ") with " + orientation + " orientation");
	}
}