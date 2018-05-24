package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;

public class LevelHUD extends Stage implements InputProcessor {
	protected TankInfinity game;
	private Label fpsLabel;
	private static float sinceChange;
	private ProgressBar reloadBar;
	private Skin skin;

	public LevelHUD(TankInfinity game) {
		
	}

	@Override
	public void act(float delta) {
		
	}

	private Table buildTable() {
		return null;
	}
}