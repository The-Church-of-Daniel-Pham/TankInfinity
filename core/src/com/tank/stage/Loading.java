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
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture splash = Assets.manager.get(Assets.splash);
	
	public Loading(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		Background backdrop = new Background(splash);
		// between ratios of screen size to image dimensions, picks the largest such
		// that the image is scaled up to fill the screen
		backdrop.setScale(
				Math.max(((float) Gdx.graphics.getWidth()) / splash.getWidth(), (float) Gdx.graphics.getHeight())
						/ splash.getHeight());
		super.addActor(backdrop);
		super.addActor(buildTable());
	}
	
	@Override
	public void act(float delta) {
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