package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.stage.GameOverMenu;
import com.tank.stage.Level;
import com.tank.stage.LevelHUD;
import com.tank.stage.PauseMenu;
import com.tank.utils.Constants;

public class PlayScreen implements Screen {
	protected TankInfinity game;
	public Level level;
	public LevelHUD levelhud;
	public PauseMenu pauseMenu;
	public GameOverMenu gameOverMenu;
	protected boolean paused;
	protected boolean gameOver;

	public PlayScreen(TankInfinity game) {
		this.game = game;
		//level = new Level(this.game, Constants.LEVEL1_WIDTH, Constants.LEVEL1_HEIGHT);
		level = new Level(this.game, 1);
		levelhud = new LevelHUD(this.game);
		pauseMenu = new PauseMenu(this.game);
		gameOverMenu = new GameOverMenu(this.game);
		gameOver = false;
	}

	@Override
	public void show() {
		game.addInput(levelhud);
		if (paused) {
			game.addInput(pauseMenu);
		}
	}

	@Override
	public void hide() {
		game.removeInput(levelhud);
		game.removeInput(pauseMenu);
		paused = true; // when leaving this screen, pause automatically for return
	}

	@Override
	public void resize(int width, int height) {
		level.getViewport().update(width, height, true);
		levelhud.getViewport().update(width, height, true);
		pauseMenu.getViewport().update(width, height, true);
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)); // adds
																											// anti-aliasing

		// if more than one type of viewports are used, each's apply() must be called
		// before drawing

		if (!gameOver) {
			gameOver = isGameOver(); //check if game is over
			if (gameOver) { //only runs once, when gameOver becomes true
				game.removeInput(levelhud);
				game.addInput(gameOverMenu);
			}
		}

		// Update the stage even if game is over
		if (!paused) {
			level.act(delta);
		}
		level.getViewport().apply();
		level.draw();

		// update the hud
		if (!paused && !gameOver) {
			levelhud.act(delta);
			levelhud.getViewport().apply();
			levelhud.draw();
		}

		// update the pause menu
		if (paused) {
			pauseMenu.act(delta);
			pauseMenu.getViewport().apply();
			pauseMenu.draw();
		}
		
		//update the gameOver menu
		if (gameOver) {
			gameOverMenu.act(delta);
			gameOverMenu.getViewport().apply();
			gameOverMenu.draw();
		}

	}

	public boolean isGameOver() {  	//game is over if all player tanks are destroyed
    		for(Player p: game.players) {
    		if(p.tank != null && !p.tank.isDestroyed()) return false;
    	}
    	return true;
    }

	@Override
	public void dispose() {
		level.dispose();
		levelhud.dispose();
		pauseMenu.dispose();
	}

	@Override
	public void pause() {
		paused = true;
		game.removeInput(levelhud);
		game.addInput(pauseMenu);
	}

	@Override
	public void resume() {
		paused = false;
		game.addInput(levelhud);
		game.removeInput(pauseMenu);
	}
}