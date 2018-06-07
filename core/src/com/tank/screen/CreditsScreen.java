package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.Credits;
import com.tank.utils.Constants;;

public class CreditsScreen implements Screen{
	public Credits credits;
	private TankInfinity game;
	
	public CreditsScreen (TankInfinity game) {
		this.game = game;
		credits = new Credits(this.game);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void show() {
		game.addInput(credits);
	}
	
	@Override
	public void hide() {
		game.removeInput(credits);
	}
	
	@Override
	public void resize (int width, int height) {
		credits.getViewport().update(width, height, true);
	}
	
	@Override
	public void render (float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update the stage
		credits.act(delta);
		credits.draw();
	}

	public void dispose() {
		credits.dispose();
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
