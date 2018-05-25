package com.tank.stage;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.tiles.AbstractMapTile;
import com.tank.actor.vehicles.PlayerTank;

public class Level extends Stage {
	protected int width;
	protected int height;
	protected ArrayList<PlayerTank> players;
	protected Map map;
	protected LevelCamera camera;

	/**
	 * Creates a new level of width number of tiles and height number of tiles
	 * @param width the width in tiles
	 * @param height the height in tiles
	 */
	public Level(int width, int height) {
		// world is first scaled to fit within the viewport, then the shorter dimension is lengthened to fill the viewport
		super(new ExtendViewport(12 * AbstractMapTile.SIZE, 8 * AbstractMapTile.SIZE));
		this.width = width;
		this.height = height;

		map = new Map(width, height, this);
		addActor(map);
		players.add(new PlayerTank(1,null,null,0,0));
		for (PlayerTank p : players) {
			addActor(p);
		}
		
		// replace default stage OrthographicCamera with LevelCamera
		camera = new LevelCamera(width, height, players);
		super.getViewport().setCamera(camera);
	}
	
	public ArrayList<PlayerTank> getPlayers() {
		return players;
	}
	
	public LevelCamera getCamera() {
		return camera;
	}
}