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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.ttr.actor.DynamicCollider;
import com.ttr.level.Level;
import com.ttr.utils.Assets;

public class Bullet extends DynamicCollider {
	public static final int SIZE = Assets.manager.get(Assets.bullet).getWidth();
	public static final float SCALE = 2.0f;
	public static final float SPEED = 800;
	public static final float LIFETIME = 6.0f; // seconds
	public static final int MAX_BOUNCES = 3;

	private Sound shoot_sound = Assets.manager.get(Assets.bullet_fire);
	private Sound bounce_sound = Assets.manager.get(Assets.bullet_bounce);

	public Vector2 velocity;
	private Vector2 v; // for determining vertices of hitbox
	public int length = 12; // of region in texture for hitbox
	public int width = 4; // of region in texture for hitbox
	private float theta = (float) Math.atan2(width, length); // see diagram, also doesn't change with scale
	public float age; // time since creation, in seconds
	public int bounces; // number of bounces that have occurred since creation

	// _______________________
	// |_____________________.|
	// |_________________.____|
	// |_____________.__theta_|
	// |_________.............|
	// |______________________|
	// |______________________|
	// |______________________|

	public Bullet(float x, float y, float orientation, Level level) {
		super.setX(x);
		super.setY(y);
		velocity = new Vector2(SPEED * (float) Math.cos(orientation), SPEED * (float) Math.sin(orientation));
		super.setRotation(velocity.angle());
		super.setOrigin(SIZE / 2f, SIZE / 2f); // set origin to center of texture-sized square
		super.setTexture(Assets.manager.get(Assets.bullet));
		super.setLevel(level);
		v = new Vector2(length * SCALE, width * SCALE);

		collidesAt(0, 0, 0); // set-up vertex arrays
		age = 0f; // starts with 0 age
		bounces = 0; // starts with 0 bounces
		shoot_sound.play(0.7f); // play shoot sound on creation

	}

	private void move(float delta) {
		float tempX = super.getX() + velocity.x * delta;
		float tempY = super.getY() + velocity.y * delta;

		if (collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {//super.getLevel().map.inMap(tempX, tempY)) {
			
			onCollision();
		}
		else {
			super.setY(tempY);
			super.setX(tempX);
		}
	}

	private void bounce(Vector2 wall) {
		velocity.rotateRad(2 * velocity.angleRad(wall)); // rotate by double to angle that the bullet forms, relative to
															// the wall
		super.setRotation(velocity.angle()); // update rotation
		bounce_sound.play(0.25f); // play bounce sound effect
		bounces++; // increment bounces completed
	}

	@Override
	public void act(float delta) {
		age += delta; // add time since last update onto age
		if (age > LIFETIME || bounces > MAX_BOUNCES) { // check if too old or too many bounces
			super.remove(); // remove old or worn out bullet
		} else {
			move(delta); // move to next position
			
		}
	}

	@Override
	public float[] getVertices(float x, float y, float orientation) {
//		float[] vertices = new float[2];
//		Vector2 ve = new Vector2(13, 0);
//		ve.setAngleRad(orientation);
//		vertices[0] = x + ve.x;
//		vertices[1] = y + ve.y;
//		ve.x = 13; ve.y = 4;
//		ve.setAngleRad(orientation + 2.843f);
//		vertices[2] = x + ve.x;
//		vertices[3] = y + ve.y;
//		ve.rotateRad(0.597f);
//		vertices[4] = x + ve.x;
//		vertices[5] = y + ve.y;
		float[] vertices = new float[8];
		v.setAngleRad(orientation + theta);
		vertices[0] = x + v.x;
		vertices[1] = y + v.y;
		v.rotateRad((float) Math.PI - 2 * theta);
		vertices[2] = x + v.x;
		vertices[3] = y + v.y;
		v.rotateRad(2 * theta);
		vertices[4] = x + v.x;
		vertices[5] = y + v.y;
		v.rotateRad((float) Math.PI - 2 * theta);
		vertices[6] = x + v.x;
		vertices[7] = y + v.y;
		return vertices;
	}

	@Override
	public void onCollision() {
		if (isCollisionVertical()) {
			bounce(new Vector2(0, 1));
		} else {
			bounce(new Vector2(1, 0)); // currently set for horizontal wall faces, will change later
			// appears to die on vertical collisions, what is really happening is that it
			// collides super often, rapidly flipping direction, then passes the bounce max
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		//drawVertices(batch, alpha);
	}
}