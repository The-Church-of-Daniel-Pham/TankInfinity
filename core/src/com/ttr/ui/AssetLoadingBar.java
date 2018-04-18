package com.ttr.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ttr.utils.Assets;
import com.ttr.utils.Constants;

public class AssetLoadingBar {
	private static final ShapeRenderer shapeRenderer = new ShapeRenderer();;

	public static final float WIDTH = Constants.WINDOW_WIDTH / 2.5f;
	public static final float HEIGHT = Constants.WINDOW_HEIGHT / 10f;

	public static void render() {
		float progress = Assets.manager.getProgress();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect((Constants.WINDOW_WIDTH - WIDTH) / 2f, (Constants.WINDOW_HEIGHT - HEIGHT) / 2f,
				WIDTH * progress, HEIGHT);
		shapeRenderer.end();
	}

	public static void dispose() {
		shapeRenderer.dispose();
	}
}