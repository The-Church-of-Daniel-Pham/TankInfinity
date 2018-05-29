package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class Bullet extends AbstractProjectile {
	private static Texture playerTexture = Assets.manager.get(Assets.bullet);
	private static Texture enemyTexture;

	public Bullet(AbstractVehicle src, float x, float y, float direction) {
		super(playerTexture, src, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Bullet_Speed"), 0);
		velocity = v.setAngle(direction);
		setRotation(direction);
		setOrigin(playerTexture.getWidth() / 2, playerTexture.getHeight() / 2);
	}

	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	protected void setStats() {
		stats = new Stats();
		stats.addStat("Bullet_Speed", 700);
	}

	public void draw(Batch batch, float a) {
		super.draw(batch, a);
		super.drawVertices(batch, a);
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float) (playerTexture.getWidth()) / 2, 0);
		v.setAngle(direction);
		v.rotate(45);

		for (int i = 0; i < 4; i++) {
			f[i * 2] = x + v.x;
			f[i * 2 + 1] = y + v.y;
			v.rotate(90);
		}
		return new Polygon(f);
	}
}
