package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Cursor extends AbstractUI {
	protected Color color;

	protected Texture tex;

	public Cursor(Texture t, Color col, float x, float y) {
		super(false, true, x, y);
		tex = t;
		color = col;
	}

	public void act(float delta) {
		
	}
	public void draw(Batch batch, float a) {
		
	}
	public void setColor(Color col) {
		
	}
	public Color getColor() {
		return color;
	}
}
