package com.tank.table;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;

public class ControlsSettings extends Table{
	protected TankInfinity game;
	protected Table titleTable; // tab headers
	protected Table settingsTable; // placeholder to be swapped out
	protected ScrollPane controlsScroll;
	private Skin skin = Assets.manager.get(Assets.skin);
	
	private ArrayList<ControlsSettingPlayer> playerControlSettings;
	private ArrayList<TextButton> playerButtons;
	
	public ControlsSettings(TankInfinity game){
		this.game = game;
		playerControlSettings = new ArrayList<ControlsSettingPlayer>();
		playerButtons = new ArrayList<TextButton>();
		for (Player player : game.players) {
			playerControlSettings.add(new ControlsSettingPlayer(game, player));
		}
		titleTable = new Table();
		settingsTable = new Table();
		buildTitleTable();
		buildTable();
	}
	
	public void buildTable() {
		setFillParent(false);
		setDebug(false); // This is optional, but enables debug lines for tables.
		top();

		add(titleTable);
		row();
		add(settingsTable); // first tab to display
		changeToPlayer(0);
	}

	public void buildTitleTable() {
		titleTable.setDebug(false);
		titleTable.setFillParent(false);
		titleTable.defaults().width(300).height(100).space(25);
		titleTable.center();
		titleTable.top();

		TextButton player1 = new TextButton("Player 1", skin);
		TextButton player2 = new TextButton("Player 2", skin);
		TextButton player3 = new TextButton("Player 3", skin);
		TextButton player4 = new TextButton("Player 4", skin);

		player1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeToPlayer(0);
			}
		});

		player2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeToPlayer(1);
			}
		});

		player3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeToPlayer(2);
			}
		});
		
		player4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeToPlayer(3);
			}
		});
		playerButtons.add(player1);
		playerButtons.add(player2);
		playerButtons.add(player3);
		playerButtons.add(player4);


		titleTable.add(player1);
		titleTable.add(player2);
		titleTable.add(player3);
		titleTable.add(player4);
	}
	
	public boolean isWaitingForInput() {
		for (ControlsSettingPlayer playerControls : playerControlSettings) {
			if (playerControls.isWaitingForInput()) return true;
		}
		return false;
	}
	
	public void refreshMenu() {
		for (ControlsSettingPlayer playerControls : playerControlSettings) {
			playerControls.refreshMenu();
		}
	}
	
	public void changeToPlayer(int player) {
		settingsTable.clearChildren();
		settingsTable.setFillParent(false);
		playerControlSettings.get(player).refreshMenu();
		settingsTable.add(playerControlSettings.get(player));
		settingsTable.layout();
		for (int i = 0; i < playerControlSettings.size(); i++) {
			if (i != player) {
				playerButtons.get(i).setColor(Color.WHITE);
				playerButtons.get(i).setText("Player " + (i + 1));
			}
			else {
				playerButtons.get(i).setColor(Color.RED);
				playerButtons.get(i).setText("<" + "Player " + (i + 1) + ">");
			}
		}
	}
}