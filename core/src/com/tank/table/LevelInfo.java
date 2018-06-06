package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.tank.screen.PlayScreen;
import com.tank.stage.Level;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class LevelInfo extends Table {
	private Skin skin = Assets.manager.get(Assets.skin);
	protected PlayScreen playscreen;
	protected Level level;
	protected Label levelNumLabel;
	protected Label enemyCountLabel;
	protected Label timePlayedLabel;
	
	public LevelInfo(PlayScreen playscreen) {
		this.playscreen = playscreen;
		updateLevel();
		
		super.setFillParent(false);
		super.setDebug(false); // This is optional, but enables debug lines for tables.
		super.defaults().width(150).height(50).space(20).center();
		
		levelNumLabel = new Label("Level ", skin, "medium");
		levelNumLabel.setAlignment(Align.left);
		enemyCountLabel = new Label(" Enemies", skin, "medium");
		enemyCountLabel.setAlignment(Align.right);
		timePlayedLabel = new Label("", skin, "medium");
		timePlayedLabel.setAlignment(Align.center);
		
		super.add(levelNumLabel);
		super.add(enemyCountLabel);
		super.row();
		super.add(timePlayedLabel).colspan(2).center();
		
		super.setBackground(skin.newDrawable("list", 1.0f, 1.0f, 1.0f, 0.75f));
	}
	
	protected void updateLevel() {
		this.level = playscreen.getLevel();
	}
	
	@Override
	public void act(float delta) {
		updateLevel();
		levelNumLabel.setText("Level " + playscreen.getLevelNum());
		enemyCountLabel.setText(level.getEnemyCount() + " Enemies");
		timePlayedLabel.setText(Constants.formatTime(playscreen.getTimePlayed()));
	}
}
