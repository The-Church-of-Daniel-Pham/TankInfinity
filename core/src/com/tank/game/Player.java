package com.tank.game;

import com.tank.actor.vehicles.PlayerTank;
import com.tank.controls.KeyboardMouseController;
import com.tank.controls.TankController;

public class Player {
	protected String name;
	public PlayerTank tank;
	public TankController controls;
	
	public Player(String name) {
		this.name = name;
		tank = new PlayerTank();
		controls = new KeyboardMouseController();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
