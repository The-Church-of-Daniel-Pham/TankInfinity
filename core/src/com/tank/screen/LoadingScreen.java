package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.Loading;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class LoadingScreen implements Screen {
	public Loading loading;
	protected TankInfinity game;

	public LoadingScreen(TankInfinity game) {
		this.game = game;
		// starts loading everything, but not waiting to continue
		Assets.loadAll();
		// wait until loading stage's assets are loaded
		Assets.manager.finishLoadingAsset(Assets.backdrop.fileName);
		Assets.manager.finishLoadingAsset(Assets.loading_tank.fileName);
		Assets.manager.finishLoadingAsset(Assets.skin.fileName);
		loading = new Loading(this.game);
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Update the stage
    	loading.act(delta);
    	loading.draw();
    	
		if (Assets.manager.update()) {
			// create rest of screens
			game.screens.put("Main Menu", new MainMenuScreen(game));
			game.screens.put("Customization Menu", new CustomizationMenuScreen(game));
			game.screens.put("Settings Menu", new SettingsMenuScreen(game));
			game.screens.put("Upgrades Menu", new UpgradeMenuScreen(game));
			game.setScreen(game.screens.get("Main Menu"));
		}
	}

	@Override
	public void resize(int width, int height) {
		loading.getViewport().update(width, height, true);
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