package com.ttr.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ttr.TankTankRevolution;

public class LevelHUD extends Stage implements InputProcessor {
	protected Game game;

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
		Skin skin = new Skin(Gdx.files.internal("menu/uiskin.json"));

		TextButton returnButton = new TextButton("Return to Main Menu", skin);

		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(((TankTankRevolution) game).mainMenuScreen);
				event.stop();
			}
		});

		uiTable.defaults().width(200).height(50).pad(25);
		uiTable.add(returnButton);

		return uiTable;
	}
}
