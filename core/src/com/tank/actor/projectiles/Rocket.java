package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.BorderTile;
import com.tank.actor.map.tiles.WallTile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class Rocket extends AbstractProjectile {
	private static Texture rocketTexture = Assets.manager.get(Assets.rocket);
	private float lifeTime;
	private float angle;
	
	public Rocket(AbstractVehicle src, Stats stats, float x, float y, float direction) {
		super(rocketTexture, src, stats, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		setRotation(direction);
		setOrigin(rocketTexture.getWidth() / 2, rocketTexture.getHeight() / 2);
		setWidth(54);
		setHeight(15);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
		//source.changeBulletCount(1);
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
		Stats explosionStats = new Stats();
		explosionStats.addStat("Damage", getStat("Damage") / 3);
		explosionStats.addStat("Explosion Size", 256);
		explosionStats.addStat("Lifetime", 5);
		getStage().addActor(new RadialExplosion(source, explosionStats, getX(), getY()));
		destroy();
	}
	
	public void damageNeighbors() {
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				((AbstractVehicle)e.getCollidable()).applySecondaryForce(getVelocity().cpy().scl(2));
			}
			if (e.getCollidable() instanceof WallTile && !(e.getCollidable() instanceof BorderTile)) {
				((WallTile)e.getCollidable()).destroyWall();
			}
		}
	}
	
	public void draw(Batch batch, float a) {
		super.draw(batch, a);
		//super.drawVertices(batch, a);
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
