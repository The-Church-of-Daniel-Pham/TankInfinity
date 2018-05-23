package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.interfaces.Destructible;
import com.tank.stats.Stats;

public abstract class AbstractProjectile extends Actor implements Collidable, Destructible{

protected Texture tex;
protected Vector2 velocity;
protected AbstractVehicle source;
protected Stats stats;

	public AbstractProjectile(Texture t, Stats stat, AbstractVehicle src, float x, float y) {
		tex = t;
		stats = stat;
		source = src;
		setX(x);
		setY(y);
	}
	
	public void act(float delta) {
		
	}
	public void draw(Batch batch, float a) {
		
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	public void updateVelocityAndMove() {
		
	}
	public void applyForce(Vector2 acceleration) {
		
	}
	public void applyForce(float mag, float dir) {
		
	}
	public void bounce(Vector2 wall) {
		
	}
	public int getStat(String stat) {
		return stats.getStatValue(stat);
	}
	
	public abstract ArrayList<Polygon> getHitbox();
	public abstract void checkCollision(Collidable other);
	
	public void damage(Actor source, int damage) {
		
	}
	public void heal(Actor source, int heal) {
		
	}
	public void destroy() {
		this.remove();
	}
	public boolean isDestroyed() {
		return false;
	}
}
