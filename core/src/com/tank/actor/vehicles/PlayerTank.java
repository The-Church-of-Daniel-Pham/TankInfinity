package com.tank.actor.vehicles;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.ui.Cursor;
import com.tank.controls.TankController;
import com.tank.interfaces.Collidable;
import com.tank.subweapons.SubWeapon;

public class PlayerTank extends FreeTank implements InputProcessor {

	protected TankController controls;
	protected Cursor cursor;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;
	
	public static float RATE_OF_FIRE = 1.0f;

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

	public ArrayList<Polygon> getHitbox() {
		ArrayList<Polygon> a = new ArrayList<Polygon>();
		float[] f = new float[8];
		Vector2 v = new Vector2((float)(getWidth())/2, 0);
		v.setAngle(getRotation());
		v.rotate(45);
		for(int i = 0; i < 4; i++) {
			f[i*2] = v.x;
			f[i*2+1] = v.y;
			v.rotate(90);
		}
		a.add(new Polygon(f));
		return a;
	}

	public void checkCollision(Collidable other) {
		ArrayList<Polygon> thisHitbox = getHitbox();
		ArrayList<Polygon> otherHitbox = other.getHitbox();
		for(Polygon t: thisHitbox) {
			for(Polygon o: otherHitbox) {
				if(Intersector.intersectPolygons(t,  o, new Polygon())) {
					//TODO something happens
				}
			}
		}
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
