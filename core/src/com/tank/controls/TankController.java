package com.tank.controls;

import com.badlogic.gdx.math.Vector3;

public abstract class TankController {
	public abstract boolean upPressed();
	public abstract boolean downPressed();
	public abstract boolean rightPressed();
	public abstract boolean leftPressed();
	public abstract boolean firePressed();
	public abstract boolean subPressed();
	public abstract boolean subRightPressed();
	public abstract boolean subLeftPressed();
	public abstract Vector3 getCursor(Vector3 oldCursor);
}
