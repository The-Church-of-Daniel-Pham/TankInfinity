package com.ttr.screen;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Play screen for the game. Given a Level, it continually refreshes the screen and calls the level's act and draw methods.
 * Resizing is done through the level's viewport. Hitting escape stops the game.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ttr.stage.Level;
import com.ttr.ui.FrameRate;
import com.ttr.ui.TankReloadBar;

public class GameScreen implements Screen {
	private Level level;

	public GameScreen(Level level) {
		this.level = level;
	}

    @Override
    public void render(float delta) {
    	exitButton();
    	
    	//Clear the screen
    	Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT
				| (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0)); // adds anti-aliasing

        //Update the stage
        level.act(delta);
		level.draw();
		TankReloadBar.render();
		FrameRate.render();
    }
    @Override
	public void resize(int width, int height) {
		level.getViewport().update(width, height, true);
	}
    
	public void exitButton() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		level.dispose();
		TankReloadBar.dispose();
		FrameRate.dispose();
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}