
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
 * The Upgrade Menu Screen class is used to invoke the logic
 * and draw the textures of the upgrade menu, notably the
 * controls that operate the menu, the navigation button, and
 * images/text that must be continuously updated as the menu
 * is navigated.
 */
package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.UpgradeMenu;
import com.tank.utils.Constants;

public class UpgradeMenuScreen implements Screen {
	public UpgradeMenu uMenu;
	private TankInfinity game;

	public UpgradeMenuScreen(TankInfinity game) {
		this.game = game;
		uMenu = new UpgradeMenu(this.game);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		game.addInput(uMenu);
		uMenu.resetTable();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		uMenu.act();
		uMenu.draw();
	}

	@Override
	public void resize(int width, int height) {
		uMenu.getViewport().update(width, height, true);
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
		game.removeInput(uMenu);
	}

	@Override
	public void dispose() {
		uMenu.dispose();
	}
}
