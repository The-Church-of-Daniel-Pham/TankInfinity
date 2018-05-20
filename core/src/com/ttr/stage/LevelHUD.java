package com.ttr.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ttr.TankTankRevolution;
import com.ttr.utils.Assets;

public class LevelHUD extends Stage implements InputProcessor {
	protected Game game;
	private Skin skin = Assets.manager.get(Assets.skin);

	public LevelHUD(Game game) {
		super(new ScreenViewport());
		this.game = game;
		super.addActor(buildTable());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		TextButton returnButton = new TextButton("Return to Main Menu", skin);

		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(TankTankRevolution.screens.get("Main Menu"));
				event.stop();
			}
		});
		
		uiTable.defaults().width(200).height(75).space(25).center();
		uiTable.add(returnButton).width(300).expand().top().right();

		return uiTable;
	}
}
