package com.tank.controls;

import java.util.LinkedHashMap;

import com.badlogic.gdx.Gdx;

public class KeyboardMouseController extends TankController {
	private LinkedHashMap<String, KeyControl> keyMap;

	public boolean upPressed() {
		KeyControl up = keyMap.get("UP");
		if (up.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(up.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(up.getKeyCode());
		}
	}
	public boolean downPressed() {
		KeyControl down = keyMap.get("DOWN");
		if (down.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(down.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(down.getKeyCode());
		}
	}
	public boolean rightPressed() {
		KeyControl right = keyMap.get("RIGHT");
		if (right.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(right.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(right.getKeyCode());
		}
	}
	public boolean leftPressed() {
		KeyControl left = keyMap.get("LEFT");
		if (left.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(left.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(left.getKeyCode());
		}
	}
	public boolean firePressed() {
		KeyControl shoot = keyMap.get("SHOOT");
		if (shoot.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(shoot.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(shoot.getKeyCode());
		}
	}
	public boolean subPressed() {
		KeyControl sub = keyMap.get("SUB");
		if (sub.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(sub.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(sub.getKeyCode());
		}
	}
	public boolean subRightPressed() {
		KeyControl rSwitch = keyMap.get("RSWITCH");
		if (rSwitch.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(rSwitch.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(rSwitch.getKeyCode());
		}
	}
	public boolean subLeftPressed() {
		KeyControl lSwitch = keyMap.get("LSWITCH");
		if (lSwitch.getKeyType() == 0) {
			return Gdx.input.isKeyPressed(lSwitch.getKeyCode());
		}
		else {
			return Gdx.input.isButtonPressed(lSwitch.getKeyCode());
		}
	}
	public float[] moveCursor(float x, float y) {
		return null;
	}
}