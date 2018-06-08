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
 * The Settings Menu Screen class is used to invoke the logic
 * and draw the textures of the settings menu, notably the
 * buttons and the underlying changes to controls, graphics,
 * and audio.
 */
package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.KeyBindsMenu;
import com.tank.stage.SettingsMenu;
import com.tank.utils.Constants;

public class SettingsMenuScreen implements Screen {
	private SettingsMenu settingsMenu;
	private KeyBindsMenu keyBindsMenu;
	private TankInfinity game;
	private boolean lastWaitingForInput;
	
	public SettingsMenuScreen (TankInfinity game) {
		this.game = game;
		settingsMenu = new SettingsMenu(this.game);
		keyBindsMenu = new KeyBindsMenu(this.game);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public SettingsMenu getSettingsMenu()
	{
		return settingsMenu;
	}
	
	@Override
	public void show() {
		game.addInput(settingsMenu);
	}
	
	@Override
	public void hide() {
		game.removeInput(settingsMenu);
	}
	
	@Override
	public void resize (int width, int height) {
		settingsMenu.getViewport().update(width, height, true);
	}
	
	@Override
	public void render (float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
		settingsMenu.act(delta);
		settingsMenu.draw();
		if (settingsMenu.getControlsSettings().isWaitingForInput()) {
			keyBindsMenu.act(delta);
			keyBindsMenu.draw();
		}
		if (settingsMenu.getControlsSettings().isWaitingForInput() && !lastWaitingForInput) {
			game.removeInput(settingsMenu);
			lastWaitingForInput = true;
		}
		if (!settingsMenu.getControlsSettings().isWaitingForInput() && lastWaitingForInput) {
			game.addInput(settingsMenu);
			lastWaitingForInput = false;
		}
	}

	public void dispose() {
		settingsMenu.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}