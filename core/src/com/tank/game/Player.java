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
	public PlayerTank tank;
	public PlayerCustomizationMenu customMenu;
	public PlayerHUD hud;
	public TankController controls;
	
	public Player(String name, int number, String defaultColor) {
		this.name = name;
		playerNumber = number;
		this.defaultColor = defaultColor;
		tank = new PlayerTank(number);
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
