package com.tank.controls;

public class KeyControl {
	private int keyNumber;
	private int type; //0 = key/gamepad button, 1 = button/axis
	
	public KeyControl(int keyNumber, int type) {
		this.keyNumber = keyNumber;
		this.type = type;
	}
	
	public int getKeyCode() {
		return keyNumber;
	}
	
	public int getKeyType() {
		return type;
	}
}
