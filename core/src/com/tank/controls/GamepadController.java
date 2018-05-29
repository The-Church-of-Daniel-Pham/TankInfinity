package com.tank.controls;

import java.util.LinkedHashMap;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;

public class GamepadController extends TankController {
	public Controller controller;
	private LinkedHashMap<String, Integer> keyMap;

	public GamepadController() {
		keyMap = new LinkedHashMap<String, Integer>();
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
		return controller.getAxis(keyMap.get("UP")) == -1;
	}

	public boolean downPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("DOWN")) == 1;
	}

	public boolean rightPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("RIGHT")) == 1;
	}

	public boolean leftPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("LEFT")) == -1;
	}

	public boolean firePressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("SHOOT")) == -1;
	}

	public boolean subPressed() {
		if (controller == null) {
			return false;
		}
		return controller.getAxis(keyMap.get("SUB")) == 1;
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

	public Vector3 getCursor() {
		if (controller == null) {
			return null;
		}
		return new Vector3(controller.getAxis(Xbox.R_STICK_HORIZONTAL_AXIS),
				controller.getAxis(Xbox.R_STICK_VERTICAL_AXIS), 0);
	}
}
