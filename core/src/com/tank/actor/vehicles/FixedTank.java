package com.tank.actor.vehicles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class FixedTank extends AbstractVehicle {
	protected Texture tankTexture;

	public FixedTank(float x, float y, String color) {
		super(x, y);
		super.custom.addCustom("tank", color);
		tankTexture = super.custom.getTexture("tank");
		super.setOrigin(tankTexture.getWidth() / 2, tankTexture.getHeight() / 2);
	}

	public void draw(Batch batch, float a) {
		batch.draw(tankTexture, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), tankTexture.getWidth(), tankTexture.getHeight(),
				super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, tankTexture.getWidth(),
				tankTexture.getHeight(), false, false);
	}
}
