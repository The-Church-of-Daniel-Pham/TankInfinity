/**
 * @author The Church of Daniel Pham
 * Description:
 * This class stores information about the map layout of different
 * tile types in the level and takes care of creating the maptiles 
 * themselves, creating their hitboxes, and rendering them.
 * There are also useful methods that pertain to the map as a whole.
 */
package com.tank.actor.map;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.map.tiles.BorderTile;
import com.tank.actor.map.tiles.FloorTile;
import com.tank.actor.map.tiles.PortalTile;
import com.tank.actor.map.tiles.WallTile;
import com.tank.stage.Level;
import com.tank.utils.mapgenerator.MazeMaker;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Group {
	/**
	 * Double integer array where the indices indicate the row and column of a tile
	 * [row][col] and the value indicates the type of tile. The origin is in the
	 * bottom left corner.
	 */
	private int[][] layout;
	/**
	 * Double array where the indices indicate the row and column of a tile
	 * [row][col] and the value points to the tile object in that location. The
	 * origin is in the bottom left corner.
	 */
	public AbstractMapTile[][] map;
	/**
	 * the FloorTiles generated in this Map
	 */
	public ArrayList<FloorTile> floors;
	/**
	 * the WallTiles generated in this Map
	 */
	public ArrayList<WallTile> walls;
	/**
	 * the BorderTiles generated in this Map
	 */
	public ArrayList<BorderTile> border;
	/**
	 * The single PortalTile contained in this Map
	 */
	public PortalTile portal;
	/**
	 * An integer array whose first index contains the x coordinate and the second
	 * index contains the y coordinate of the center of the players' spawn location
	 * in this Map
	 */
	public int[] spawnZone;
	/**
	 * Used to point to the higher level object which implements this instance
	 */
	protected Level level;

	/**
	 * Creates a new randomly generated map of size width tiles and height tiles
	 * 
	 * @param width
	 *            width of Map
	 * @param height
	 *            height of Map
	 * @param level
	 *            the Level to which the Map belongs
	 */
	public Map(int width, int height, Level level) {
		// create level pointer
		this.level = level;
		// maze generation
		MazeMaker mazeGen = new MazeMaker(height - 1, width - 1); // create maze maker object in order to create a maze
		mazeGen.createMaze(0, 0);// must call createMaze(...) after object creation to generate maze
		mazeGen.addBorder(1); // give map a border. Vehicles and Projectiles cannot move there
		// Create the spawn area
		spawnZone = mazeGen.getRandomOpen(4);
		mazeGen.squareOpener(spawnZone[0], spawnZone[1], 2);
		floors = new ArrayList<FloorTile>();
		walls = new ArrayList<WallTile>();
		border = new ArrayList<BorderTile>();
		layout = mazeGen.getMaze(); // create layout pointer
		// using layout, create tile objects and store in its own double array
		map = new AbstractMapTile[height][width];
		for (int row = map.length - 1; row >= 0; row--) {
			for (int col = 0; col < map[row].length; col++) {
				AbstractMapTile tile = null;
				if (layout[row][col] == 0) { // layout values of 0 = floor tile
					// polymorphic for simplicity
					tile = new FloorTile(row, col, this);
					floors.add((FloorTile) tile);
				} else if (layout[row][col] == 1) {// layout values of 1 = wall tile
					tile = new WallTile(row, col, this);
					walls.add((WallTile) tile);
				}
				if (layout[row][col] == 2) { // layout values of 2 = border tile
					tile = new BorderTile(row, col, this);
					border.add((BorderTile) tile);
				}
				map[row][col] = tile; // store new object in array
				super.addActor(tile);// kinda redundant, but may come in handy later
			}
		}
		ArrayList<FloorTile> possibleSpawns = getEmptyNonSpawnFloorTiles();
		FloorTile portalSpawn = possibleSpawns.get((int) (Math.random() * possibleSpawns.size()));
		portal = new PortalTile(portalSpawn.getRow(), portalSpawn.getCol(), this);
		super.addActor(portal);
	}

	/**
	 * Used for efficiency purposes
	 * 
	 * @param border
	 *            the threshold of the distance, in tiles, beyond the screen that
	 *            tiles should still be rendered
	 */
	public void setFrustrumTilesVisible(int border) {
		int[] bottomLeft = getTileAt(getStage().getCamera().frustum.planePoints[0].x,
				getStage().getCamera().frustum.planePoints[0].y);
		int[] topRight = getTileAt(getStage().getCamera().frustum.planePoints[2].x,
				getStage().getCamera().frustum.planePoints[2].y);
		int minRow = MathUtils.clamp(bottomLeft[0] - border, 0, map.length - 1);
		int maxRow = MathUtils.clamp(topRight[0] + border, 0, map.length - 1);
		int minCol = MathUtils.clamp(bottomLeft[1] - border, 0, map[0].length - 1);
		int maxCol = MathUtils.clamp(topRight[1] + border, 0, map[0].length - 1);
		for (int r = minRow; r <= maxRow; r++) {
			for (int c = minCol; c <= maxCol; c++) {
				map[r][c].inView = true;
			}
		}
	}

	@Override
	public void act(float delta) {
		setFrustrumTilesVisible(2); // only render tiles on the screen for efficiency
		portal.act(delta); // check if any players are touching
	}

	/**
	 * 
	 * @return the width, in pixels
	 */
	public int getSizeX() {
		return map[0].length * AbstractMapTile.SIZE;
	}

	/**
	 * 
	 * @return the height, in pixels
	 */
	public int getSizeY() {
		return map.length * AbstractMapTile.SIZE;
	}

	public int[][] getLayout() {
		return layout;
	}

	/**
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @return an integer array of size 2 {row, col} that represents the tile the
	 *         given pixel coordinates lie on, clamped within the map
	 */
	public int[] getTileAt(float x, float y) {
		int mapRow = MathUtils.clamp((int) (y / AbstractMapTile.SIZE), 0, map.length - 1);
		int mapCol = MathUtils.clamp((int) (x / AbstractMapTile.SIZE), 0, map[0].length - 1);
		return new int[] { mapRow, mapCol };
	}

	/**
	 * Returns x/y coords given the row/col
	 * 
	 * @param row
	 *            the row of the given tile
	 * @param col
	 *            the column of the given tile
	 * @return The Vector2 whose x/y represent the coordinates of the center of the
	 *         given tile
	 */
	public Vector2 getCenterOfTilePos(int row, int col) {
		int x = col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2; // center of tile
		int y = row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		return new Vector2(x, y);
	}

	/**
	 * @return a random FloorTile in this Map instance
	 */
	public FloorTile getRandomFloorTile() {
		return (floors.get((int) (Math.random() * floors.size())));
	}

	/**
	 * 
	 * @return All FloorTiles in this Map, except for any that exist within the 5x5
	 *         spawn zone
	 */
	public ArrayList<FloorTile> getEmptyNonSpawnFloorTiles() {
		ArrayList<FloorTile> tiles = new ArrayList<FloorTile>();
		for (FloorTile floorTile : floors) {
			if (!(floorTile.getRow() >= spawnZone[0] - 2 && floorTile.getRow() <= spawnZone[0] + 2
					&& floorTile.getCol() >= spawnZone[1] - 2 && floorTile.getCol() <= spawnZone[1] + 2))
				tiles.add(floorTile);
		}
		return tiles;
	}

	/**
	 * @return a random WallTile in this Map instance
	 */
	public WallTile getRandomWallTile() {
		return (walls.get((int) (Math.random() * walls.size())));
	}

	/**
	 * @return a random BorderTile in this Map instance
	 */
	public BorderTile getRandomBorderTile() {
		return (border.get((int) (Math.random() * border.size())));
	}

	/**
	 * @return An integer array whose first index contains the x coordinate and the
	 *         second index contains the y coordinate of the center of the players'
	 *         spawn location in this Map
	 */
	public int[] getSpawnPoint() {
		return spawnZone;
	}

	/**
	 * @return The single PortalTile contained in this Map
	 */
	public PortalTile getPortalTile() {
		return portal;
	}

	/**
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @return returns true if the given coordinates in inside the Map, otherwise
	 *         returns false
	 */
	public boolean inMap(float x, float y) {
		return x > 0 && x < getSizeX() && y > 0 && y < getSizeY();
	}

	/**
	 * 
	 * @param row
	 *            a tile's row
	 * @param col
	 *            a tile's column
	 * @return an ArrayList of MapTiles of all bricks within one tile of the given
	 *         tile, i.e. a 3x3 zone
	 */
	public ArrayList<WallTile> getWallNeighbors(int row, int col) {
		ArrayList<WallTile> brickNeighbors = new ArrayList<WallTile>();
		for (int yOffset = -1; yOffset <= 1; yOffset++) {
			for (int xOffset = -1; xOffset <= 1; xOffset++) {
				int tempRow = row + yOffset;
				int tempCol = col + xOffset;
				if (tempRow >= 0 && tempRow < map.length && tempCol >= 0 && tempCol < map[0].length) {
					if (map[tempRow][tempCol] instanceof WallTile) {
						brickNeighbors.add((WallTile) map[tempRow][tempCol]);
					}
				}
			}
		}
		return brickNeighbors;
	}

	/**
	 * 
	 * @param row
	 *            a tile's row
	 * @param col
	 *            a tile's column
	 * @return an ArrayList of MapTiles of all bricks within 'size' tiles of the
	 *         given tile, i.e. a (size*2+1) square zone
	 */
	public ArrayList<WallTile> getWallNeighbors(int row, int col, int size) {
		ArrayList<WallTile> brickNeighbors = new ArrayList<WallTile>();
		for (int yOffset = -size; yOffset <= size; yOffset++) {
			for (int xOffset = -size; xOffset <= size; xOffset++) {
				int tempRow = row + yOffset;
				int tempCol = col + xOffset;
				if (tempRow >= 0 && tempRow < map.length && tempCol >= 0 && tempCol < map[0].length) {
					if (map[tempRow][tempCol] instanceof WallTile) {
						brickNeighbors.add((WallTile) map[tempRow][tempCol]);
					}
				}
			}
		}
		return brickNeighbors;
	}

	/**
	 * Removal: replaces the given tile to a FloorTile in the current location of
	 * the given tile
	 * 
	 * @param wall
	 *            The wall to be removed
	 */
	public void removeWall(AbstractMapTile wall) {
		if (wall.getParent() != null && wall.getParent().equals(this)) {
			layout[wall.getRow()][wall.getCol()] = 0;
			FloorTile floor = new FloorTile(wall.getRow(), wall.getCol(), this);
			addActor(floor);
			map[wall.getRow()][wall.getCol()] = floor;
			walls.remove(wall);
			floors.add(floor);
			wall.remove();
		}
	}

	/**
	 * Precondition: batch.begin() has been called
	 * 
	 * @Override
	 * @param batch
	 *            the current batch for drawing sprites
	 * @param alpha
	 *            transparency [0,1]
	 */
	public void draw(Batch batch, float alpha) {
		for (Actor tile : super.getChildren()) {
			tile.draw(batch, alpha);
		}
		portal.draw(batch, alpha);
	}
}