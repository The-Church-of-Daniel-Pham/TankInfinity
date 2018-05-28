package com.tank.controls;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.badlogic.gdx.Input;

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
		DEFAULT_KEYBOARD_CONTROLS.put("SUB", new KeyControl(Input.Buttons.RIGHT,0));
		DEFAULT_KEYBOARD_CONTROLS.put("RSHIFT", new KeyControl(Input.Keys.E,0));
		DEFAULT_KEYBOARD_CONTROLS.put("LSHIFT", new KeyControl(Input.Keys.Q,0));
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
		LinkedHashMap<String, KeyControl> keys = new LinkedHashMap<String, KeyControl>();
		keys.put("UP", new KeyControl(Input.Keys.DPAD_UP,0));
		keys.put("DOWN", new KeyControl(Input.Keys.DPAD_DOWN,0));
		keys.put("RIGHT", new KeyControl(Input.Keys.DPAD_RIGHT,0));
		keys.put("LEFT", new KeyControl(Input.Keys.DPAD_LEFT,0));
		keys.put("SHOOT", new KeyControl(Input.Buttons.LEFT,1));
		keys.put("SUB", new KeyControl(Input.Buttons.RIGHT,0));
		keys.put("RSHIFT", new KeyControl(Input.Keys.COMMA,0));
		keys.put("LSHIFT", new KeyControl(Input.Keys.PERIOD,0));
		playerControls.put(2, new KeyboardMouseController(keys));
	}

	private static void makeDefaultPlayer3() {
		LinkedHashMap<String, KeyControl> keys = new LinkedHashMap<String, KeyControl>();
		keys.put("UP", new KeyControl(Input.Keys.NUMPAD_8,0));
		keys.put("DOWN", new KeyControl(Input.Keys.NUMPAD_2,0));
		keys.put("RIGHT", new KeyControl(Input.Keys.NUMPAD_6,0));
		keys.put("LEFT", new KeyControl(Input.Keys.NUMPAD_4,0));
		keys.put("SHOOT", new KeyControl(Input.Buttons.LEFT,1));
		keys.put("SUB", new KeyControl(Input.Buttons.RIGHT,0));
		keys.put("RSHIFT", new KeyControl(Input.Keys.NUMPAD_9,0));
		keys.put("LSHIFT", new KeyControl(Input.Keys.NUMPAD_7,0));
		playerControls.put(3, new KeyboardMouseController(keys));
	}
	
	private static void makeDefaultPlayer4() {
		LinkedHashMap<String, KeyControl> keys = new LinkedHashMap<String, KeyControl>();
		keys.put("UP", new KeyControl(Input.Keys.U,0));
		keys.put("DOWN", new KeyControl(Input.Keys.J,0));
		keys.put("RIGHT", new KeyControl(Input.Keys.K,0));
		keys.put("LEFT", new KeyControl(Input.Keys.H,0));
		keys.put("SHOOT", new KeyControl(Input.Buttons.LEFT,1));
		keys.put("SUB", new KeyControl(Input.Buttons.RIGHT,0));
		keys.put("RSHIFT", new KeyControl(Input.Keys.I,0));
		keys.put("LSHIFT", new KeyControl(Input.Keys.Y,0));
		playerControls.put(4, new KeyboardMouseController(keys));
	}
}
