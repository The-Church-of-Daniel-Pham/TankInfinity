package com.ttr.actor.tank;
/**
 * @author Samuel
import java.util.ArrayList;


import com.badlogic.gdx.graphics.Texture;


 * @version April 13th 2018
 * 
 * Description: Simple projectile, with position and orientation. Continues along straight-line path while within Map bounds.
 */

import com.ttr.actor.DynamicCollider;
import com.ttr.level.Level;
import com.ttr.utils.Assets;

public class Bullet extends DynamicCollider {
	public float tempX, tempY; // test values to determine if move is free from collision

	public static final int SIZE = Assets.manager.get(Assets.bullet).getWidth();
	public static final float VELOCITY = 800;

	public Bullet(float x, float y, float orientation, Level level) {
		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		super.setOrigin(SIZE / 2f, SIZE / 2f); // set origin to center of texture-sized square
		super.setTexture(Assets.manager.get(Assets.bullet));
		super.setLevel(level);
		
		collidesAt(0, 0, 0); // set-up vertex arrays
	}
	
	private void move(float delta) {
		tempX = (super.getX() + (float) Math.cos(Math.toRadians(super.getRotation())) * Bullet.VELOCITY * delta);
		tempY = (super.getY() + (float) Math.sin(Math.toRadians(super.getRotation())) * Bullet.VELOCITY * delta);

		if (super.getLevel().map.inMap(tempX, tempY)
				&& !collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
			super.setY(tempY);
			super.setX(tempX);
		} else {
			onCollision();
		}
	}

	@Override
	public void act(float delta) {
		move(delta);
	}
	
	@Override
	public float[] getVertices(float x, float y, float orientation) {
		float[] vertices = new float[8];
		vertices[0] = super.getX();
		vertices[1] = super.getY();
		vertices[2] = super.getX() + SIZE;
		vertices[3] = super.getY();
		vertices[4] = super.getX() + SIZE;
		vertices[5] = super.getY() + SIZE;
		vertices[6] = super.getX();
		vertices[7] = super.getY() + SIZE;
		return vertices;
	}

	@Override
	public void onCollision() {
		super.remove();
	}
}