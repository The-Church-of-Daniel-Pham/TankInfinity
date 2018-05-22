package com.ttr.screen;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Loading screen for the game. Begins to load Assets, and once the splash screen is loaded, draws the splash with batch and the progress bar with shapeRenderer.
 * After all of the assets are loaded, it goes up to the Game and switches to GameScreen. Windows can be resized by its own viewport.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ttr.TankTankRevolution;
import com.ttr.stage.Loading;
import com.ttr.utils.Assets;

public class LoadingScreen implements Screen {
	public Loading loading;
	protected TankTankRevolution game;

	public LoadingScreen(TankTankRevolution game) {
		this.game = game;
		// starts loading everything, but not waiting to continue
		Assets.loadAll();
		// wait until loading stage's assets are loaded
		Assets.manager.finishLoadingAsset(Assets.splash.fileName);
		Assets.manager.finishLoadingAsset(Assets.skin.fileName);
		loading = new Loading(this.game);
	}

	@Override
	public void render(float delta) {
		exitButton();

		// Clear the screen
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Update the stage
    	loading.act(delta);
    	loading.draw();
    	
		if (Assets.manager.update()) {
			// create rest of screens
			game.screens.put("Main Menu", new MainMenuScreen(game));
			game.screens.put("Settings Menu", new SettingsMenuScreen(game));
			game.screens.put("Play", new PlayScreen(game));
			game.setScreen(game.screens.get("Main Menu"));
		}
	}

	@Override
	public void resize(int width, int height) {
		loading.getViewport().update(width, height, true);
	}

	public void exitButton() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		loading.dispose();
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

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}