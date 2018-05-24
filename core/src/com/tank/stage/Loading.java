package com.tank.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tank.game.TankInfinity;

public class Loading extends Stage implements InputProcessor {
	protected TankInfinity game;
	private ProgressBar assetsBar;	
	private Skin skin;
	private Texture splash;
	
	public Loading(TankInfinity game) {
		
	}
	
	@Override
	public void act(float delta) {
		
	}

	private Table buildTable() {
		return null;
	}
}