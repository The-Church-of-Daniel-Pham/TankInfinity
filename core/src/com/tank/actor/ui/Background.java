package com.tank.actor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
	protected Texture texture;
	
	public Background(Texture tex) {
		texture = tex;
	}
	
	public void fillScale() {
		// between ratios of screen size to image dimensions, picks the largest such
		// that the image is scaled up to fill the screen
		setScale(Math.max(((float) Gdx.graphics.getWidth()) / texture.getWidth(), (float) Gdx.graphics.getHeight()) / texture.getHeight());
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(texture, super.getX()-super.getOriginX(), super.getY()-super.getOriginY(),super.getOriginX(),super.getOriginY(), texture.getWidth(),
				texture.getHeight(), super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, texture.getWidth(),
				texture.getHeight(), false, false);
	}
}
