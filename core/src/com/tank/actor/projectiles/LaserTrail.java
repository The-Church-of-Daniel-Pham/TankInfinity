package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.NumberUtils;
import com.tank.utils.Assets;

public class LaserTrail extends Actor{
	
	private static Texture laserTexture = Assets.manager.get(Assets.laser);
	private float lifeTime;
	private float maxLifeTime;
	
	public LaserTrail(float x, float y, float rotation) {
		setX(x);
		setY(y);
		setRotation(rotation);
		setOrigin(laserTexture.getWidth() / 2, laserTexture.getHeight() / 2);
		maxLifeTime = 1.5f;
	}
	
	@Override
	public void act(float delta) {
		lifeTime += delta;
		if (lifeTime >= maxLifeTime) {
			remove();
		}
	}
	
	@Override
	public void draw(Batch batch, float a) {
		batch.setColor(NumberUtils.intToFloatColor(Color.toIntBits(255, 255, 255, (int)(191 * (1.0 - (lifeTime / maxLifeTime))))));
		batch.draw(laserTexture, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), laserTexture.getWidth(), laserTexture.getHeight(), super.getScaleX(), super.getScaleY(),
				getRotation(), 0, 0, laserTexture.getWidth(), laserTexture.getHeight(), false, false);
		batch.setColor(Color.WHITE);
	}
}
