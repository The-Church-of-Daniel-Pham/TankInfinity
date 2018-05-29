package com.tank.controls;

import java.util.LinkedHashMap;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;

public class GamepadController extends TankController {
	public Controller game;
	private final int id;
	private boolean connected;
	private LinkedHashMap<String, Integer> keyMap;

	public GamepadController(int id) {
		this.id = id;
		connected = false;
		keyMap = new LinkedHashMap<String, Integer>();
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
	}

	public int getID() {
		return id;
	}

	public boolean upPressed() {
		if (connected) {
			return game.getAxis(keyMap.get("UP")) == -1;
		}
		return false;
	}

	public boolean downPressed() {
		if (connected) {
			return game.getAxis(keyMap.get("DOWN")) == 1;
		}
		return false;
	}

	public boolean rightPressed() {
		if (connected) {
			return game.getAxis(keyMap.get("RIGHT")) == 1;
		}
		return false;
	}

	public boolean leftPressed() {
		if (connected) {
			return game.getAxis(keyMap.get("LEFT")) == -1;
		}
		return false;
	}

	public boolean firePressed() {
		if (connected) {
			return game.getButton(keyMap.get("SHOOT"));
		}
		return false;
	}

	public boolean subPressed() {
		if (connected) {
			return game.getButton(keyMap.get("SUB"));
		}
		return false;
	}

	public boolean subRightPressed() {
		if (connected) {
			return game.getButton(keyMap.get("RSWITCH"));
		}
		return false;
	}

	public boolean subLeftPressed() {
		if (connected) {
			return game.getButton(keyMap.get("LSWITCH"));
		}
		return false;
	}

	public Vector3 getCursor() {
		if (connected) {
			return new Vector3(game.getAxis(Xbox.R_STICK_HORIZONTAL_AXIS), game.getAxis(Xbox.R_STICK_VERTICAL_AXIS), 0);
		}
		return null;
	}
}
