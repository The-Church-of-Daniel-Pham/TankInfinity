package com.tank.game;

import com.tank.actor.vehicles.PlayerTank;
import com.tank.controls.ControlConstants;
import com.tank.controls.TankController;
import com.tank.table.PlayerCustomizationMenu;
import com.tank.table.PlayerHUD;

public class Player {
	protected String name;
	protected int playerNumber;
	protected String defaultColor;
	protected int defaultRow;
	protected int defaultCol;
	public PlayerTank tank;
	public PlayerCustomizationMenu customMenu;
	public PlayerHUD hud;
	public TankController controls;
	
	public Player(String name, int number, String defaultColor, int defaultRow, int defaultCol) {
		this.name = name;
		playerNumber = number;
		this.defaultColor = defaultColor;
		this.defaultRow = defaultRow;
		this.defaultCol = defaultCol;
		tank = new PlayerTank(number);
		tank.setCustom("tank color", this.defaultColor);
		tank.setMapPosition(defaultRow, defaultCol);
		customMenu = new PlayerCustomizationMenu(this);
		hud = new PlayerHUD(this);
		controls = ControlConstants.getPlayerControls(playerNumber);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}
	
	public String getDefaultColor() {
		return defaultColor;
	}
}
