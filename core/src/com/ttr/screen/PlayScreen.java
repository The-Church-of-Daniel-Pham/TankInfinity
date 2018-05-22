package com.ttr.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ttr.TankTankRevolution;
import com.ttr.stage.Level;
import com.ttr.stage.LevelHUD;
import com.ttr.stage.PauseMenu;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Play screen for the game. Given a Level, it continually refreshes the screen and calls the level's act and draw methods.
 * Resizing is done through the level's viewport. Hitting escape stops the game.
 */

public class PlayScreen implements Screen {
	protected TankTankRevolution game;
	public Level level;
	public LevelHUD levelhud;
	public PauseMenu pauseMenu;
	protected boolean paused;

	public PlayScreen(TankTankRevolution game) {
		this.game = game;
		level = new Level(40, 40);
		levelhud = new LevelHUD(this.game);
		pauseMenu = new PauseMenu(this.game);
	}
	
	@Override
	public void show() {
		game.addInput(levelhud);
		game.addInput(level.playerTank);
		game.addInput(level.camera);
		if (paused) {
			game.addInput(pauseMenu);
		}
	}

	@Override
	public void hide() {
		game.removeInput(levelhud);
		game.removeInput(level.playerTank);
		game.removeInput(level.camera);
		game.removeInput(pauseMenu);
		paused = true;	//when leaving this screen, pause automatically for return
	}
	
    @Override
	public void resize(int width, int height) {
		level.getViewport().update(width, height, true);
		levelhud.getViewport().update(width, height, true);
		pauseMenu.getViewport().update(width, height, true);
	}
	
    @Override
    public void render(float delta) {
    	exitButton();
    	
    	//Clear the screen
    	Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)); // adds anti-aliasing
		
		//if more than one type of viewports are used, each's apply() must be called before drawing
		
        //Update the stage
		if (!paused) {
			level.act(delta);
		}
		level.getViewport().apply();
		level.draw();
		
		//update the hud
		if (!paused) {
			levelhud.act(delta);
			levelhud.getViewport().apply();
			levelhud.draw();
		}
		
		//update the pause menu
		if (paused) {
			pauseMenu.act(delta);
			pauseMenu.getViewport().apply();
			pauseMenu.draw();
		}
    }
    
	public void exitButton() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		level.dispose();
		levelhud.dispose();
	}
	
	@Override
	public void pause() {
		paused = true;
		game.removeInput(levelhud);
		game.removeInput(level.playerTank);
		game.removeInput(level.camera);
		game.addInput(pauseMenu);
	}

	@Override
	public void resume() {
		paused = false;
		game.addInput(levelhud);
		game.addInput(level.playerTank);
		game.addInput(level.camera);
		game.removeInput(pauseMenu);
	}
}