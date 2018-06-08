/**
 * Author: Daniel P., Samuel H., Edmond F., Gokul S.
 * Description: Used as a subweapon of tanks, 
 * the anti bullet wall is a stationary 'projectile'
 * with high durability that "absorbs" enemy bullets
 * and slows down enemies that attempt to pass it.
 */
package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.NumberUtils;
import com.tank.actor.map.tiles.BorderTile;
import com.tank.actor.map.tiles.WallTile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.stage.Level;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class AntiBulletWall extends AbstractProjectile {
	
	/**
	 * the texture of the bullet wall
	 */
	private static Texture antiBulletWallTexture = Assets.manager.get(Assets.bulletWall);
	/**
	 * the amount of time the bullet wall has existed for, in seconds
	 */
	private float lifeTime = 0f;
	private float maxLifeTime;
	
	private static float maxScale = 4f;
	private static float timeToMaxScale = 1.0f;
	private float angle;
	private ArrayList<AbstractVehicle> vehiclesHit;

	public AntiBulletWall(AbstractVehicle src, Stats stat, float x, float y, float direction) {
		super(antiBulletWallTexture, src, stat, x, y);
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		lifeTime = 0f;
		velocity = v.setAngle(direction);
		super.setRotation(direction);
		super.setWidth(14);
		super.setHeight(128);
		setOrigin(antiBulletWallTexture.getWidth() / 2, antiBulletWallTexture.getHeight() / 2);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
		initializeHitbox();
		maxLifeTime = stats.getStatValue("Lifetime") / 10.0f;
		vehiclesHit = new ArrayList<AbstractVehicle>();
	}
	
	public void act(float delta) {
		lifeTime += delta;
		if (lifeTime < timeToMaxScale) {
			setScaleY(maxScale * (lifeTime / timeToMaxScale));
			super.setHeight(128 * getScaleY());
		}
		else if (lifeTime > maxLifeTime - timeToMaxScale) {
			setScaleY(maxScale * ((maxLifeTime - lifeTime) / timeToMaxScale));
			super.setHeight(128 * getScaleY());
		}
		else {
			setScaleY(maxScale);
			super.setHeight(128 * maxScale);
		}
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
		initializeHitbox();
		
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
		velocity.scl((float)Math.pow(0.05, delta));
		
		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
		
		applySlow();
	}
	
	@Override
	public void draw(Batch batch, float a) {
		batch.setColor(NumberUtils.intToFloatColor(Color.toIntBits(255, 255, 255, 128)));
		super.draw(batch, a);
		batch.setColor(Color.WHITE);
		//super.drawVertices(batch, a);
	}
	
	@Override
	/**
	 * sets its hitbox based on its current position
	 */
	protected void initializeHitbox() {
		testHitbox = hitbox = getHitboxAt(getX(), getY(), getRotation());
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
	
	public void applySlow() {
		checkCollisions(getNeighbors());
		ArrayList<AbstractVehicle> currentVehiclesHit = new ArrayList<AbstractVehicle>();
		for (CollisionEvent e : collisions) {
			if (e.getCollidable() instanceof AbstractVehicle) {
				if (!vehiclesHit.contains((AbstractVehicle)e.getCollidable()) && !currentVehiclesHit.contains((AbstractVehicle)e.getCollidable())) {
					((AbstractVehicle)e.getCollidable()).applySlow(0.5f);
				}
				currentVehiclesHit.add((AbstractVehicle)e.getCollidable());
			}
		}
		vehiclesHit = currentVehiclesHit;
	}
	
	@Override
	public ArrayList<Collidable> getNeighbors() {
		ArrayList<Collidable> neighbors = new ArrayList<Collidable>();

		for (AbstractVehicle v : AbstractVehicle.vehicleList) {
			boolean canCollide = !(v.getTeam() != null && getTeam() != null && getTeam().equals(v.getTeam()));
			if (canCollide)
				neighbors.add(v);
		}
		return neighbors;
	}
}
