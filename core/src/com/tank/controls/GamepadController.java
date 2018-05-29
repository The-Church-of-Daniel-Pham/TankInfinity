package com.tank.controls;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class GamepadController extends TankController {
	public Controller controller;
	private LinkedHashMap<String, KeyControl> keyMap;
	private float deadzone = 0.25f;
	private float sensitivity = 15f;
	private static ArrayList<Controller> inUse = new ArrayList<Controller>();

	public GamepadController() throws Exception {
		keyMap = new LinkedHashMap<String, KeyControl>();
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
		for (Controller controller : Controllers.getControllers()) {
			if (!inUse.contains(controller)) {
				this.controller = controller;
				inUse.add(controller);
				break;
			}
		}
		if (controller == null) throw new Exception("No more controllers");
	}

	public void setDeadzone(float d) {
		deadzone = d;
	}
	
	public void setSensitivity(float s) {
		sensitivity = s;
	}

	public boolean upPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl up = keyMap.get("UP");
		if (up.getKeyType() == 0) {
			return controller.getButton(up.getKeyCode());
		}
		else {
			if (up.getDirection() < 0) {
				return (controller.getAxis(up.getKeyCode()) <= -deadzone);
			}
			else if (up.getDirection() > 0) {
				return (controller.getAxis(up.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean downPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl down = keyMap.get("DOWN");
		if (down.getKeyType() == 0) {
			return controller.getButton(down.getKeyCode());
		}
		else {
			if (down.getDirection() < 0) {
				return (controller.getAxis(down.getKeyCode()) <= -deadzone);
			}
			else if (down.getDirection() > 0) {
				return (controller.getAxis(down.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean rightPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl right = keyMap.get("RIGHT");
		if (right.getKeyType() == 0) {
			return controller.getButton(right.getKeyCode());
		}
		else {
			if (right.getDirection() < 0) {
				return (controller.getAxis(right.getKeyCode()) <= -deadzone);
			}
			else if (right.getDirection() > 0) {
				return (controller.getAxis(right.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean leftPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl left = keyMap.get("LEFT");
		if (left.getKeyType() == 0) {
			return controller.getButton(left.getKeyCode());
		}
		else {
			if (left.getDirection() < 0) {
				return (controller.getAxis(left.getKeyCode()) <= -deadzone);
			}
			else if (left.getDirection() > 0) {
				return (controller.getAxis(left.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean firePressed() {
		// testButtons();
		if (controller == null) {
			return false;
		}
		KeyControl shoot = keyMap.get("SHOOT");
		if (shoot.getKeyType() == 0) {
			return controller.getButton(shoot.getKeyCode());
		}
		else {
			if (shoot.getDirection() < 0) {
				return (controller.getAxis(shoot.getKeyCode()) <= -deadzone);
			}
			else if (shoot.getDirection() > 0) {
				return (controller.getAxis(shoot.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean subPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl sub = keyMap.get("SUB");
		if (sub.getKeyType() == 0) {
			return controller.getButton(sub.getKeyCode());
		}
		else {
			if (sub.getDirection() < 0) {
				return (controller.getAxis(sub.getKeyCode()) <= -deadzone);
			}
			else if (sub.getDirection() > 0) {
				return (controller.getAxis(sub.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean subRightPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl rSwitch = keyMap.get("RSHIFT");
		if (rSwitch.getKeyType() == 0) {
			return controller.getButton(rSwitch.getKeyCode());
		}
		else {
			if (rSwitch.getDirection() < 0) {
				return (controller.getAxis(rSwitch.getKeyCode()) <= -deadzone);
			}
			else if (rSwitch.getDirection() > 0) {
				return (controller.getAxis(rSwitch.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
	}

	public boolean subLeftPressed() {
		if (controller == null) {
			return false;
		}
		KeyControl lSwitch = keyMap.get("LSHIFT");
		if (lSwitch.getKeyType() == 0) {
			return controller.getButton(lSwitch.getKeyCode());
		}
		else {
			if (lSwitch.getDirection() < 0) {
				return (controller.getAxis(lSwitch.getKeyCode()) <= -deadzone);
			}
			else if (lSwitch.getDirection() > 0) {
				return (controller.getAxis(lSwitch.getKeyCode()) >= deadzone);
			}
				
		}
		return false;
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

	public Vector3 getCursor(Vector3 oldCursor) {
		if (controller == null) 
			return null;
		KeyControl vertical = keyMap.get("CURSOR-V");
		KeyControl horizontal = keyMap.get("CURSOR-H");
		float newX = oldCursor.x;
		float newY = oldCursor.y;
		if (Math.abs(controller.getAxis(horizontal.getKeyCode())) > 1 - deadzone) newX += sensitivity * controller.getAxis(horizontal.getKeyCode());
		if (Math.abs(controller.getAxis(vertical.getKeyCode())) > 1 - deadzone) newY += sensitivity * controller.getAxis(vertical.getKeyCode());
		return new Vector3(newX, newY, 0);
	}
}
