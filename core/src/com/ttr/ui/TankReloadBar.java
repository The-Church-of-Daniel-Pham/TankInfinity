package com.ttr.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ttr.actor.tank.Tank;
import com.ttr.utils.Constants;

public class TankReloadBar {
	private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

	public static final float WIDTH = Constants.WINDOW_WIDTH / 5f;
	public static final float HEIGHT = Constants.WINDOW_HEIGHT / 20f;

	public static void render() {
		float completion = Tank.reloadTime / (1 / Tank.RATE_OF_FIRE); // reload time out of max reload time (inverse of
																		// rate of fire)
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(Gdx.graphics.getWidth() - WIDTH, 0, WIDTH * completion, HEIGHT);
		shapeRenderer.end();
	}

	public static void dispose() {
		shapeRenderer.dispose();
	}
}