package com.tank.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tank.game.TankInfinity;
import com.tank.stage.UpgradeMenu;
import com.tank.utils.Constants;

public class UpgradeMenuScreen implements Screen {
	public UpgradeMenu uMenu;
	private TankInfinity game;

	public UpgradeMenuScreen(TankInfinity game) {
		this.game = game;
		uMenu = new UpgradeMenu(this.game);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		game.addInput(uMenu);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, Constants.CLEAR_COLOR, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		uMenu.act();
		uMenu.draw();
	}

	@Override
	public void resize(int width, int height) {
		uMenu.getViewport().update(width, height, true);
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
		game.removeInput(uMenu);
	}

	@Override
	public void dispose() {
		uMenu.dispose();
	}
}
