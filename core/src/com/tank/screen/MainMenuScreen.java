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
 * The Main Menu Screen class is used to invoke the logic
 * and draw the textures of the main menu, which contains
 * the buttons needed to explore all the portions of the
 * game.
 */
package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.MainMenu;
import com.tank.utils.Constants;

public class MainMenuScreen implements Screen {
	public MainMenu mainMenu;
	protected TankInfinity game;
	
	public MainMenuScreen (TankInfinity game) {
		this.game = game;
		mainMenu = new MainMenu(this.game);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void show() {
		game.addInput(mainMenu);
	}
	
	@Override
	public void hide() {
		game.removeInput(mainMenu);
	}

	@Override
	public void resize (int width, int height) {
		mainMenu.getViewport().update(width, height, true);
	}
	
	@Override
	public void render (float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
    	mainMenu.act(delta);
    	mainMenu.draw();
	}

	public void dispose() {
		mainMenu.dispose();
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