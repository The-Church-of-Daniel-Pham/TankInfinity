package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class LandMine extends AbstractProjectile {
	private static Texture landMineTexture = Assets.manager.get(Assets.landMine);
	private float lifeTime = 0f;

	public LandMine(AbstractVehicle src, Stats stat, float x, float y) {
		super(landMineTexture, src, stat, x, y);
		//super.setRotation(src.getRotation());
		super.setWidth(128);
		super.setHeight(128);
		setOrigin(landMineTexture.getWidth() / 2, landMineTexture.getHeight() / 2);
		initializeHitbox();
		velocity = new Vector2(0, 0);
	}

	@Override
	public void act(float delta) {
		lifeTime += delta;
		checkDetonation();
		if (lifeTime >= stats.getStatValue("Lifetime") / 10.0f) {
			destroy();
			return;
		}
	}

	@Override
	public void move(float delta) {
		// mines don't move
	}

	@Override
	public void updateVelocityAndMove() {
		// do nothing
	}

	@Override
	public void applyForce(Vector2 acceleration) {
		// can't apply forces
	}

	@Override
	public void applyForce(float mag, float dir) {
		// can't apply forces
	}

	@Override
	public void bounce(Vector2 wall) {
		// nothing
	}
	
	public void checkDetonation() {
		checkCollisions(getNeighbors());
		boolean markForDeletion = false;
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle) {
				((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
				float tankX = ((AbstractVehicle)e.getCollidable()).getX();
				float tankY = ((AbstractVehicle)e.getCollidable()).getY();
				float direction =  (float) Math.toDegrees(Math.atan2((tankY - getY()), (tankX - getX())));
				Vector2 knockback = new Vector2(2000, 0);
				knockback.setAngle(direction);
				((AbstractVehicle)e.getCollidable()).applySecondaryForce(knockback);
				markForDeletion = true;
			}
		}
		if (markForDeletion) destroy();
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
	 
	@Override
	protected void initializeHitbox() {
		testHitbox = hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(getRotation() + 45);
		f[0] = getX() + v.x;
		f[1] = getY() + v.y;
		v.rotate(90);
		f[2] = getX() + v.x;
		f[3] = getY() + v.y;
		v.rotate(90);
		f[4] = getX() + v.x;
		f[5] = getY() + v.y;
		v.rotate(90);
		f[6] = getX() + v.x;
		f[7] = getY() + v.y;
		return new Polygon(f);
	}

}
