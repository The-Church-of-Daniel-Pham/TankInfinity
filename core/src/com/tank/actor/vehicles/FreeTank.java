package com.tank.actor.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.tank.utils.Assets;

public abstract class FreeTank extends AbstractVehicle {
	protected Texture treadTexture;
	protected Texture gunTexture;
	protected Texture debug = Assets.manager.get(Assets.vertex);
	protected float gunOffsetX;
	protected float gunOffsetY;
	protected float gunPivotX;
	protected float gunPivotY;
	protected float bulletOffset;
	protected float gunRotation;

	public FreeTank(float x, float y) {
		super(x, y);
		initiliazeCustom("default", "default");
		initiliazeHitbox();
		super.setOrigin(treadTexture.getWidth() / 2, treadTexture.getHeight() / 2);
		setGunPivot(treadTexture.getWidth() / 2, treadTexture.getWidth() / 2);
	}

	public FreeTank(float x, float y, String tColor, String gColor) {
		super(x, y);
		initiliazeCustom(tColor, gColor);
		initiliazeHitbox();
		super.setOrigin(treadTexture.getWidth() / 2, treadTexture.getHeight() / 2);
		setGunPivot(treadTexture.getWidth() / 2, treadTexture.getWidth() / 2);
	}
	
	protected void initiliazeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	protected void initiliazeCustom(String tColor, String gColor) {
		custom.addCustom("tread", tColor);
		custom.addCustom("gun", gColor);
		treadTexture = super.custom.getTexture("tread");
		gunTexture = super.custom.getTexture("gun");
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

	public void pointGunToMouse() {
		gunRotation = 0;
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		getStage().getCamera().unproject(mousePos); // to world coordinates
		gunRotation = (float) Math.toDegrees(Math.atan2((mousePos.y - getY()), (mousePos.x - getX())));
	}

	public void draw(Batch batch, float a) {
		batch.draw(treadTexture, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), treadTexture.getWidth(), treadTexture.getHeight(), 1, 1,
				super.getRotation(), 0, 0, treadTexture.getWidth(), treadTexture.getHeight(), false, false);
		batch.draw(gunTexture, super.getX() - gunPivotX, super.getY() - gunPivotY, gunPivotX, gunPivotY,
				gunTexture.getWidth(), gunTexture.getHeight(), 1, 1, gunRotation, 0, 0, gunTexture.getWidth(),
				gunTexture.getHeight(), false, false);
		for (int i = 0; i < 4; i++) {
			batch.draw(debug, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1], 0, 0,
					debug.getWidth(), debug.getHeight(), 1, 1, 0, 0, 0, debug.getWidth(), debug.getHeight(), false,
					false);
		}
	}
}
