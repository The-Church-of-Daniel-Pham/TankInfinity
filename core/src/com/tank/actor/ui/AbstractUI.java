package com.tank.actor.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AbstractUI extends Actor {
	protected boolean absolute;
	protected boolean visible;
	protected float absoluteX;
	protected float absoluteY;

	public AbstractUI(boolean abs, boolean vis, float x, float y) {
		absolute = abs;
		visible = vis;
		setX(x);
		setY(y);
	}

	public void act(float delta) {
		
	}
	public void draw(Batch batch, float a) {
		
	}
}
