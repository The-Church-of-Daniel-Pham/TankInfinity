package com.tank.actor.vehicles;

import java.util.ArrayList;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.ui.Cursor;
import com.tank.controls.KeyboardMouseController;
import com.tank.controls.TankController;
import com.tank.interfaces.Collidable;
import com.tank.stats.Stats;
import com.tank.subweapons.SubWeapon;

public class PlayerTank extends FreeTank implements InputProcessor {

	protected TankController controls;
	protected Cursor cursor;
	protected static Texture tTexture; 
	protected static Texture gTexture;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;
	
	public static float RATE_OF_FIRE = 1.0f;
	protected double timeSinceShot;

	public PlayerTank(int player, float x, float y) {
		super(x, y);
		playerNumber = player;
		controls = new KeyboardMouseController();

	}
	
	@Override
	protected void setStats() {
		stats = new Stats();
		stats.addStat("Acceleration", 10);
		stats.addStat("Angular_Velocity", 2);
		
	}	

	public PlayerTank(int player, Texture tTexture, Texture gTexture, Color color, float x, float y) {
		super(tTexture, gTexture, color, x, y);
		playerNumber = player;
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
			super.rotateBy(delta * stats.getStatValue("Angular_Velocity"));
		} else if (controls.rightPressed()) {
			super.rotateBy(delta * -1 * stats.getStatValue("Angular_Velocity"));
		}
		updateVelocityAndMove();
		if(controls.firePressed()) {
			shoot();
		}
	}
	public void shoot() {
		getStage().addActor(new Bullet(null, null, this, getX(), getY(), getRotation()));
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

	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public float getReloadTime() {
		return 0f;	//write later
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
		// TODO Auto-generated method stub
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
