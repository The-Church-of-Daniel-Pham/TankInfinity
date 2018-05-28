package com.tank.actor.vehicles;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.ui.Cursor;
import com.tank.controls.ControlConstants;
import com.tank.controls.KeyboardMouseController;
import com.tank.controls.TankController;
import com.tank.stats.Customization;
import com.tank.subweapons.SubWeapon;

public class PlayerTank extends FreeTank implements InputProcessor {
	/**
	 * used for spawning bullets the correct distance away from Vehicle's center
	 */
	public static final int TANK_GUN_LENGTH = 135;	//probably redundant, check free tank
	
	protected TankController controls;
	protected Cursor cursor;
	protected String tColor;
	protected String gColor;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;
	protected Customization custom;

	protected float reloadTime;
	
	public PlayerTank(int playerNumber) {
		super(0, 0);	// defaults
		initializeCustom("default", "default");
		this.playerNumber = playerNumber;
		initializeStats();
		controls = ControlConstants.getPlayerControls(playerNumber);
		reloadTime = 0;
		super.setGunOffsetX(-12);
		super.setGunPivotX(treadTexture.getWidth() / 2 + super.getGunOffsetX());
	}

	public PlayerTank(float x, float y, int playerNumber, String tColor, String gColor) {
		super(x, y);
		initializeCustom(tColor, gColor);
		this.playerNumber = playerNumber;
		initializeStats();
		controls = new KeyboardMouseController();
		reloadTime = 0;
		super.setGunOffsetX(-12);
		super.setGunPivotX(treadTexture.getWidth() / 2 - super.getGunOffsetX());
	}
	
	protected void initializeStats() {
		stats.addStat("Friction", 96); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 1000);
		stats.addStat("Angular_Friction", 98);
		stats.addStat("Angular_Acceleration", 250);
		stats.addStat("Rate_Of_Fire", 1);
	}
	
	protected void initializeCustom(String tColor, String gColor) {
		custom = new Customization();
		custom.setCustom("tread", tColor);
		custom.setCustom("gun", gColor);
		treadTexture = custom.getTexture("tread");
		gunTexture = custom.getTexture("gun");
		this.tColor = tColor;
		this.gColor = gColor;
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
		//Gun Pointing
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		getStage().getCamera().unproject(mousePos); // to world coordinates
		super.pointGunToPoint(mousePos.x, mousePos.y);
		if (controls.firePressed() && reloadTime < 0.01) {	//if almsot done reloading, allow for rounding
			reloadTime = 1.0f / stats.getStatValue("Rate_Of_Fire");
			shoot();
		}
		else if (reloadTime > 0){
			reloadTime -= delta;
		}
	}

	public void shoot() {
		Vector2 v = new Vector2(TANK_GUN_LENGTH, 0);
		v.setAngle(getGunRotation());
		getStage().addActor(new Bullet(this, getX() + v.x, getY() + v.y, super.gunRotation));
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float) (super.treadTexture.getWidth()) / 2, 0);
		v.setAngle(direction);
		v.rotate(45);

		for (int i = 0; i < 4; i++) {
			f[i * 2] = x + v.x;
			f[i * 2 + 1] = y + v.y;
			v.rotate(90);
		}
		return new Polygon(f);

	}
	
	/**
	 * Set Vehicle customization unique to each player tank type
	 */
	public void setCustom(String cust, String val) {
		custom.setCustom(cust, val);
		gunTexture = custom.getTexture("gun");
		treadTexture = custom.getTexture("tread");
	}

	public String getCustom(String cust) {
		return custom.getCustomValue(cust);
	}

	public void cycleCustom(String cust, int n) {
		custom.cycleCustom(cust, n);
		gunTexture = custom.getTexture("gun");
		treadTexture = custom.getTexture("tread");
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public float getReloadTime() {
		return reloadTime;
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
