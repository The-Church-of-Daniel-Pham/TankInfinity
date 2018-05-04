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
	public static final float SCALE = 1.0f;
	public static final float SPEED = 1000f;
	public static final float LIFETIME = 3.0f; // seconds
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
	// |                     .|
	// |                 .    |
	// |             . theta  |
	// |         .............|
	// |                      |
	// |                      |
	// |______________________|

	public Bullet(float x, float y, float orientation, Level level) {
		super.setX(x);
		super.setY(y);
		velocity = new Vector2(SPEED * (float) Math.cos(orientation), SPEED * (float) Math.sin(orientation));
		super.setRotation(velocity.angle());
		super.setOrigin(SIZE / 2f, SIZE / 2f); // set origin to center of texture-sized square
		super.setTexture(Assets.manager.get(Assets.bullet));
		super.setScale(SCALE);
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

		if (super.getLevel().map.inMap(tempX, tempY)) {
			super.setY(tempY);
			super.setX(tempX);
		}
	}

	private void bounce(Vector2 wall) {
		velocity.rotateRad(2 * velocity.angleRad(wall)); // rotate by double to angle that the bullet forms, relative to
															// the wall
		super.setRotation(velocity.angle()); // update rotation
		bounce_sound.play(0.5f);	// play bounce sound effect
		bounces++; // increment bounces completed
	}

	@Override
	public void act(float delta) {
		age += delta; // add time since last update onto age
		if (age > LIFETIME || bounces > MAX_BOUNCES) { // check if too old or too many bounces
			super.remove(); // remove old or worn out bullet
		} else {
			move(delta); // move to next position
			if (collidesAt(super.getX(), super.getY(), (float) Math.toRadians(super.getRotation()))) { // check for
																										// collision
				onCollision(); // react to collision
			}
		}
	}

	@Override
	public float[] getVertices(float x, float y, float orientation) {
		float[] vertices = new float[8];
		v.setAngleRad(orientation + theta);
		vertices[0] = super.getX() + v.x;
		vertices[1] = super.getY() + v.y;
		v.rotateRad((float) Math.PI - 2 * theta);
		vertices[2] = super.getX() + v.x;
		vertices[3] = super.getY() + v.y;
		v.rotateRad(2 * theta);
		vertices[4] = super.getX() + v.x;
		vertices[5] = super.getY() + v.y;
		v.rotateRad((float) Math.PI - 2 * theta);
		vertices[6] = super.getX() + v.x;
		vertices[7] = super.getY() + v.y;
		return vertices;
	}

	@Override
	public void onCollision() {
		bounce(new Vector2(1, 0)); // currently set for horizontal wall faces, will change later
		// appears to die on vertical collisions, what is really happening is that it
		// collides super often, rapidly flipping direction, then passes the bounce max
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		drawVertices(batch, alpha);
	}
}