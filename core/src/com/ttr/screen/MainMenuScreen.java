package com.ttr.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ttr.stage.MainMenu;

public class MainMenuScreen implements Screen {
	public MainMenu mainMenu;
	private Game game;
	
	public MainMenuScreen (Game game) {
		this.game = game;
		mainMenu = new MainMenu(this.game);
		Gdx.input.setInputProcessor(mainMenu);
	}

	public void resize (int width, int height) {
		mainMenu.getViewport().update(width, height, true);
	}
	
	@Override
	public void render (float delta) {
		exitButton();

		//Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
    	mainMenu.act(delta);
    	mainMenu.draw();
	}

	public void dispose() {
		mainMenu.dispose();
	}
	
	public void exitButton() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
