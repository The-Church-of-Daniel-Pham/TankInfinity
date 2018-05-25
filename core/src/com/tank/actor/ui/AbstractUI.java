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

	/**
	 * The act method is shared by all Actors. It tells what the actor is going to do.
	 * 
	 * @param delta		Time since last called.
	 */
	public void act(float delta) {
		
	}
	/**
	 * The draw method is shared by all Actors. This is called to draw the actor onto the stage.
	 * 
	 * @param batch		The object used to draw the textures and objects onto the screen
	 * @param a			The "transparency" of the object
	 */
	public void draw(Batch batch, float a) {
		
	}
}
