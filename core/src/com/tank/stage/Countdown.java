package com.tank.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.ui.Background;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class Countdown extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Label timeLabel;
	protected float remainingTime;
	protected Table uiTable;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture black = Assets.manager.get(Assets.black);
	
	public Countdown(TankInfinity game) {
		super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
		this.game = game;
		Background darken = new Background(black);
		
		darken.setFill(true);
		super.addActor(darken);
		
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}
	
	private void buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(100).height(50).space(25).center();
		
		// Add widgets to the table here.
		timeLabel = new Label("", skin, "title");
		timeLabel.setAlignment(Align.center);
		uiTable.add(timeLabel);
	}
	
	public void setTime(float t) {
		remainingTime = t;
	}
	
	public boolean isFinished() {
		return (remainingTime <= 0);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		remainingTime -= delta;
		if (remainingTime < 0) {
			remainingTime = 0;
			timeLabel.setText("0");
		}
		else{
			timeLabel.setText("" + ((int) remainingTime + 1));
		}
	}
}