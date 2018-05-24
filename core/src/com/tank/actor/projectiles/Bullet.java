package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.stats.Stats;

public class Bullet extends AbstractProjectile{

	public Bullet(Texture t, Stats stat, AbstractVehicle src, float x, float y) {
		super(t, stat, src, x, y);
	}
	
	@Override
	public void act(float delta) {
		
	}
	
	@Override
	public ArrayList<Polygon> getHitbox(){
		return null;
		
	}
	
	@Override
	public void checkCollision(Collidable other) {
		
	}
}
