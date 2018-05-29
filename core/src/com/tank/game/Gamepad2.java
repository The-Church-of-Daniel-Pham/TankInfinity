package com.tank.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gamepad2 extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite sprite;
	Controller controller;
	BitmapFont font;
	boolean hasControllers = true;
	String message = "Please install a controller";

	@Override
	public void create() {
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture("map/grass0.png"));
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);

		font = new BitmapFont();
		font.setColor(Color.WHITE);

		if (Controllers.getControllers().size == 0) {
			hasControllers = false;
		} else
			controller = Controllers.getControllers().first();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// Update movement based on movement of left stick
		// Give a "deadzone" of 0.2 - -0.2, meaning the first 20% in either direction
		// will be ignored.
		// This keeps controls from being too twitchy
		// Move by up to 10 pixels per frame if full left or right.
		// Once again I flipped the sign on the Y axis because I prefer inverted Y axis
		// controls.
		if (controller.getAxis(XBox360Pad.AXIS_LEFT_X) > 0.2f || controller.getAxis(XBox360Pad.AXIS_LEFT_X) < -0.2f)
			sprite.translateX(controller.getAxis(XBox360Pad.AXIS_LEFT_X) / 10f);
		if (controller.getAxis(XBox360Pad.AXIS_LEFT_Y) > 0.2f || controller.getAxis(XBox360Pad.AXIS_LEFT_Y) < -0.2f)
			sprite.translateY(controller.getAxis(XBox360Pad.AXIS_LEFT_Y) / -10f);

		// Poll if user hits start button, if they do, reset position of sprite
		if (controller.getButton(XBox360Pad.BUTTON_START))
			sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(),
				sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());

		batch.end();
	}
}
