package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.utils.Constants;

public class Background extends Actor {
	protected Texture texture;
	protected boolean fill;
	
	public Background(Texture tex) {
		texture = tex;
		setX(Constants.DEFAULT_WIDTH / 2);
		setY(Constants.DEFAULT_HEIGHT / 2);
		setOrigin(texture.getWidth() / 2, texture.getHeight() / 2);
	}
	
	public void setFill(boolean f) {
		fill = f;
	}
	
	private void fillScale() {
		// between ratios of screen size to image dimensions, picks the largest such
		// that the image is scaled up to fill the screen
		setScale(Math.max(((float) getStage().getWidth()) / texture.getWidth(), ((float) getStage().getHeight()) / texture.getHeight()));
		if (texture.getWidth() > 1) {
			setX(getStage().getWidth() / 2);
		}
		else {
			setX(0);
		}
		if (texture.getHeight() > 1) {
			setY(getStage().getHeight() / 2);
		}
		else {
			setY(0);
		}
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
