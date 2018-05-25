package com.tank.actor.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class FreeTank extends AbstractVehicle {

	protected Texture treadTexture;
	protected Texture gunTexture;
	protected float gunOffsetX;
	protected float gunOffsetY;
	protected float gunPivotX;
	protected float gunPivotY;
	protected float gunRotation;
	protected Color color;

	public FreeTank( float x, float y) {
		super(x, y);
		color = null;
	}
	public FreeTank(Texture tTexture, Texture gTexture, Color color, float x, float y) {
		super(x, y);
		treadTexture = tTexture;
		gunTexture = gTexture;
		this.color = color;
	}

	public void setGunOffset(float x, float y) {
		gunOffsetX = x;
		gunOffsetY = y;
	}
	public void setGunOffsetX(float x) {
		gunOffsetX = x;
	}
	public void setGunOffsetY(float y) {
		gunOffsetY = y;
	}
	public void setGunPivot(float x, float y) {
		gunPivotX = x;
		gunPivotY = y;
	}
	public void setGunPivotX(float x) {
		gunPivotX = x;
	}
	public void setGunPivotY(float y) {
		gunPivotY = y;
	}
	public float getGunOffsetX() {
		return gunOffsetX;
	}
	public float getGunOffsetY() {
		return gunOffsetY;
	}
	public float getGunPivotX() {
		return gunPivotX;
	}
	public float getGunPivotY() {
		return gunPivotY;
	}
	public void rotateGun(float rotation) {
		gunRotation += rotation;
	}
	public void setGunRotation(float rotation) {
		gunRotation = rotation;
	}
	public float getGunRotation() {
		return gunRotation;
	}
	public void draw(Batch batch, float a) {
		
	}
}
