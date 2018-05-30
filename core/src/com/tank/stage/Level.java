package com.tank.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.map.Map;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.projectiles.AbstractProjectile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.actor.vehicles.BasicEnemy;
import com.tank.game.Player;
import com.tank.game.TankInfinity;


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
			if (p.isEnabled()) {
				p.initializeTank();
				addActor(p.tank);
			}
		}
		
		for (int i = 0; i < 4; i++) {
			AbstractMapTile randomFloor = map.getRandomFloorTile();
			int[] pos = new int[] {randomFloor.getCol() * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
									randomFloor.getRow() * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2};
			addActor(new BasicEnemy(pos[0], pos[1]));
		}
		
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
	
	public TankInfinity getGame() {
		return game;
	}
	@Override
	public void dispose() {
		AbstractVehicle.vehicleList.clear();
		AbstractProjectile.projectileList.clear();
		super.dispose();
	}
}