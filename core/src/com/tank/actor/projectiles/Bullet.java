package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.badlogic.gdx.audio.Sound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class Bullet extends AbstractProjectile {
	private static Texture playerTexture = Assets.manager.get(Assets.bullet);
	private static Texture enemyTexture;
	private static float angle;	//angle between diagonal of rectangle and its base

	private static Sound bounce_sound = Assets.manager.get(Assets.bullet_bounce);
    private static final float BOUNCE_VOLUME = 0.5f;
    private int bounceCount = 0;

	public Bullet(AbstractVehicle src, float x, float y, float direction) {
		super(playerTexture, src, x, y, bounce_sound, BOUNCE_VOLUME);
		Vector2 v = new Vector2(stats.getStatValue("Bullet_Speed"), 0);
		velocity = v.setAngle(direction);
		setRotation(direction);
		setOrigin(playerTexture.getWidth() / 2, playerTexture.getHeight() / 2);
		setWidth(25);
		setHeight(6);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}

	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	protected void setStats() {
		stats = new Stats();
		stats.addStat("Bullet_Speed", 700);
		stats.addStat("Damage", 10);
	}
	
	@Override
	public void bounce(Vector2 wall) {
		damageNeighbors();
		bounceCount += 1;
		if (bounceCount <= 3)
			super.bounce(wall);
		else
			super.destroy();
	}
	
	public void damageNeighbors() {
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
			}
		}
	}

	public void draw(Batch batch, float a) {
		super.draw(batch, a);
		super.drawVertices(batch, a);
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
