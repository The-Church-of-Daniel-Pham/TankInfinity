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

public class Chakram extends AbstractProjectile{
	
	private static Texture chakramTexture = Assets.manager.get(Assets.chakram);
	private static float angle;	//angle between diagonal of rectangle and its base
	private int bounceCount = 0;
	private float lifeTime = 0f;
	private float rotateTime = 0f;
	
	private static final float BOUNCE_VOLUME = 0.5f;
    private static final float HIT_VOLUME = 0.5f;
    private static MediaSound bounceSound = new MediaSound(Assets.manager.get(Assets.chakram_bounce), BOUNCE_VOLUME);
    private static MediaSound hitSound = new MediaSound(Assets.manager.get(Assets.chakram_hit), HIT_VOLUME);
    
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
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}
	
	public void act(float delta) {
		super.act(delta);
		lifeTime += delta;
		rotateTime += delta;
		if (rotateTime >= 2.0f) rotateTime -= 2.0f;
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
		//drawVertices(batch, a);
	}

	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	@Override
	public void bounce(Vector2 wall) {
		boolean hit = damageNeighbors();
		bounceCount += 1;
		if (bounceCount <= stats.getStatValue("Max Bounce")) {
			if (!hit) bounceSound.play();
			super.bounce(wall);
		}
		else {
			destroy();
		}
	}
	
	public boolean damageNeighbors() {
		boolean hit = false;
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				((AbstractVehicle)e.getCollidable()).applySecondaryForce(getVelocity().cpy().scl(0.5f));
				if (!hit) hitSound.play();
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
}
