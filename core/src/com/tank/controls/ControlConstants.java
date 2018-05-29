package com.tank.controls;

import java.util.LinkedHashMap;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.mappings.Xbox;

public class ControlConstants {
	public static final LinkedHashMap<String, KeyControl> DEFAULT_KEYBOARD_CONTROLS = new LinkedHashMap<String, KeyControl>();
	public static final LinkedHashMap<String, Integer> DEFAULT_GAMEPAD_CONTROLS = new LinkedHashMap<String, Integer>();
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
		
		DEFAULT_GAMEPAD_CONTROLS.put("UP", Xbox.L_STICK_VERTICAL_AXIS);
		DEFAULT_GAMEPAD_CONTROLS.put("DOWN", Xbox.L_STICK_VERTICAL_AXIS);
		DEFAULT_GAMEPAD_CONTROLS.put("RIGHT", Xbox.L_STICK_HORIZONTAL_AXIS);
		DEFAULT_GAMEPAD_CONTROLS.put("LEFT", Xbox.L_STICK_HORIZONTAL_AXIS);
		DEFAULT_GAMEPAD_CONTROLS.put("SHOOT", Xbox.R_TRIGGER);
		DEFAULT_GAMEPAD_CONTROLS.put("SUB", Xbox.L_TRIGGER);
		DEFAULT_GAMEPAD_CONTROLS.put("RSHIFT", Xbox.R_BUMPER);
		DEFAULT_GAMEPAD_CONTROLS.put("LSHIFT", Xbox.L_BUMPER);
		
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
	
	private static void makeDefaultPlayer1() {
		playerControls.put(1, new KeyboardMouseController());
	}
	
	private static void makeDefaultPlayer2() {
		playerControls.put(2, new GamepadController(2));
	}

	private static void makeDefaultPlayer3() {
		playerControls.put(3, new GamepadController(3));
	}
	
	private static void makeDefaultPlayer4() {
		playerControls.put(4, new GamepadController(4));
	}
}
