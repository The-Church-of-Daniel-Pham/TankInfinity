/**
 * @author The Church of Daniel Pham
 * Description:
 * Screens are one of the highest levels of code we
 * implement using Libgdx, the highest being Game/DesktopLauncher.
 * The Libgdx framework calls the render(...) method,
 * and we write the Stages that get called in this method.
 * Screens essentially implement their non-screen,
 * Stage counterparts, calling their act(...) and draw()
 * methods.
 * 
 * The Loading Screen class is used to invoke the logic
 * and draw the textures of the loading portion of the game
 * that is the first thing a user sees. As loading takes some
 * time, it provides a visual for the user to look at. In
 * this class every other necessary screen is also loaded.
 */
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
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
			Constants.createResolutionsCycleList();
			game.screens.put("Main Menu", new MainMenuScreen(game));
			game.screens.put("Customization Menu", new CustomizationMenuScreen(game));
			game.screens.put("Tutorial", new TutorialScreen(game));
			game.screens.put("Credits", new CreditsScreen(game));
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