/**
 * Author: Daniel P., Samuel H., Edmond F., Gokul S.
 * Description: Used as a subweapon of tanks, 
 * caltrops consist of multiple stationary projectiles bunched in a group, 
 * each of which deal minor damage to enemy tanks.
 * This class represents an individual caltrop and is implemented by CaltropSubWeapon
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

public class Caltrop extends AbstractProjectile {

	/**
	 * the texture of a caltrop
	 */
	private static Texture caltropTexture = Assets.manager.get(Assets.caltrop);
	/**
	 * used for calculating hitboxes
	 */
	private static float angle; // angle between diagonal of rectangle and its base
	/**
	 * the time the projectile has existed, in seconds
	 */
	private float lifeTime = 0f;
	/**
	 * the random angle at which the caltrop is rotated
	 */
	private float rotation;
	/**
	 * the volume of the sound of caltrop hitting a tank
	 */
	private static final float HIT_VOLUME = 0.5f;
	/**
	 * the sound of caltrop hitting a tank
	 */
	private static MediaSound hitSound = new MediaSound(Assets.manager.get(Assets.caltrop_hit), HIT_VOLUME);

	public Caltrop(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(caltropTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		rotation = (float) Math.random() * 360f;
		setOrigin(caltropTexture.getWidth() / 2, caltropTexture.getHeight() / 2);
		setScale(0.6f);
		setWidth(30);
		setHeight(30);
		angle = (float) Math.toDegrees(Math.atan((double) getHeight() / getWidth()));
	}

	@Override
	/**
	 * moves the caltrop in its random direction while slowing it down and removes
	 * it if its lifetime is over
	 */
	public void act(float delta) {
		super.act(delta);
		lifeTime += delta;
		rotation += (velocity.len() * delta) / 50f;
		while (rotation >= 360f)
			rotation -= 360f;
		velocity.scl((float) Math.pow(0.1f, delta));

		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
		super.move(delta);
	}

	@Override
	public void draw(Batch batch, float a) {
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), tex.getWidth(), tex.getHeight(), super.getScaleX(), super.getScaleY(), rotation, 0,
				0, tex.getWidth(), tex.getHeight(), false, false);
		// drawVertices(batch, a);
	}

	@Override
	/**
	 * sets its hitbox based on its current position
	 */
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	/**
	 * caltrops don't bounce since they don't move, but they cause damage upon
	 * collision when bounce is normally called
	 */
	public void bounce(Vector2 wall) {
		damageNeighbors();
	}

	/**
	 * damages AbstractVehicles it has collided with (source's enemy teams' tanks)
	 * using the CollisionEvents created by the last call of checkCollisions()
	 */
	public void damageNeighbors() {
		for (CollisionEvent e : collisions) {
			if (e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle) e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				((AbstractVehicle) e.getCollidable()).applySlow(0.2f);
				hitSound.play();
				destroy();
				return;
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
