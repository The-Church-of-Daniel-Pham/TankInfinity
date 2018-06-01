package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class LandMine extends AbstractProjectile {
	private static Texture landMineTexture = Assets.manager.get(Assets.landMine);

	public LandMine(AbstractVehicle src, Stats stat, float x, float y) {
		super(landMineTexture, src, stat, x, y);
		//super.setRotation(src.getRotation());
		super.setWidth(128);
		super.setHeight(128);
		initializeHitbox();
		velocity = new Vector2(0, 0);
	}

	@Override
	public void act(float delta) {
		// do nothing

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
		// mines don't bounce
	}

	@Override
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
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
