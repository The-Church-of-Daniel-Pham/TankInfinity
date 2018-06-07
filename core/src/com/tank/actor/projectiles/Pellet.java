/**
 * Author: Daniel P., Samuel H., Edmond F., Gokul S.
 * Description: Used as a subweapon of tanks, 
 * the pellet is essentially a shotgun. Multiple Pellet instances, implemented by PelletsSubweapon, form the subweapon
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

public class Pellet extends AbstractProjectile {
	/**
	 * the texture of the pellet
	 */
	private static Texture pelletTexture = Assets.manager.get(Assets.pellet);
	/**
	 * used for calculating hitboxes
	 */
	private static float angle; // angle between diagonal of rectangle and its base
	/**
	 * how long the laser has existed for, in seconds
	 */
	private float lifeTime = 0f;
	/**
	 * used for animating the chakram rotation
	 */
	private float rotateTime = 0f;
	/**
	 * used for animating the rotation. refers to the angle, in degrees, of the
	 * pellet's rotation
	 */
	private float animationRotation;
	/**
	 * the volume of the hit sound
	 */
	private static float HIT_VOLUME = 0.5f;
	/**
	 * the hit sound
	 */
	private static MediaSound hitSound = new MediaSound(Assets.manager.get(Assets.pellet_hit), HIT_VOLUME);

	public Pellet(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(pelletTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		rotateTime = 2.0f / direction;
		setOrigin(pelletTexture.getWidth() / 2, pelletTexture.getHeight() / 2);
		setWidth(16);
		setHeight(16);
		angle = (float) Math.toDegrees(Math.atan((double) getHeight() / getWidth()));
	}

	/**
	 * attempts to move pellet, checks if max lifetime is reached (destroying the
	 * pellet if true), and rotates the pellet
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
	 * bounces off a wall/tank. for pellets, colliding neighbors are damaged and the
	 * pellet is destroyed upon bounce
	 */
	public void bounce(Vector2 wall) {
		damageNeighbors();
		destroy();
	}

	public void damageNeighbors() {
		for (CollisionEvent e : collisions) {
			if (e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle) e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				((AbstractVehicle) e.getCollidable()).applySecondaryForce(getVelocity().cpy().scl(0.1f));
				hitSound.play();
			}
		}
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
