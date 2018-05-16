package com.ttr.actor.tank;
/**
 * @author Samuel
import java.util.ArrayList;


import com.badlogic.gdx.graphics.Texture;


 * @version April 13th 2018
 * 
 * Description: Simple projectile, with position and orientation. Continues along straight-line path while within Map bounds.
 */

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ttr.actor.DynamicCollider;
import com.ttr.actor.map.BorderTile;
import com.ttr.actor.map.FloorTile;
import com.ttr.actor.map.MapTile;
import com.ttr.stage.Level;
import com.ttr.utils.Assets;

public class Bullet extends DynamicCollider {
	public static final int SIZE = Assets.manager.get(Assets.bullet).getWidth();
	public static final float SCALE = 2.0f;
	public static final float SPEED = 1000;
	public static final float LIFETIME = 6.0f; // seconds
	public static final int MAX_BOUNCES = 2;
	public static final float DAMAGE = 30;

	private Sound shoot_sound = Assets.manager.get(Assets.bullet_fire);
	private Sound bounce_sound = Assets.manager.get(Assets.bullet_bounce);

	public Vector2 velocity;
	private Vector2 v; // for determining vertices of hitbox
	public int length = 12; // of region in texture for hitbox
	public int width = 4; // of region in texture for hitbox
	private float theta = (float) Math.atan2(width, length); // see diagram, also doesn't change with scale
	public float age; // time since creation, in seconds
	public int bounces; // number of bounces that have occurred since creation
	public boolean collidedWithWall;
	public boolean collidedWithTank;
	// _______________________
	// |_____________________.|
	// |_________________.____|
	// |_____________.__theta_|
	// |_________.............|
	// |______________________|
	// |______________________|
	// |______________________|

	public Bullet(float x, float y, float orientation, Level level) {
		super(level);
		super.setX(x);
		super.setY(y);
		velocity = new Vector2(SPEED * (float) Math.cos(orientation), SPEED * (float) Math.sin(orientation));
		super.setRotation(velocity.angle());
		super.setOrigin(SIZE / 2f, SIZE / 2f); // set origin to center of texture-sized square
		super.setTexture(Assets.manager.get(Assets.bullet));
		v = new Vector2(length * SCALE, width * SCALE);
		age = 0f; // starts with 0 age
		bounces = 0; // starts with 0 bounces
		shoot_sound.play(0.7f); // play shoot sound on creation

	}

	private void move(float delta) {
		float tempX = super.getX() + velocity.x * delta;
		float tempY = super.getY() + velocity.y * delta;

		if (collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {// super.getLevel().map.inMap(tempX,
																					// tempY)) {

			onCollision();
		} else {
			super.setY(tempY);
			super.setX(tempX);
			updateHitbox();
		}
		updateHitbox();
	}

	@Override
	public boolean collidesAt(float x, float y, float orientation) {
		collidedWithWall = super.collidesAt(x, y, orientation);
		if(noclip)
			return false;
		collidedWithTank = false;
		ArrayList<Vector2> lastCollidingVertices = new ArrayList<Vector2>();
		Tank t = getLevel().playerTank;
		for (int i = 0; i < 4; i++) {
			if (t.getHitbox().contains(collisionHitbox.getVertices()[i * 2],
					collisionHitbox.getVertices()[i * 2 + 1])) {
				lastCollidingVertices
						.add(new Vector2(getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1]));
				collidedWithTank = true;
			}
		}
		if (lastCollidingVertices.size() > 0) {
			this.lastCollidingVertices = lastCollidingVertices;
		}
		return collidedWithTank || collidedWithWall;
	}

	public float restrictDegrees(float d) {
		if (d < 0) {
			return d % 360 + 360;
		}
		return d % 360;
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
	public Polygon getHitboxAt(float x, float y, float orientation) {
		// float[] vertices = new float[2];
		// Vector2 ve = new Vector2(13, 0);
		// ve.setAngleRad(orientation);
		// vertices[0] = x + ve.x;
		// vertices[1] = y + ve.y;
		// ve.x = 13; ve.y = 4;
		// ve.setAngleRad(orientation + 2.843f);
		// vertices[2] = x + ve.x;
		// vertices[3] = y + ve.y;
		// ve.rotateRad(0.597f);
		// vertices[4] = x + ve.x;
		// vertices[5] = y + ve.y;
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
		return new Polygon(vertices);
	}

	@Override
	public void onCollision() {
		if (collidedWithWall) {
			if (isCollisionVertical()) {
				bounce(new Vector2(0, 1));
			} else {
				bounce(new Vector2(1, 0));
			}
			for (MapTile m : lastHitBricks) {
				m.takeDamage(DAMAGE);
			}
		} else if (collidedWithTank) {
			Tank t = getLevel().playerTank;
			float[] tankV = t.getHitbox().getVertices();
			float vX = lastCollidingVertices.get(0).x;
			float vY = lastCollidingVertices.get(0).y;
			Vector2 leftToV = new Vector2(vX - tankV[0], vY - tankV[1]);
			Vector2 rightToV = new Vector2(vX - tankV[6], vY - tankV[7]);
			Vector2 tankLeftSide = new Vector2(tankV[0] - tankV[2], tankV[1] - tankV[3]);
			Vector2 tankRightSide = new Vector2(tankV[6] - tankV[4], tankV[7] - tankV[5]);
			if (leftToV.angle(tankLeftSide) * rightToV.angle(tankRightSide) < 0) {
				bounce(new Vector2(tankV[0] - tankV[6], tankV[1] - tankV[7]));
			} else {
				bounce(new Vector2(tankV[0] - tankV[2], tankV[1] - tankV[3]));
			}
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		super.drawVertices(batch, alpha);
	}
}