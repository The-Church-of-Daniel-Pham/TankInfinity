package com.tank.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tank.actor.ui.Cursor;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.controls.ControlConstants;
import com.tank.controls.TankController;
import com.tank.stats.Customization;
import com.tank.table.PlayerCustomizationMenu;
import com.tank.table.PlayerHUD;

public class Player {
	protected boolean enabled;
	protected String name;
	protected int playerNumber;
	protected String defaultColor;
	protected int defaultRow;
	protected int defaultCol;
	public PlayerTank tank;
	public Customization custom;
	public PlayerCustomizationMenu customMenu;
	public PlayerHUD hud;
	public TankController controls;
	public Cursor cursor;
	
	public Player(boolean enabled, String name, int playerNumber, String defaultColor, int defaultRow, int defaultCol) {
		this.enabled = enabled;
		this.name = name;
		this.playerNumber = playerNumber;
		this.defaultColor = defaultColor;
		this.defaultRow = defaultRow;
		this.defaultCol = defaultCol;
		this.controls = ControlConstants.getPlayerControls(playerNumber);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void enable(boolean e) {
		enabled = e;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}
	
	public String getDefaultColor() {
		return defaultColor;
	}
	
	public void initializeCustomMenu() {
		customMenu = new PlayerCustomizationMenu(this, enabled);
	}
	
	public void initializeCustom() {
		custom = new Customization();
		custom.setCustom("tank color", defaultColor);
	}
	
	public void initializeTank() {
		tank = new PlayerTank(playerNumber, this);
		tank.setMapPosition(defaultRow, defaultCol);
		hud = new PlayerHUD(this);
		controls = ControlConstants.getPlayerControls(playerNumber);
	}
	
	public void initializeTank(int row, int col, float rotation, boolean first) {
		if (first) {
			tank = new PlayerTank(playerNumber, this, row, col, rotation);
			hud = new PlayerHUD(this);
			controls = ControlConstants.getPlayerControls(playerNumber);
		}
		else {
			if (tank.getHealth() > 0) {
				int heal = tank.getMaxHealth() / 5;
				tank.heal(null, heal);
				tank.setupTank(row, col, rotation);
			}
		}
	}
	
	public void initializeCursor() {
		cursor = new Cursor(this, tank.getStage());
	}
}
