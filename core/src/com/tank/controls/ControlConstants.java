package com.tank.controls;

import java.util.LinkedHashMap;
import com.badlogic.gdx.Input;

public class ControlConstants {
	public static final LinkedHashMap<String, KeyControl> DEFAULT_KEYBOARD_CONTROLS = new LinkedHashMap<String, KeyControl>();
	public static final LinkedHashMap<String, KeyControl> DEFAULT_GAMEPAD_CONTROLS = new LinkedHashMap<String, KeyControl>();
	public static LinkedHashMap<Integer, TankController> playerControls = new LinkedHashMap<Integer, TankController>();
	
	static {
		DEFAULT_KEYBOARD_CONTROLS.put("UP", new KeyControl(Input.Keys.W,0));
		DEFAULT_KEYBOARD_CONTROLS.put("DOWN", new KeyControl(Input.Keys.S,0));
		DEFAULT_KEYBOARD_CONTROLS.put("RIGHT", new KeyControl(Input.Keys.D,0));
		DEFAULT_KEYBOARD_CONTROLS.put("LEFT", new KeyControl(Input.Keys.A,0));
		DEFAULT_KEYBOARD_CONTROLS.put("SHOOT", new KeyControl(Input.Buttons.LEFT,1));
		DEFAULT_KEYBOARD_CONTROLS.put("SUB", new KeyControl(Input.Buttons.RIGHT,1));
		DEFAULT_KEYBOARD_CONTROLS.put("RSHIFT", new KeyControl(Input.Keys.E,0));
		DEFAULT_KEYBOARD_CONTROLS.put("LSHIFT", new KeyControl(Input.Keys.Q,0));
		DEFAULT_KEYBOARD_CONTROLS.put("PAUSE", new KeyControl(Input.Keys.ESCAPE,0));
		
		DEFAULT_GAMEPAD_CONTROLS.put("UP", new KeyControl(XboxMapping.AXIS_LEFT_Y, 1, -1));
		DEFAULT_GAMEPAD_CONTROLS.put("DOWN", new KeyControl(XboxMapping.AXIS_LEFT_Y, 1, 1));
		DEFAULT_GAMEPAD_CONTROLS.put("RIGHT", new KeyControl(XboxMapping.AXIS_LEFT_X, 1, 1));
		DEFAULT_GAMEPAD_CONTROLS.put("LEFT", new KeyControl(XboxMapping.AXIS_LEFT_X, 1, -1));
		DEFAULT_GAMEPAD_CONTROLS.put("CURSOR-LEFT", new KeyControl(XboxMapping.AXIS_RIGHT_X, 1, -1));
		DEFAULT_GAMEPAD_CONTROLS.put("CURSOR-RIGHT", new KeyControl(XboxMapping.AXIS_RIGHT_X, 1, 1));
		DEFAULT_GAMEPAD_CONTROLS.put("CURSOR-UP", new KeyControl(XboxMapping.AXIS_RIGHT_Y, 1, -1));
		DEFAULT_GAMEPAD_CONTROLS.put("CURSOR-DOWN", new KeyControl(XboxMapping.AXIS_RIGHT_Y, 1, 1));
		DEFAULT_GAMEPAD_CONTROLS.put("SHOOT", new KeyControl(XboxMapping.AXIS_RIGHT_TRIGGER, 1, -1));
		DEFAULT_GAMEPAD_CONTROLS.put("SUB", new KeyControl(XboxMapping.AXIS_LEFT_TRIGGER, 1, 1));
		DEFAULT_GAMEPAD_CONTROLS.put("RSHIFT", new KeyControl(XboxMapping.BUTTON_RB, 0));
		DEFAULT_GAMEPAD_CONTROLS.put("LSHIFT", new KeyControl(XboxMapping.BUTTON_LB, 0));
		DEFAULT_GAMEPAD_CONTROLS.put("PAUSE", new KeyControl(XboxMapping.BUTTON_START, 0));
		
		makeDefaultPlayer1();
		makeDefaultPlayer2();
		makeDefaultPlayer3();
		makeDefaultPlayer4();
	}
	
	public static TankController getPlayerControls(int num) {
		if (playerControls.get(num) == null) {
			switch(num) {
				case 1: makeDefaultPlayer1(); break;
				case 2: makeDefaultPlayer2(); break;
				case 3: makeDefaultPlayer3(); break;
				case 4: makeDefaultPlayer4(); break;
			}
		}
		return playerControls.get(new Integer(num));
	}
	
	public static void setPlayerKeyboardController(int num) {
		playerControls.put(num, new KeyboardMouseController());
	}
	
	public static void setPlayerController(int num, TankController controller) {
		playerControls.put(new Integer(num), controller);
	}
	
	private static void makeDefaultPlayer1() {
		playerControls.put(1, new KeyboardMouseController());
	}
	
	private static void makeDefaultPlayer2() {
		try {
			playerControls.put(2, new GamepadController());
		}
		catch (Exception e) {
			playerControls.put(2, new KeyboardMouseController());
		}
	}

	private static void makeDefaultPlayer3() {
		try {
			playerControls.put(3, new GamepadController());
		}
		catch (Exception e) {
			playerControls.put(3, new KeyboardMouseController());
		}
	}
	
	private static void makeDefaultPlayer4() {
		try {
			playerControls.put(4, new GamepadController());
		}
		catch (Exception e) {
			playerControls.put(4, new KeyboardMouseController());
		}
	}
}
