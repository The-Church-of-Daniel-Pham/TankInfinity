package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class Bullet extends AbstractProjectile{
	private static Texture playerTexture = Assets.manager.get(Assets.bullet);
	private static Texture enemyTexture;

	public Bullet(AbstractVehicle src, float x, float y, float direction) {
		super(playerTexture, src, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Bullet_Speed"), 0);
		velocity = v.setAngle(direction);
		setRotation(direction);
		setOrigin(playerTexture.getWidth()/2, playerTexture.getHeight()/2);
	}
	

	@Override
	protected void setStats() {
		stats = new Stats();
		stats.addStat("Bullet_Speed", 500);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	


	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		// TODO Auto-generated method stub
		return null;
	}
}
