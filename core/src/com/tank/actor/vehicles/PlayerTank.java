package com.tank.actor.vehicles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.ui.Cursor;
import com.tank.controls.TankController;
import com.tank.interfaces.Collidable;
import com.tank.subweapons.SubWeapon;

public class PlayerTank extends FreeTank {

	protected TankController controls;
	protected Cursor cursor;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;

	public PlayerTank(int player, Texture tTexture, Texture gTexture, float x, float y) {
		super(tTexture, gTexture, x, y);
		playerNumber = player;

	}

	public PlayerTank(int player, Texture tTexture, Texture gTexture, Color color, float x, float y) {
		super(tTexture, gTexture, color, x, y);
		playerNumber = player;
	}

	public void act(float delta) {
		if (controls.downPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration"), 180 + getRotation());
		} else if (controls.upPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration"), getRotation());
		}
		if (controls.leftPressed()) {
			super.rotateBy(delta * stats.getStatValue("Angular_Velocity"));
		} else if (controls.rightPressed()) {
			super.rotateBy(delta * -1 * stats.getStatValue("Angular_Velocity"));
		}
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float)(getWidth())/2, 0);
		v.setAngle(direction);
		v.rotate(45);
		for(int i = 0; i < 4; i++) {
			f[i*2] = x +v.x;
			f[i*2+1] = y + v.y;
			v.rotate(90);
		}
		return new Polygon(f);
	}


}
