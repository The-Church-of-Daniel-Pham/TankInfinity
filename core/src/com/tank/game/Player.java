package com.tank.game;

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
	
	public Player(boolean enabled, String name, int playerNumber, String defaultColor, int defaultRow, int defaultCol) {
		this.enabled = enabled;
		this.name = name;
		this.playerNumber = playerNumber;
		this.defaultColor = defaultColor;
		this.defaultRow = defaultRow;
		this.defaultCol = defaultCol;
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
	
	public void initializeTankCustom() {
		custom = new Customization();
		custom.setCustom("tank color", defaultColor);
	}
	
	public void initializeTank() {
		tank = new PlayerTank(playerNumber);
		tank.setCustom(custom);
		tank.setMapPosition(defaultRow, defaultCol);
		hud = new PlayerHUD(this);
		controls = ControlConstants.getPlayerControls(playerNumber);
	}
}
