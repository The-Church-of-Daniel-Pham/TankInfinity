package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class Bullet extends AbstractProjectile {
	private static Texture playerTexture = Assets.manager.get(Assets.bullet);
	//private static Texture enemyTexture;
	private static float angle;	//angle between diagonal of rectangle and its base

    private static final float BOUNCE_VOLUME = 0.5f;
    private static final float HIT_VOLUME = 0.5f;
    private static MediaSound bounceSound = new MediaSound(Assets.manager.get(Assets.bullet_bounce), BOUNCE_VOLUME);
    private static MediaSound hitSound = new MediaSound(Assets.manager.get(Assets.bullet_hit), HIT_VOLUME);
    private int bounceCount = 0;
    private float lifeTime;

	public Bullet(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(playerTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		setOrigin(playerTexture.getWidth() / 2, playerTexture.getHeight() / 2);
		setScale(1.5f);
		setWidth(25 * getScaleX());
		setHeight(6 * getScaleY());
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
		source.changeBulletCount(1);
	}
	
	public void act(float delta) {
		lifeTime += delta;
		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
		super.act(delta);
	}

	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	@Override
	public void bounce(Vector2 wall) {
		damageNeighbors();
		if (!isDestroyed()) {
			bounceCount += 1;
			if (bounceCount <= stats.getStatValue("Max Bounce")) {
				bounceSound.play();
				super.bounce(wall);
			}
			else {
				destroy();
			}
		}
	}
	
	public void damageNeighbors() {
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				hitSound.play();
				destroy();
				break;
			}
		}
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(angle);
		f[0] = x+ v.x;
		f[1] = y +v.y;
		v.rotate(180-2*angle);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(2*angle);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(180-2*angle);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}
	@Override
	public void destroy() {
		source.changeBulletCount(-1);
		super.destroy();
	}
}
