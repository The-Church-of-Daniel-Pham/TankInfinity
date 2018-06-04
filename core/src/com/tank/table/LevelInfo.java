package com.tank.table;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tank.screen.PlayScreen;
import com.tank.stage.Level;
import com.tank.utils.Assets;

public class LevelInfo extends Table {
	private Skin skin = Assets.manager.get(Assets.skin);
	protected Game game;
	protected Level level;
	
	protected Label levelNumLabel;
	
	public LevelInfo(Game game) {
		this.game = game;
		updateLevel();
		
		super.setFillParent(false);
		super.setDebug(false); // This is optional, but enables debug lines for tables.
		super.defaults().width(400).height(100).space(25).center();
		
		levelNumLabel = new Label("Level 0", skin);
		super.add(levelNumLabel);
	}
	
	public void updateLevel() {
		if (game.getScreen() instanceof PlayScreen) {
			this.level = ((PlayScreen) game.getScreen()).getLevel();
		}
	}
	
	@Override
	public void act(float delta) {
		updateLevel();
		levelNumLabel.setText("Level " + "0");
	}
}
