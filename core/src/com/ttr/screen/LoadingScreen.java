package com.ttr.screen;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Loading screen for the game. Begins to load Assets, and once the splash screen is loaded, draws the splash with batch and the progress bar with shapeRenderer.
 * After all of the assets are loaded, it goes up to the Game and switches to GameScreen. Windows can be resized by its own viewport.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ttr.level.Level;
import com.ttr.ui.AssetLoadingBar;
import com.ttr.utils.Assets;
import com.ttr.utils.Constants;

public class LoadingScreen implements Screen {
	private final SpriteBatch batch = new SpriteBatch();
	private final FitViewport viewport = new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	private Sprite splash;

	@Override
	public void show() {
		// starts loading as soon as game switches to this screen
		Assets.loadAll();
		// wait until menu texture is loaded
		Assets.manager.finishLoadingAsset(Assets.splash.fileName);
		splash = new Sprite(Assets.manager.get(Assets.splash));
	}

	@Override
	public void render(float delta) {
		exitButton();
		batch.begin();
		splash.draw(batch);
		batch.end();

		AssetLoadingBar.render();

		if (Assets.manager.update()) {
			// Comment this out if you just want to see the progress bar. As this can be
			// quite quick on desktop.
			((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(new Level(40, 40)));
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	public void exitButton() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}