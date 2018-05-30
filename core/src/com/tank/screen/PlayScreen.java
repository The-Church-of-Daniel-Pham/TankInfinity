package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.stage.Level;
import com.tank.stage.LevelHUD;
import com.tank.stage.PauseMenu;
import com.tank.utils.Constants;

public class PlayScreen implements Screen {
	protected TankInfinity game;
	public Level level;
	public LevelHUD levelhud;
	public PauseMenu pauseMenu;
	protected boolean paused;

	public PlayScreen(TankInfinity game) {
		this.game = game;
		level = new Level(this.game, Constants.LEVEL1_WIDTH, Constants.LEVEL1_HEIGHT);
		levelhud = new LevelHUD(this.game, this.game.players);
		pauseMenu = new PauseMenu(this.game);
	}
	
	@Override
	public void show() {
		game.addInput(levelhud);
		for (Player p : this.game.players) {
			if (p.isEnabled()) {
				game.addInput(p.tank);
			}
		}
		if (paused) {
			game.addInput(pauseMenu);
		}
	}

	@Override
	public void hide() {
		game.removeInput(levelhud);
		for (Player p : this.game.players) {
			game.removeInput(p.tank);
		}
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
		pauseMenu.dispose();
	}
	
	@Override
	public void pause() {
		paused = true;
		game.removeInput(levelhud);
		for (Player p : game.players) {
			game.removeInput(p.tank);
		}
		game.addInput(pauseMenu);
	}

	@Override
	public void resume() {
		paused = false;
		game.addInput(levelhud);
		for (Player p : game.players) {
			game.addInput(p.tank);
		}
		game.removeInput(pauseMenu);
	}
}