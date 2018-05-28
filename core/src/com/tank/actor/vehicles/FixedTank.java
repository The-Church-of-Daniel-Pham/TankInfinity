package com.tank.actor.vehicles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class FixedTank extends AbstractVehicle {
	protected Texture tankTexture;

	public FixedTank(float x, float y, String color) {
		super(x, y);
		initiliazeCustom(color);
		initiliazeHitbox();
		super.setOrigin(tankTexture.getWidth() / 2, tankTexture.getHeight() / 2);
	}
	
	protected void initiliazeCustom(String color) {
		super.custom.addCustom("tank", color);
		tankTexture = super.custom.getTexture("tank");
	}
	
	protected void initiliazeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	public void draw(Batch batch, float a) {
		batch.draw(tankTexture, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), tankTexture.getWidth(), tankTexture.getHeight(),
				super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, tankTexture.getWidth(),
				tankTexture.getHeight(), false, false);
	}
}
