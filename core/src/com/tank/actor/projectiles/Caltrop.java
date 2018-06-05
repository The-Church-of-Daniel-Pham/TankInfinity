package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class Caltrop extends AbstractProjectile{
	
	private static Texture caltropTexture = Assets.manager.get(Assets.caltrop);
	private static float angle;	//angle between diagonal of rectangle and its base
	private float lifeTime = 0f;
	private float rotation;
	
	public Caltrop(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(caltropTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		rotation = (float)Math.random() * 360f;
		setOrigin(caltropTexture.getWidth() / 2, caltropTexture.getHeight() / 2);
		setScale(0.6f);
		setWidth(30);
		setHeight(30);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}
	
	public void act(float delta) {
		super.act(delta);
		lifeTime += delta;
		rotation += (velocity.len() * delta) / 50f;
		while (rotation >= 360f) rotation -= 360f;
		velocity.scl((float)Math.pow(0.3f, delta));
		
		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
		super.move(delta);
	}
	
	@Override
	public void draw(Batch batch, float a) {
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), tex.getWidth(), tex.getHeight(), super.getScaleX(), super.getScaleY(),
				rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
		//drawVertices(batch, a);
	}

	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	@Override
	public void bounce(Vector2 wall) {
		damageNeighbors();
	}
	
	public void damageNeighbors() {
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				((AbstractVehicle)e.getCollidable()).applySlow(0.2f);
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
