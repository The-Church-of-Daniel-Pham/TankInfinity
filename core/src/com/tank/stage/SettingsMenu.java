package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.table.AudioSettings;
import com.tank.table.ControlsSettings;
import com.tank.table.VideoSettings;
import com.tank.utils.Assets;

public class SettingsMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable; // master table containing titles, settings, and back button
	protected Table titleTable; // tab headers
	protected Table settingsTable; // placeholder to be swapped out
	protected Table videoTable;
	protected Table audioTable;
	protected Table controlsTable;
	private Skin skin = Assets.manager.get(Assets.skin);;

	public SettingsMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;

		titleTable = new Table();
		buildTitleTable();

		settingsTable = new Table();
		videoTable = new VideoSettings();
		audioTable = new AudioSettings();
		controlsTable = new ControlsSettings();

		uiTable = new Table();
		buildTable();

		super.addActor(uiTable);
	}

	public void buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.
		uiTable.top();

		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.previousScreen);
				event.stop();
			}
		});

		uiTable.add(titleTable);
		uiTable.row();
		uiTable.add(settingsTable);
		uiTable.row();
		uiTable.add(backButton).width(150).height(100).space(25).center();
	}

	public void buildTitleTable() {
		TextButton videoButton = new TextButton("Video", skin);
		videoButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeSettingsTo(videoTable);
			}
		});

		TextButton audioButton = new TextButton("Audio", skin);
		audioButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeSettingsTo(audioTable);
			}
		});

		TextButton controlsButton = new TextButton("Controls", skin);
		controlsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeSettingsTo(controlsTable);
			}
		});

		titleTable.setDebug(true);
		titleTable.setFillParent(false);
		titleTable.defaults().width(300).height(100).space(25);
		titleTable.center();
		titleTable.top();
		titleTable.padTop(100f);
		titleTable.add(videoButton);
		titleTable.add(audioButton);
		titleTable.add(controlsButton);
	}

	public void changeSettingsTo(Table t) {
		settingsTable.clearChildren();
		settingsTable.setFillParent(false);
		settingsTable.add(t);
	}
}