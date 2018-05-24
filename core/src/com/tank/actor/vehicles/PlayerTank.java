package com.tank.actor.vehicles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
			super.applyForce(stats.getStatValue("Acceleration"), 180 + getRotation());
		} else if (controls.upPressed()) {
			super.applyForce(stats.getStatValue("Acceleration"), getRotation());
		}
		if (controls.leftPressed()) {
			super.rotateBy(stats.getStatValue("Angular_Velocity"));
		} else if (controls.rightPressed()) {
			super.rotateBy(-1 * stats.getStatValue("Angular_Velocity"));
		}
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public ArrayList<Polygon> getHitbox() {
		ArrayList<Polygon> a = new ArrayList<Polygon>();
		float[] f = new float[8];
		Vector2 v = new Vector2((float)(getWidth())/2, 0);
		v.setAngle(getRotation());
		v.rotate(45);
		a.add(new Polygon(f));
		return a;
	}

	public void checkCollision(Collidable other) {

	}
}
