package com.tank.stage;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.map.Map;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.vehicles.PlayerTank;

public class Level extends Stage {
	protected int mapWidth;
	protected int mapHeight;
	protected ArrayList<PlayerTank> players;
	protected Map map;
	protected LevelCamera camera;

	/**
	 * Creates a new level of mapWidth number of tiles and mapHeight number of tiles
	 * @param mapWidth the width of the map in tiles
	 * @param mapHeight the height of the map in tiles
	 */
	public Level(int mapWidth, int mapHeight) {
		// world is first scaled to fit within the viewport, then the shorter dimension is lengthened to fill the viewport
		super(new ExtendViewport(12 * AbstractMapTile.SIZE, 8 * AbstractMapTile.SIZE));
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;

		map = new Map(mapWidth, mapHeight, this);
		addActor(map);
		
		players = new ArrayList<PlayerTank>();
		players.add(new PlayerTank(1, Color.GREEN, 0, 0));
		for (PlayerTank p : players) {
			addActor(p);
		}
		
		// replace default stage OrthographicCamera with LevelCamera
		camera = new LevelCamera(mapWidth, mapHeight, players);
		super.getViewport().setCamera(camera);
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public ArrayList<PlayerTank> getPlayers() {
		return players;
	}
	
	public LevelCamera getCamera() {
		return camera;
	}
}