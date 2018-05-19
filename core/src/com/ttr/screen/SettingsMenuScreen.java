package com.ttr.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ttr.TankTankRevolution;
import com.ttr.stage.SettingsMenu;

public class SettingsMenuScreen implements Screen {
	public SettingsMenu settingsMenu;
	private Game game;
	
	public SettingsMenuScreen (Game game) {
		this.game = game;
		settingsMenu = new SettingsMenu(this.game);
	}
	
	@Override
	public void show() {
		TankTankRevolution.addInput(settingsMenu);
	}
	
	@Override
	public void hide() {
		TankTankRevolution.removeInput(settingsMenu);
	}
	
	@Override
	public void resize (int width, int height) {
		settingsMenu.getViewport().update(width, height, true);
	}
	
	@Override
	public void render (float delta) {
		exitButton();

		//Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
		settingsMenu.act(delta);
		settingsMenu.draw();
	}

	public void dispose() {
		settingsMenu.dispose();
	}
	
	public void exitButton() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
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
