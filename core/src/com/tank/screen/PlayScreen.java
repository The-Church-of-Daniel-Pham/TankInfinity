package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.stage.Countdown;
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
	public Countdown countdown;
	protected boolean paused;
	protected boolean pausedState;
	protected boolean pauseHeld;
	protected boolean gameOver;
	protected boolean counting;
	protected int levelNum;
	protected float timePlayed;

	public PlayScreen(TankInfinity game) {
		this.game = game;
		//level = new Level(this.game, Constants.LEVEL1_WIDTH, Constants.LEVEL1_HEIGHT);
		levelNum = 1;
		level = new Level(this.game, levelNum);
		levelhud = new LevelHUD(this.game, this);
		pauseMenu = new PauseMenu(this.game);
		gameOverMenu = new GameOverMenu(this.game);
		countdown = new Countdown(this.game);
		gameOver = false;
		timePlayed = 0f;
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public Level getLevel() {
		return level;
	}
	
	public int getLevelNum() {
		return levelNum;
	}

	@Override
	public void show() {
		game.addInput(levelhud);
		if (paused) {
			game.addInput(pauseMenu);
		}
		if (gameOver) {
			game.addInput(gameOverMenu);
		}
	}

	@Override
	public void hide() {
		game.removeInput(levelhud);
		game.removeInput(pauseMenu);
		game.removeInput(gameOverMenu);
		//paused = true; // when leaving this screen, pause automatically for return
	}

	@Override
	public void resize(int width, int height) {
		level.getViewport().update(width, height, true);
		levelhud.getViewport().update(width, height, true);
		pauseMenu.getViewport().update(width, height, true);
		gameOverMenu.getViewport().update(width, height, true);
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)); // adds
																											// anti-aliasing

		// if more than one type of viewports are used, each's apply() must be called
		// before drawing
		
		// pause by key input, only from active players
		boolean pausePressed = false;
		if (!gameOver) {
			for (Player p : game.players) {
				if (p.isEnabled()) {
					if (p.controls.pausePressed()) {
						pausePressed = true;
					}
				}
			}
		}
		if (pausePressed && !pauseHeld) {
			if (!paused) {
				pause();
			}
			else{
				resume();
			}
		}
		pauseHeld = pausePressed;

		if (!gameOver) {
			gameOver = isGameOver(); //check if game is over
			if (gameOver) { //only runs once, when gameOver becomes true
				game.removeInput(levelhud);
				game.addInput(gameOverMenu);
			}
		}

		// Update the stage even if game is over
		if (!paused && !counting) {
			level.act(delta);
		}
		level.getViewport().apply();
		level.draw();

		// update the hud
		if (!paused && !gameOver && !counting) {
			levelhud.act(delta);
			levelhud.getViewport().apply();
			levelhud.draw();
		}

		// update the pause menu
		if (paused && !counting) {
			pauseMenu.act(delta);
			pauseMenu.getViewport().apply();
			pauseMenu.draw();
		}
		
		//update the gameOver menu
		if (gameOver && !counting) {
			gameOverMenu.act(delta);
			gameOverMenu.getViewport().apply();
			gameOverMenu.draw();
		}
		
		if (!paused && !gameOver && !counting) {
			if (isReadyForNextLevel()) {
				setupNextLevel();
				game.setScreen(game.screens.get("Upgrades Menu"));
				countdown.setTime(3.0f);
				counting = true;
			}
		}
		
		if (counting) {
			countdown.act(delta);
			countdown.getViewport().apply();
			countdown.draw();
		}
		
		if (countdown.isFinished()) {
			counting = false;
		}
		
		if (!paused && !gameOver && !counting) {
			timePlayed += delta;
		}
	}

	public boolean isGameOver() {  	//game is over if all player tanks are destroyed
    	for(Player p: game.players) {
    		if(p.tank != null && !p.tank.isDestroyed()) return false;
    	}
    	return true;
    }
	
	public boolean isReadyForNextLevel() {
		boolean atleastOne = false;
		for(Player p: game.players) {
			if (p.isEnabled() && p.tank != null && !p.tank.isDestroyed()) {
				atleastOne = true;
				if (!p.tank.isReadyForNextLevel()) return false;
			}
    	}
    	return atleastOne && true;
	}
	
	public void setupNextLevel() {
		levelNum++;
		level.dispose();
		level = new Level(this.game, levelNum);
		for (Player p : game.players) {
			if (p.isEnabled() && !p.tank.isDestroyed()) {
				p.cursor.setupCursor(level);
			}
		}
	}

	@Override
	public void dispose() {
		level.dispose();
		levelhud.dispose();
		pauseMenu.dispose();
		gameOverMenu.dispose();
	}

	@Override
	public void pause() {
		if (!paused) {
			paused = true;
			game.removeInput(levelhud);
			game.addInput(pauseMenu);
			game.addInput(gameOverMenu);
		}
		else {
			pausedState = true;
		}
	}

	@Override
	public void resume() {
		if (pausedState) {
			pausedState = false;
			return;
		}
		else if (paused) {
			paused = false;
			game.addInput(levelhud);
			game.removeInput(pauseMenu);
			game.removeInput(gameOverMenu);
		}
	}
	
	public float getTimePlayed() {
		return timePlayed;
	}
}