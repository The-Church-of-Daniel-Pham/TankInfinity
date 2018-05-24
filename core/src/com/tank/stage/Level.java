package com.tank.stage;

import java.util.ArrayList;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tank.actor.vehicles.PlayerTank;

public class Level extends Stage {
	protected int width;
	protected int height;
	protected ArrayList<PlayerTank> playerTanks;
	protected Map map;
	protected LevelCamera camera;

	public Level(int width, int height) {
		
	}
}