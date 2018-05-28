package com.tank.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.map.Map;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.actor.projectiles.AbstractProjectile;
import com.tank.actor.vehicles.AbstractVehicle;


public class Level extends Stage {
	protected TankInfinity game;
	protected int mapWidth;
	protected int mapHeight;
	protected Map map;
	protected LevelCamera camera;

	/**
	 * Creates a new level of mapWidth number of tiles and mapHeight number of tiles
	 * @param mapWidth the width of the map in tiles
	 * @param mapHeight the height of the map in tiles
	 */
	public Level(TankInfinity game, int mapWidth, int mapHeight) {
		// world is first scaled to fit within the viewport, then the shorter dimension is lengthened to fill the viewport
		super(new ExtendViewport(12 * AbstractMapTile.SIZE, 8 * AbstractMapTile.SIZE));
		this.game = game;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;

		map = new Map(mapWidth, mapHeight, this);
		addActor(map);
		
		for (Player p : game.players) {
			addActor(p.tank);
		}
		
		AbstractVehicle.vehicleList.clear();
		AbstractProjectile.projectileList.clear();
		
		// replace default stage OrthographicCamera with LevelCamera
		camera = new LevelCamera(mapWidth, mapHeight, this.game.players);
		super.getViewport().setCamera(camera);
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public LevelCamera getCamera() {
		return camera;
	}
	
	public Map getMap() {
		return map;
	}
}