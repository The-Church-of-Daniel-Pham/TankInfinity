/**
 * Author: Daniel P., Samuel H., Edmond F., Gokul S.
 * Description: Used as a subweapon of tanks, 
 * the chakram is a large, circular, rotating projectile that damages enemies upon bouncing.
 */
package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class Chakram extends AbstractProjectile {
	/**
	 * the texture of the chakram
	 */
	private static Texture chakramTexture = Assets.manager.get(Assets.chakram);
	/**
	 * used for calculating hitbox
	 */
	private static float angle; // angle between diagonal of rectangle and its base
	/**
	 * number of times the chakram has bounced
	 */
	private int bounceCount = 0;
	/**
	 * how long the chakram has existed for, in seconds
	 */
	private float lifeTime = 0f;
	/**
	 * used for animating the chakram rotation
	 */
	private float rotateTime = 0f;
	/**
	 * the volume of the sound of the bounce
	 */
	private static final float BOUNCE_VOLUME = 0.5f;
	/**
	 * the volume of the sound of the chakram hit
	 */
	private static final float HIT_VOLUME = 0.5f;
	/**
	 * the sound of the bounce
	 */
	private static MediaSound bounceSound = new MediaSound(Assets.manager.get(Assets.chakram_bounce), BOUNCE_VOLUME);
	/**
	 * the sound of the hit
	 */
	private static MediaSound hitSound = new MediaSound(Assets.manager.get(Assets.chakram_hit), HIT_VOLUME);
	/**
	 * used for animating the rotation. refers to the angle, in degrees, of the
	 * chakram's rotation
	 */
	private float animationRotation;

	public Chakram(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(chakramTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		rotateTime = 2.0f / direction;
		setOrigin(chakramTexture.getWidth() / 2, chakramTexture.getHeight() / 2);
		setWidth(50);
		setHeight(50);
		angle = (float) Math.toDegrees(Math.atan((double) getHeight() / getWidth()));
	}

	/**
	 * moves the chakram, checks if it has reached its lifetime (and destroys it if
	 * so), and updates the rotation for the animation
	 */
	public void act(float delta) {
		super.act(delta);
		lifeTime += delta;
		rotateTime += delta;
		if (rotateTime >= 2.0f)
			rotateTime -= 2.0f;
		animationRotation = 360 * (rotateTime / 2.0f);
		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
	}

	@Override
	public void draw(Batch batch, float a) {
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), tex.getWidth(), tex.getHeight(), super.getScaleX(), super.getScaleY(),
				animationRotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
		// drawVertices(batch, a);
	}

	/**
	 * sets its hitbox based on its current position
	 */
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	/**
	 * The bounce method is used to deflect the direction of the projectile when it
	 * bounces off a wall/tank. checks if chakram has exceeded the max number of bounces
	 * (destroying it if so)
	 */
	public void bounce(Vector2 wall) {
		boolean hit = damageNeighbors();
		bounceCount += 1;
		if (bounceCount <= stats.getStatValue("Max Bounce")) {
			if (!hit)
				bounceSound.play();
			super.bounce(wall);
		} else {
			destroy();
		}
	}

	/**
	 * damages AbstractVehicles it has collided with (source's enemy teams' tanks)
	 * using the CollisionEvents created by the last call of checkCollisions()
	 * 
	 * @return true if damage was dealt, otherwise false
	 */
	public boolean damageNeighbors() {
		boolean hit = false;
		for (CollisionEvent e : collisions) {
			if (e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle) e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				((AbstractVehicle) e.getCollidable()).applySecondaryForce(getVelocity().cpy().scl(0.5f));
				if (!hit)
					hitSound.play();
				hit = true;
			}
		}
		return hit;
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(angle);
		f[0] = x + v.x;
		f[1] = y + v.y;
		v.rotate(180 - 2 * angle);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(2 * angle);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(180 - 2 * angle);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}
}
