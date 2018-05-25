package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.stats.Stats;

public class Bullet extends AbstractProjectile{
	private static Texture playerTexture;
	private static Texture enemyTexture; 

	public Bullet(Texture t, Stats stat, AbstractVehicle src, float x, float y, float direction) {
		super(t, stat, src, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Bullet_Speed"), 0);
		velocity = v.setAngle(direction);
	}
	
	@Override
	public void act(float delta) {
		
	}
	
	
	@Override
	public void checkCollisions(ArrayList<Collidable> other) {
		
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		// TODO Auto-generated method stub
		return null;
	}


}
