package com.tank.controls;

import java.util.LinkedHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;

public class GamepadController extends TankController {
	public Controller controller;
	private float sensitivity = 30;
	private LinkedHashMap<String, KeyControl> keyMap;

	public GamepadController() {
		keyMap = new LinkedHashMap<String, KeyControl>();
		keyMap.putAll(ControlConstants.DEFAULT_GAMEPAD_CONTROLS);
		controller = Controllers.getControllers().first();
		
//		Controllers.addListener(new ControllerListener() {
//			public int indexOf(Controller controller) {
//				return Controllers.getControllers().indexOf(controller, true);
//			}
//
//			@Override
//			public void connected(Controller controller) {
//				System.out.println("connected " + controller.getName());
//				int i = 0;
//				for (Controller c : Controllers.getControllers()) {
//					System.out.println("#" + i++ + ": " + c.getName());
//				}
//			}
//
//			@Override
//			public void disconnected(Controller controller) {
//				System.out.println("disconnected " + controller.getName());
//				int i = 0;
//				for (Controller c : Controllers.getControllers()) {
//					System.out.println("#" + i++ + ": " + c.getName());
//				}
//				if (Controllers.getControllers().size == 0)
//					System.out.println("No controllers attached");
//			}
//
//			@Override
//			public boolean buttonDown(Controller controller, int buttonIndex) {
//				System.out.println("#" + indexOf(controller) + ", button " + buttonIndex + " down");
//				return false;
//			}
//
//			@Override
//			public boolean buttonUp(Controller controller, int buttonIndex) {
//				System.out.println("#" + indexOf(controller) + ", button " + buttonIndex + " up");
//				return false;
//			}
//
//			@Override
//			public boolean axisMoved(Controller controller, int axisIndex, float value) {
//				System.out.println("#" + indexOf(controller) + ", axis " + axisIndex + ": " + value);
//				return false;
//			}
//
//			@Override
//			public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
//				System.out.println("#" + indexOf(controller) + ", pov " + povIndex + ": " + value);
//				return false;
//			}
//
//			@Override
//			public boolean xSliderMoved(Controller controller, int sliderIndex, boolean value) {
//				System.out.println("#" + indexOf(controller) + ", x slider " + sliderIndex + ": " + value);
//				return false;
//			}
//
//			@Override
//			public boolean ySliderMoved(Controller controller, int sliderIndex, boolean value) {
//				System.out.println("#" + indexOf(controller) + ", y slider " + sliderIndex + ": " + value);
//				return false;
//			}
//
//			@Override
//			public boolean accelerometerMoved(Controller controller, int accelerometerIndex, Vector3 value) {
//				// not printing this as we get to many values
//				return false;
//			}
//		});
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
				return (controller.getAxis(up.getKeyCode()) <= -0.75);
			}
			else if (up.getDirection() > 0) {
				return (controller.getAxis(up.getKeyCode()) >= 0.75);
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
				return (controller.getAxis(down.getKeyCode()) <= -0.75);
			}
			else if (down.getDirection() > 0) {
				return (controller.getAxis(down.getKeyCode()) >= 0.75);
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
				return (controller.getAxis(right.getKeyCode()) <= -0.75);
			}
			else if (right.getDirection() > 0) {
				return (controller.getAxis(right.getKeyCode()) >= 0.75);
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
				return (controller.getAxis(left.getKeyCode()) <= -0.75);
			}
			else if (left.getDirection() > 0) {
				return (controller.getAxis(left.getKeyCode()) >= 0.75);
			}
				
		}
		return false;
	}

	public boolean firePressed() {
		if (controller == null) {
			return false;
		}
		KeyControl shoot = keyMap.get("SHOOT");
		if (shoot.getKeyType() == 0) {
			return controller.getButton(shoot.getKeyCode());
		}
		else {
			if (shoot.getDirection() < 0) {
				return (controller.getAxis(shoot.getKeyCode()) <= -0.75);
			}
			else if (shoot.getDirection() > 0) {
				return (controller.getAxis(shoot.getKeyCode()) >= 0.75);
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
				return (controller.getAxis(sub.getKeyCode()) <= -0.75);
			}
			else if (sub.getDirection() > 0) {
				return (controller.getAxis(sub.getKeyCode()) >= 0.75);
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
				return (controller.getAxis(rSwitch.getKeyCode()) <= -0.75);
			}
			else if (rSwitch.getDirection() > 0) {
				return (controller.getAxis(rSwitch.getKeyCode()) >= 0.75);
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
				return (controller.getAxis(lSwitch.getKeyCode()) <= -0.75);
			}
			else if (lSwitch.getDirection() > 0) {
				return (controller.getAxis(lSwitch.getKeyCode()) >= 0.75);
			}
				
		}
		return false;
	}
	
	public void testButtons() {
		for (int i = 0; i < 100; i++) {
			if (controller.getButton(i)) System.out.println("Pressed Button " + i);
		}
		for (int i = 0; i < 100; i++) {
			if (Math.abs(controller.getAxis(i)) > 0.3) System.out.println("Axis " + i + " moved: " + controller.getAxis(i));
		}
		for (int i = 0; i< 100; i++) {
			if (!controller.getPov(i).equals(PovDirection.center)) System.out.println("POV " + i + ": " + controller.getPov(i).toString());
		}
	}

	public Vector3 getCursor(Vector3 oldCursor) {
		if (controller == null) {
			return null;
		}
		KeyControl vertical = keyMap.get("CURSOR-V");
		KeyControl horizontal = keyMap.get("CURSOR-H");
	
		return new Vector3(oldCursor.x + sensitivity * controller.getAxis(horizontal.getKeyCode()),
				oldCursor.y + sensitivity * controller.getAxis(vertical.getKeyCode()), 0);
	}
}
