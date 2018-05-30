package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.SettingsMenu;

public class SettingsMenuScreen implements Screen {
	public SettingsMenu settingsMenu;
	private TankInfinity game;
	
	public SettingsMenuScreen (TankInfinity game) {
		this.game = game;
		settingsMenu = new SettingsMenu(this.game);
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
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
		settingsMenu.act(delta);
		settingsMenu.draw();
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