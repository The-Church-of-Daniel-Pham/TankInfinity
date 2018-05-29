package com.tank.actor.vehicles;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.utils.Assets;

public class BasicEnemy extends FixedTank {
	
	Vector2 targetPos = new Vector2(0, 0);
	
	public BasicEnemy(float x, float y) {
		super(x, y, Assets.manager.get(Assets.tread_default));
		initializeStats();
	}
	
	public void initializeStats() {
		stats.addStat("Friction", 95); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 1200);
		stats.addStat("Angular_Friction", 98);
		stats.addStat("Angular_Acceleration", 300);
		//stats.addStat("Rate_Of_Fire", 1);
	}
	
	@Override
	public void act(float delta) {
		if (targetPos != null) {
			moveToTarget(delta);
		}
		super.applyFriction(delta);
		super.move(delta);
	}
	
	public void moveToTarget(float delta) {
		float targetRotation = (float) Math.toDegrees(Math.atan2((targetPos.y - getY()), (targetPos.x - getX())));
		int direction = 0;
		if ((targetRotation - getRotation()) % 360 - 180 > 20) direction = 1;
		else if ((targetRotation - getRotation()) % 360 - 180 < -20) direction = -1;
		
		super.applyAngularForce(delta * stats.getStatValue("Angular_Acceleration") * direction);
		
		int moveForward = 0;
		if (!(Math.abs(targetPos.y - getY()) <= AbstractMapTile.SIZE && Math.abs(targetPos.x - getX()) <= AbstractMapTile.SIZE)) moveForward = 1;
		super.applyForce(delta * stats.getStatValue("Acceleration") * moveForward, getRotation());
		
		//System.out.println(direction + "," + moveForward);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeHitbox() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float) (super.tankTexture.getWidth()) / 2, 0);
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
