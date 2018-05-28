package com.tank.actor.vehicles;

import java.util.ArrayList;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.ui.Cursor;
import com.tank.controls.KeyboardMouseController;
import com.tank.controls.TankController;
import com.tank.stats.Stats;
import com.tank.subweapons.SubWeapon;
import com.tank.utils.Assets;

public class PlayerTank extends FreeTank implements InputProcessor {

	protected TankController controls;
	protected Cursor cursor;
	protected static Texture tTexture = Assets.manager.get(Assets.tread_red);
	protected static Texture gTexture = Assets.manager.get(Assets.gun_red);
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;

	protected double timeSinceShot;

	public PlayerTank(int player, Color color, float x, float y) {
		super(tTexture, gTexture, color, x, y);
		super.setGunPivotX(treadTexture.getWidth() / 2 - 12);
		super.setGunPivotY(treadTexture.getHeight() / 2);
		playerNumber = player;
		controls = new KeyboardMouseController();
		timeSinceShot = 0;
	}

	@Override
	protected void setStats() {
		stats = new Stats();
		stats.addStat("Friction", 96); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 1000);
		stats.addStat("Max_Speed", 0); // used for?
		stats.addStat("Angular_Friction", 98);
		stats.addStat("Angular_Acceleration", 250);
		stats.addStat("Max_Angular_Speed", 4);
		stats.addStat("Rate_Of_Fire", 1);
	}

	/**
	 * updates the velocity based on user input and tank stats
	 */
	public void act(float delta) {
		if (controls.downPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration"), 180 + getRotation());
		} else if (controls.upPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration"), getRotation());
		}
		if (controls.leftPressed()) {
			super.applyAngularForce(delta * stats.getStatValue("Angular_Acceleration"));
		} else if (controls.rightPressed()) {
			super.applyAngularForce(-1 * delta * stats.getStatValue("Angular_Acceleration"));
		}
		super.applyFriction(delta);
		super.move(delta);
		super.pointGunToMouse();
		if (controls.firePressed() && timeSinceShot > 1.0 / stats.getStatValue("Rate_Of_Fire")) {
			shoot();
			timeSinceShot = 0;
		} else {
			timeSinceShot += delta;
		}
	}

	public void shoot() {
		Vector2 v = new Vector2(AbstractVehicle.TANKGUNLENGTH, 0);
		v.setAngle(getGunRotation());
		getStage().addActor(new Bullet(this, getX() + v.x, getY() + v.y, super.gunRotation));
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float) (tTexture.getWidth()) / 2, 0);
		v.setAngle(direction);
		v.rotate(45);

		for (int i = 0; i < 4; i++) {
			f[i * 2] = x + v.x;
			f[i * 2 + 1] = y + v.y;
			v.rotate(90);
		}
		return new Polygon(f);

	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public float getReloadTime() {
		return 0f; // write later
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
