package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class VampiricFang extends AbstractProjectile{
	
	private static Texture fangTexture = Assets.manager.get(Assets.fang);
	private float lifeTime;
	private float angle;
	
	public VampiricFang(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(fangTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		setOrigin(fangTexture.getWidth() / 2, fangTexture.getHeight() / 2);
		setWidth(36);
		setHeight(43);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
		initializeHitbox();
	}
	
	public void act(float delta) {
		lifeTime += delta;
		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
		super.act(delta);
	}
	
	@Override
	public void bounce(Vector2 wall) {
		damageNeighbors();
		destroy();
	}
	
	public void damageNeighbors() {
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				int damage = (int)Math.pow(((AbstractVehicle)e.getCollidable()).getStatValue("Armor"), 0.8) + getStat("Damage");
				((AbstractVehicle)e.getCollidable()).damage(this, damage);
				((AbstractVehicle)e.getCollidable()).applySecondaryForce(getVelocity().cpy().scl(0.2f));
				((AbstractVehicle)e.getCollidable()).applySlow(0.3f);
				source.heal(this, getStat("Damage"));
			}
		}
	}
	
	public void draw(Batch batch, float a) {
		super.draw(batch, a);
		super.drawVertices(batch, a);
	}

	@Override
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
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
