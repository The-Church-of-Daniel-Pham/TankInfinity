package com.ttr.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ttr.TankTankRevolution;
import com.ttr.actor.Background;
import com.ttr.utils.Assets;

public class Loading extends Stage implements InputProcessor {
	protected TankTankRevolution game;
	private ProgressBar assetsBar;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture splash = Assets.manager.get(Assets.splash);
	
	public Loading(TankTankRevolution game) {
		super(new ScreenViewport());
		this.game = game;
		super.addActor(new Background(splash));
		super.addActor(buildTable());
	}
	
	@Override
	public void act(float delta) {
		assetsBar.setValue(Assets.manager.getProgress());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		assetsBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin);
		uiTable.add(assetsBar).width(500).height(150).expand();

		return uiTable;
	}
}
