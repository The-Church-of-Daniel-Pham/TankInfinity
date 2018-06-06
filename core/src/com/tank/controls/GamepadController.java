package com.tank.controls;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GamepadController extends TankController {
	public int index;
	private LinkedHashMap<String, KeyControl> keyMap;
	private float deadzone = 0.30f;
	private float sensitivity = 15f;
	private static ArrayList<Integer> inUse = new ArrayList<Integer>();
	private static Array<Controller> controllers = Controllers.getControllers();

	public GamepadController() throws Exception {
		keyMap = new LinkedHashMap<String, KeyControl>();
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
		index = -1;
		for (int i = 0; i < controllers.size; i++) {
			boolean using = false;
			for (Integer integer : inUse) {
				if (integer.intValue() == i) {
					using = true;
					break;
				}
			}
			if (!using) {
				this.index = i;
				inUse.add(index);
				break;
			}
		}
		if (index == -1) throw new Exception("No more controllers");
	}
	
	public GamepadController(int index) {
		this.index = index;
		keyMap = new LinkedHashMap<String, KeyControl>();
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
	}

	public void setDeadzone(float d) {
		deadzone = d;
	}
	
	public void setSensitivity(float s) {
		sensitivity = s;
	}

	public boolean upPressed() {
		return (keyControlPressed(keyMap.get("UP")));
	}

	public boolean downPressed() {
		return (keyControlPressed(keyMap.get("DOWN")));
	}

	public boolean rightPressed() {
		return (keyControlPressed(keyMap.get("RIGHT")));
	}

	public boolean leftPressed() {
		return (keyControlPressed(keyMap.get("LEFT")));
	}

	public boolean firePressed() {
		return (keyControlPressed(keyMap.get("SHOOT")));
	}

	public boolean subPressed() {
		return (keyControlPressed(keyMap.get("SUB")));
	}

	public boolean subRightPressed() {
		return (keyControlPressed(keyMap.get("RSHIFT")));
	}

	public boolean subLeftPressed() {
		return (keyControlPressed(keyMap.get("LSHIFT")));
	}
	
	public boolean pausePressed() {
		return (keyControlPressed(keyMap.get("PAUSE")));
	}
	
	public boolean keyControlPressed(KeyControl key) {
		if (index >= controllers.size || controllers.get(index) == null) {
			return false;
		}
		if (key.getKeyType() == 0) {
			return controllers.get(index).getButton(key.getKeyCode());
		}
		else if (key.getKeyType() == 1){
			if (key.getDirection() < 0) {
				return (controllers.get(index).getAxis(key.getKeyCode()) <= -deadzone);
			}
			else if (key.getDirection() > 0) {
				return (controllers.get(index).getAxis(key.getKeyCode()) >= deadzone);
			}
				
		}
		else if (key.getKeyType() == 2) {
			return generalPovClicked(key.getKeyCode(), key.getDirection());
		}
		return false;
	}
	
	public boolean generalPovClicked(int index, int direction) {
		if (direction == 1) {
			return controllers.get(index).getPov(index).equals(PovDirection.values()[1]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[5]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[7]);
		}
		if (direction == 2) {
			return controllers.get(index).getPov(index).equals(PovDirection.values()[2]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[6]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[8]);
		}
		if (direction == 3) {
			return controllers.get(index).getPov(index).equals(PovDirection.values()[3]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[5]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[6]);
		}
		if (direction == 4) {
			return controllers.get(index).getPov(index).equals(PovDirection.values()[4]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[7]) ||
					 controllers.get(index).getPov(index).equals(PovDirection.values()[8]);
		}
		return false;
	}

	public void testButtons() {
		for (int i = 0; i < 100; i++) {
			if (controllers.get(index).getButton(i))
				System.out.println("Pressed Button " + i);
		}
		for (int i = 0; i < 100; i++) {
			if (Math.abs(controllers.get(index).getAxis(i)) > 0.3)
				System.out.println("Axis " + i + " moved: " + controllers.get(index).getAxis(i));
		}
		for (int i = 0; i < 100; i++) {
			if (!controllers.get(index).getPov(i).equals(PovDirection.center))
				System.out.println("POV " + i + ": " + controllers.get(index).getPov(i).toString());
		}
	}

	public Vector3 getCursor(Vector3 oldCursor) {
		if (index >= controllers.size || controllers.get(index) == null) 
			return oldCursor;
		float newX = oldCursor.x;
		float newY = oldCursor.y;
		newX = cursorLeft(newX); newX = cursorRight(newX);
		newY = cursorUp(newY); newY = cursorDown(newY);
		
		return new Vector3(newX, newY, 0);
	}
	
	public float cursorUp(float y) {
		KeyControl cursorUp = keyMap.get("CURSOR-UP");
		return cursorMoved(cursorUp, -1, y);
	}
	
	public float cursorDown(float y) {
		KeyControl cursorDown = keyMap.get("CURSOR-DOWN");
		return cursorMoved(cursorDown, 1, y);
	}
	
	public float cursorLeft(float x) {
		KeyControl cursorLeft = keyMap.get("CURSOR-LEFT");
		return cursorMoved(cursorLeft, -1, x);
	}
	
	public float cursorRight(float x) {
		KeyControl cursorRight = keyMap.get("CURSOR-RIGHT");
		return cursorMoved(cursorRight, 1, x);
	}
	
	public float cursorMoved (KeyControl key, int direction, float pos) {
		if (key.getKeyType() == 0) {
			if (controllers.get(index).getButton(key.getKeyCode())) {
				return pos + sensitivity * direction;
			}
			else {
				return pos;
			}
		}
		else if (key.getKeyType() == 1){
			if (key.getDirection() < 0) {
				if (controllers.get(index).getAxis(key.getKeyCode()) < -deadzone) {
					return pos + Math.abs(controllers.get(index).getAxis(key.getKeyCode())) * sensitivity * direction;
				}
				else {
					return pos;
				}
			}
			else if (key.getDirection() > 0) {
				if (controllers.get(index).getAxis(key.getKeyCode()) > deadzone) {
					return pos + Math.abs(controllers.get(index).getAxis(key.getKeyCode())) * sensitivity * direction;
				}
				else {
					return pos;
				}
			}
				
		}
		else if (key.getKeyType() == 2) {
			if (generalPovClicked(key.getKeyCode(), key.getDirection())) {
				return pos + sensitivity * direction;
			}
			else {
				return pos;
			}
		}
		return pos;
	}
	
	public void setKey(String key, KeyControl control) {
		keyMap.replace(key, control);
	}

	public KeyControl getKeyControl(String key) {
		return keyMap.get(key);
	}
	
	public int getIndex() {
		return index;
	}
	
	public Controller getController() {
		return (controllers.get(index));
	}
	
	public static int getControllerAmount() {
		return controllers.size;
	}
	
	public void setToDefault() {
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
	}
}
