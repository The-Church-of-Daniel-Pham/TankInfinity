package com.tank.stage;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.map.Map;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.map.tiles.FloorTile;
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
		super(new ExtendViewport(15 * AbstractMapTile.SIZE, 9 * AbstractMapTile.SIZE));
		this.game = game;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;

		map = new Map(mapWidth, mapHeight, this);
		addActor(map);
		spawnInPlayers();
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
	
	/**
	 * Creates a new level of mapWidth number of tiles and mapHeight number of tiles
	 * @param mapWidth the width of the map in tiles
	 * @param mapHeight the height of the map in tiles
	 */
	public Level(TankInfinity game, int levelNum) {
		// world is first scaled to fit within the viewport, then the shorter dimension is lengthened to fill the viewport
		super(new ExtendViewport(15 * AbstractMapTile.SIZE, 9 * AbstractMapTile.SIZE));
		this.game = game;
		mapWidth = 40 + (int)(Math.pow(levelNum - 1, 1.5) / 2.5);
		mapHeight = 40 + (int)(Math.pow(levelNum - 1, 1.4) / 3);

		map = new Map(mapWidth, mapHeight, this);
		addActor(map);
		spawnInPlayers();
		int minEnemies = (int)(3.0 * Math.pow(levelNum, 0.25) + Math.pow(levelNum, 1.1));
		int maxEnemies = (int)(6.0 * Math.pow(levelNum, 0.25) + Math.pow(levelNum, 1.1));
		int enemyCount = (int)(Math.random() * (maxEnemies - minEnemies)) + minEnemies;
		ArrayList<FloorTile> emptySpaces = map.getEmptyNonSpawnFloorTiles();
		for (int i = 0; i < enemyCount; i++) {
			if (!emptySpaces.isEmpty()) {
				AbstractMapTile randomFloor = emptySpaces.remove((int)(Math.random() * emptySpaces.size()));
				int[] pos = new int[] {randomFloor.getCol() * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
										randomFloor.getRow() * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2};
				addActor(new BasicEnemy(pos[0], pos[1]));
			}
		}
		
		// replace default stage OrthographicCamera with LevelCamera
		camera = new LevelCamera(mapWidth, mapHeight, this.game.players);
		super.getViewport().setCamera(camera);
	}
	
	private void spawnInPlayers() {
		/**
		 * Player formations:
		 * 1 Player				2 Players
		 * 0 0 0 0 0			0 0 0 0 0
		 * 0 0 0 0 0			0 0 0 0 0
		 * 0 0 1 0 0			0 1 0 2 0
		 * 0 0 0 0 0			0 0 0 0 0
		 * 0 0 0 0 0			0 0 0 0 0
		 * 
		 * 3 Players			4 Players
		 * 0 0 0 0 0			0 0 0 0 0
		 * 0 0 1 0 0			0 1 0 2 0
		 * 0 0 0 0 0			0 0 0 0 0
		 * 0 2 0 3 0			0 3 0 4 0
		 * 0 0 0 0 0			0 0 0 0 0
		 */
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player p : game.players) {
			if (p.isEnabled()) {
				players.add(p);
				//p.initializeTank();
				//addActor(p.tank);
			}
		}
		
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (players.size() == 1) {
				p.initializeTank(map.getSpawnPoint()[0], map.getSpawnPoint()[1], 90);
			}
			else if (players.size() == 2) {
				if (i == 0) {
					p.initializeTank(map.getSpawnPoint()[0], map.getSpawnPoint()[1] - 1, 180);
				}
				else if (i == 1) {
					p.initializeTank(map.getSpawnPoint()[0], map.getSpawnPoint()[1] + 1, 0);
				}
			}
			else if (players.size() == 3) {
				if (i == 0) {
					p.initializeTank(map.getSpawnPoint()[0] + 1, map.getSpawnPoint()[1], 90);
				}
				else if (i == 1) {
					p.initializeTank(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1] - 1, 210);
				}
				else if (i == 2) {
					p.initializeTank(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1] + 1, 330);
				}
			}
			else if (players.size() == 4) {
				if (i == 0) {
					p.initializeTank(map.getSpawnPoint()[0] + 1, map.getSpawnPoint()[1] - 1, 90);
				}
				else if (i == 1) {
					p.initializeTank(map.getSpawnPoint()[0] + 1, map.getSpawnPoint()[1] + 1, 0);
				}
				else if (i == 2) {
					p.initializeTank(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1] - 1, 180);
				}
				else if (i == 3) {
					p.initializeTank(map.getSpawnPoint()[0] - 1, map.getSpawnPoint()[1] + 1, 270);
				}
			}
			addActor(p.tank);
		}
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
		for (AbstractVehicle vehicle : AbstractVehicle.vehicleList) {
			vehicle.remove();
		}
		AbstractVehicle.vehicleList.clear();
		for (AbstractProjectile projectile : AbstractProjectile.projectileList) {
			projectile.remove();
		}
		AbstractProjectile.projectileList.clear();
		super.dispose();
	}
}