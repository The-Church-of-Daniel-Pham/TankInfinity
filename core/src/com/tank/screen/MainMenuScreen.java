package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.stage.MainMenu;

public class MainMenuScreen implements Screen {
	public MainMenu mainMenu;
	protected TankInfinity game;
	
	public MainMenuScreen (TankInfinity game) {
		this.game = game;
		mainMenu = new MainMenu(this.game);
		// create first player
		this.game.players.add(new Player("Player 1"));
		this.game.players.get(0).tank = new PlayerTank(128, 128, "green", "blue");
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
		exitButton();

		//Clear the screen
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
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
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}