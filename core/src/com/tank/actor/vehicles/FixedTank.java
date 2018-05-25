package com.tank.actor.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class FixedTank extends AbstractVehicle {
	
	protected Texture tankTexture;
	protected Color color;

	public FixedTank(float x, float y) {
		super(x, y);
		color = null;
	}
	public FixedTank(Texture tex, Color color, float x, float y) {
		super(x, y);
		tankTexture = tex;
		this.color = color;
	}
	
	public void setOrigin(float x, float y) {
		super.setOrigin(x, y);
	}
	public void draw(Batch batch, float a) {
		batch.draw(tankTexture, super.getX()-super.getOriginX(), super.getY()-super.getOriginY(),super.getOriginX(),super.getOriginY(), tankTexture.getWidth(),
				tankTexture.getHeight(), super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, tankTexture.getWidth(),
				tankTexture.getHeight(), false, false);
	}
}
