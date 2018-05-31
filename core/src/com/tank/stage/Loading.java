package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.ui.Background;
import com.tank.utils.Assets;

public class Loading extends Stage implements InputProcessor {
	protected TankInfinity game;
	private Background tankLoadingBackground;
	private float distance;
	private float percent;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture backdrop = Assets.manager.get(Assets.backdrop);
	private Texture loading_tank = Assets.manager.get(Assets.loading_tank);
	
	public Loading(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		Background backdropBackground = new Background(backdrop);
		backdropBackground.fillScale();
		tankLoadingBackground = new Background(loading_tank);
		tankLoadingBackground.setPosition(-loading_tank.getWidth(), 300);
		percent = 0;
		distance = Gdx.graphics.getWidth() + loading_tank.getWidth();
		super.addActor(backdropBackground);
		super.addActor(tankLoadingBackground);
		super.addActor(buildTable());
	}
	
	@Override
	public void act(float delta) {
		percent = Interpolation.linear.apply(percent, Assets.manager.getProgress(), 0.05f);
		tankLoadingBackground.setX(percent * distance);
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(false);
		uiTable.setDebug(true);
		uiTable.bottom().padBottom(200).right().padRight(100);
		uiTable.defaults().width(400).height(200).space(25).right();

		// Add widgets to the table here.
		Label tipLabel = new Label("Get gud", skin);
		uiTable.add(tipLabel);

		return uiTable;
	}
}