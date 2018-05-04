package com.ttr.actor.tank;
/**
 * @author Samuel
import java.util.ArrayList;


import com.badlogic.gdx.graphics.Texture;


 * @version April 13th 2018
 * 
 * Description: Simple projectile, with position and orientation. Continues along straight-line path while within Map bounds.
 */

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.ttr.actor.DynamicCollider;
import com.ttr.level.Level;
import com.ttr.utils.Assets;
import com.ttr.utils.Constants;

public class Bullet extends DynamicCollider {
	public float tempX, tempY; // test values to determine if move is free from collision
	public Vector2 velocity;
	private Sound shoot_sound = Assets.manager.get(Assets.bullet_fire);
	private Sound bounce_sound = Assets.manager.get(Assets.bullet_bounce);
	public static final int SIZE = Assets.manager.get(Assets.bullet).getWidth();
	public static final float SPEED = 800;
	private Vector2 v;
	public int length = 51;
	public int width = 13;
	private float theta = (float) Math.atan2(width, length);
	
	// _______________________
	//|                     . |
	//|                  .    |
	//|               . theta |
	//|            ...........|
	//|                       |
	//|                       |
	//|_______________________|

	public Bullet(float x, float y, float orientation, Level level) {
		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		velocity = new Vector2(SPEED * (float) Math.cos(orientation), SPEED * (float) Math.sin(orientation));
		super.setOrigin(SIZE / 2f, SIZE / 2f); // set origin to center of texture-sized square
		super.setTexture(Assets.manager.get(Assets.bullet));
		super.setScale(Constants.SCALE_VALUE);
		super.setLevel(level);
		v = new Vector2(26,7);
		collidesAt(0, 0, 0); // set-up vertex arrays
		shoot_sound.play();	// play shoot sound on creation
	}
	
	private void move(float delta) {
		tempX = (super.getX() + velocity.x * delta);
		tempY = (super.getY() + velocity.y * delta);

		if (super.getLevel().map.inMap(tempX, tempY)
				&& !collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
			super.setY(tempY);
			super.setX(tempX);
		} else {
			onCollision();
		}
	}
	
	private void bounce(Vector2 wall) {
		System.out.println("bullet velocity: " + velocity);
		System.out.println("wall: " + wall);
		velocity.rotateRad(2 * velocity.angleRad(wall));
		System.out.println("new bullet veolcity: " + velocity);
	}

	@Override
	public void act(float delta) {
		move(delta);
	}
	
	@Override
	public float[] getVertices(float x, float y, float orientation) {
		float[] vertices = new float[8];
		v.setAngleRad(orientation+theta);
		vertices[0] = super.getX() + v.x; //* (float)Math.cos(orientation);
		vertices[1] = super.getY() + v.y;//* (float)Math.sin(orientation);
		v.rotateRad((float) Math.PI - 2 * theta);
		vertices[2] = super.getX() + v.x;// * (float)Math.cos(orientation);
		vertices[3] = super.getY() + v.y;//* (float)Math.sin(orientation);
		v.rotateRad(2 * theta);
		vertices[4] = super.getX() + v.x;// * (float)Math.cos(orientation);
		vertices[5] = super.getY() + v.y; //* (float)Math.sin(orientation);
		v.rotateRad((float) Math.PI - 2 * theta);
		vertices[6] = super.getX() + v.x;// * (float)Math.cos(orientation);
		vertices[7] = super.getY() + v.y; //* (float)Math.sin(orientation);
		return vertices;
	}

	@Override
	public void onCollision() {
		bounce(new Vector2(1,0));
		bounce_sound.play();
		//super.remove();
	}
}