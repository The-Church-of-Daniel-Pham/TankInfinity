package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tank.utils.Assets;

public class Cursor extends AbstractUI {
	protected Color color;

	protected Texture tex;

	public Cursor() {
		super(false, true, 0, 0);
		tex = Assets.manager.get(Assets.crosshairs);
		setOrigin(tex.getWidth() / 2, tex.getHeight() / 2);
	}
	
	public Cursor(Texture t, float x, float y) {
		super(false, true, x, y);
		tex = t;
	}

	public void act(float delta) {
		
	}
	public void draw(Batch batch, float a) {
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), tex.getWidth(), tex.getHeight(), 1, 1,
				super.getRotation(), 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}
	public void setColor(Color col) {
		
	}
	public Color getColor() {
		return color;
	}
}
