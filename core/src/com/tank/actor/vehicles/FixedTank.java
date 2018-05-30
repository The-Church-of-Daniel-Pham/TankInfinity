package com.tank.actor.vehicles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tank.utils.Assets;

public abstract class FixedTank extends AbstractVehicle {
	protected Texture tankTexture;

	public FixedTank(float x, float y) {
		super(x, y);
		tankTexture = Assets.manager.get(Assets.tread_default);
		initiliazeHitbox();
		super.setOrigin(tankTexture.getWidth() / 2, tankTexture.getHeight() / 2);
	}
	
	public FixedTank(float x, float y, Texture t) {
		super(x, y);
		tankTexture = t;
		initiliazeHitbox();
		super.setOrigin(tankTexture.getWidth() / 2, tankTexture.getHeight() / 2);
	}

	protected void initiliazeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	public void draw(Batch batch, float a) {
		// update textures from customization
		batch.draw(tankTexture, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), tankTexture.getWidth(), tankTexture.getHeight(),
				super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, tankTexture.getWidth(),
				tankTexture.getHeight(), false, false);
	}
}
