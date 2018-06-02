package com.tank.actor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
	protected Texture texture;
	protected boolean fill;
	
	public Background(Texture tex) {
		texture = tex;
	}
	
	public void setFill(boolean f) {
		fill = f;
	}
	
	private void fillScale() {
		// between ratios of screen size to image dimensions, picks the largest such
		// that the image is scaled up to fill the screen
		System.out.println(Gdx.graphics.getWidth() + " x " +  Gdx.graphics.getHeight());
		setScale(Math.max(((float) Gdx.graphics.getWidth()) / texture.getWidth(), ((float) Gdx.graphics.getHeight()) / texture.getHeight()));
		System.out.println(getScaleX() + ", " + getScaleY());
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		if (fill) {
			fillScale();
		}
		batch.draw(texture, super.getX()-super.getOriginX(), super.getY()-super.getOriginY(),super.getOriginX(),super.getOriginY(), texture.getWidth(),
				texture.getHeight(), super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, texture.getWidth(),
				texture.getHeight(), false, false);
	}
}
