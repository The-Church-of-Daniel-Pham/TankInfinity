package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.ui.Background;
import com.tank.utils.Assets;

public class Loading extends Stage implements InputProcessor {
	protected TankInfinity game;
	private ProgressBar assetsBar;
	private Background tankLoadingBackground;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture backdrop = Assets.manager.get(Assets.backdrop);
	private Texture loading_tank = Assets.manager.get(Assets.loading_tank);
	
	public Loading(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		Background backdropBackground = new Background(backdrop);
		backdropBackground.fillScale();
		tankLoadingBackground = new Background(loading_tank);
		tankLoadingBackground.setPosition(300, 400);
		super.addActor(backdropBackground);
		super.addActor(tankLoadingBackground);
		super.addActor(buildTable());
	}
	
	@Override
	public void act(float delta) {
		tankLoadingBackground.moveBy(delta * Assets.manager.getProgress() * 1000, 0);
		assetsBar.setValue(Assets.manager.getProgress());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		assetsBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin);
		uiTable.add(assetsBar).width(500).height(150).expand();

		return uiTable;
	}
}