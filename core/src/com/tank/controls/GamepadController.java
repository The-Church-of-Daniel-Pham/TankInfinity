package com.tank.controls;

import java.util.LinkedHashMap;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class GamepadController extends TankController {
	public Controller controller;
	private float tolerance = 0.75f;
	private LinkedHashMap<String, Integer> keyMap;

	public GamepadController() {
		keyMap = new LinkedHashMap<String, Integer>();
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
		controller = Controllers.getControllers().first();
	}

	public void setTolerance(float tol) {
		tolerance = tol;
	}

	public boolean upPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("UP")) < -tolerance;
	}

	public boolean downPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("DOWN")) > tolerance;
	}

	public boolean rightPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("RIGHT")) > tolerance;
	}

	public boolean leftPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("LEFT")) < -tolerance;
	}

	public boolean firePressed() {
		// testButtons();
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("SHOOT")) < -tolerance;
	}

	public boolean subPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("SUB")) > tolerance;
	}

	public boolean subRightPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getButton(keyMap.get("RSWITCH"));
	}

	public boolean subLeftPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getButton(keyMap.get("LSWITCH"));
	}

	public void testButtons() {
		for (int i = 0; i < 100; i++) {
			if (controller.getButton(i))
				System.out.println("Pressed Button " + i);
		}
		for (int i = 0; i < 100; i++) {
			if (Math.abs(controller.getAxis(i)) > 0.3)
				System.out.println("Axis " + i + " moved: " + controller.getAxis(i));
		}
		for (int i = 0; i < 100; i++) {
			if (!controller.getPov(i).equals(PovDirection.center))
				System.out.println("POV " + i + ": " + controller.getPov(i).toString());
		}
	}

	public Vector3 getCursor() {
		if (controller == null) {
			return null;
		}
		return new Vector3(controller.getAxis(keyMap.get("XPOINT")), controller.getAxis(keyMap.get("YPOINT")), 0);
	}
}
