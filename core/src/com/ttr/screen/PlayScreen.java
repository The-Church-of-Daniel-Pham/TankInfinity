package com.ttr.screen;



import com.badlogic.gdx.Game;
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
	protected Game game;
	public Level level;
	public LevelHUD levelhud;
	public PauseMenu pauseMenu;
	public boolean paused;

	public PlayScreen(Game game) {
		this.game = game;
		level = new Level(40, 40);
		levelhud = new LevelHUD(this.game);
		pauseMenu = new PauseMenu(this.game);
	}
	
	@Override
	public void show() {
		paused = false;
		TankTankRevolution.addInput(levelhud);
		TankTankRevolution.addInput(level.playerTank);
		TankTankRevolution.addInput(level.camera);
	}

	@Override
	public void hide() {
		TankTankRevolution.removeInput(levelhud);
		TankTankRevolution.removeInput(level.playerTank);
		TankTankRevolution.removeInput(level.camera);
		TankTankRevolution.removeInput(pauseMenu);
	}
	
    @Override
	public void resize(int width, int height) {
		level.getViewport().update(width, height, true);
		levelhud.getViewport().update(width, height, true);
	}
	
    @Override
    public void render(float delta) {
    	exitButton();
    	
    	//Clear the screen
    	Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)); // adds anti-aliasing

        //Update the stage
		if (!paused) {
			level.act(delta);
		}
		level.draw();
		
		//update the hud
		if (!paused) {
			levelhud.act(delta);
			levelhud.draw();
		}
		
		//update the pause menu
		if (paused) {
			pauseMenu.act(delta);
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
		TankTankRevolution.removeInput(levelhud);
		TankTankRevolution.removeInput(level.playerTank);
		TankTankRevolution.removeInput(level.camera);
		TankTankRevolution.addInput(pauseMenu);
	}

	@Override
	public void resume() {
		paused = false;
		TankTankRevolution.addInput(levelhud);
		TankTankRevolution.addInput(level.playerTank);
		TankTankRevolution.addInput(level.camera);
		TankTankRevolution.removeInput(pauseMenu);
	}
}