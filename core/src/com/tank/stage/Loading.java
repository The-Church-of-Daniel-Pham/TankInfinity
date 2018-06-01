package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.ui.Background;
import com.tank.utils.Assets;

public class Loading extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	private Background tankLoadingBackground;
	private float distance;
	private float percent;
	
	protected Skin skin = Assets.manager.get(Assets.skin);
	protected Texture backdrop = Assets.manager.get(Assets.backdrop);
	protected Texture loading_tank = Assets.manager.get(Assets.loading_tank);
	
	public Loading(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		Background backdropBackground = new Background(backdrop);
		backdropBackground.fillScale();
		tankLoadingBackground = new Background(loading_tank);
		tankLoadingBackground.setPosition(-loading_tank.getWidth(), 300);
		percent = 0;
		distance = Gdx.graphics.getWidth() + loading_tank.getWidth();
		buildTable();
		super.addActor(backdropBackground);
		super.addActor(tankLoadingBackground);
		super.addActor(uiTable);
	}
	
	@Override
	public void act(float delta) {
		percent = Interpolation.linear.apply(percent, Assets.manager.getProgress(), 0.05f);
		tankLoadingBackground.setX(percent * distance);
	}

	private void buildTable() {
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false);
		uiTable.bottom().padBottom(100).right().padRight(50);

		// Add widgets to the table here.
		Label tipLabel = new Label("Git gud", skin);
		uiTable.add(tipLabel).width(500).height(100).right();
	}
}