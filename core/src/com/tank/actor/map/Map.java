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
	public ArrayList<FloorTile> floors;
	public ArrayList<WallTile> walls;
	public ArrayList<BorderTile> border;
	public PortalTile portal;
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
		MazeMaker mazeGen = new MazeMaker(height, width); // create maze maker object in order to create a maze
		mazeGen.createMaze(0, 0);// must call createMaze(...) after object creation to generate maze
		mazeGen.clearBottomLeftCorner(5); // Clears out corner so tank doesn't spawn on bricks
		mazeGen.addBorder(1); // give map a border. Vehicles and Projectiles cannot move there
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
		FloorTile portalSpawn = getRandomFloorTile();
		portal = new PortalTile(portalSpawn.getRow(), portalSpawn.getCol(), this);
		super.addActor(portal);
	}

	public void setFrustrumTilesVisible(int border) {
		int[] bottomLeft = getTileAt(getStage().getCamera().frustum.planePoints[0].x, getStage().getCamera().frustum.planePoints[0].y);
		int[] topRight =  getTileAt(getStage().getCamera().frustum.planePoints[2].x, getStage().getCamera().frustum.planePoints[2].y);
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
		setFrustrumTilesVisible(2);
		portal.act(delta);
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
	
	public int[][] getLayout(){
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
	
	public Vector2 getCenterOfTilePos(int row, int col) {
		int x = col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		int y = row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		return new Vector2(x, y);
	}
	
	public FloorTile getRandomFloorTile() {
		return (floors.get((int)(Math.random() * floors.size())));
	}
	public WallTile getRandomWallTile() {
		return (walls.get((int)(Math.random() * walls.size())));
	}
	public BorderTile getRandomBorderTile() {
		return (border.get((int)(Math.random() * border.size())));
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
	 * @param a
	 *            tile's row
	 * @param a
	 *            tile's column
	 * @return an ArrayList of MapTiles of all bricks within two tiles of the given
	 *         tile
	 */
	public ArrayList<AbstractMapTile> getWallNeighbors(int row, int col) {
		ArrayList<AbstractMapTile> brickNeighbors = new ArrayList<AbstractMapTile>();
		for (int yOffset = -1; yOffset <= 1; yOffset++) {
			for (int xOffset = -1; xOffset <= 1; xOffset++) {
				int tempRow = MathUtils.clamp(row + yOffset, 0, map.length - 1);
				int tempCol = MathUtils.clamp(col + xOffset, 0, map[0].length - 1);
				if (map[tempRow][tempCol] instanceof WallTile) {
					brickNeighbors.add(map[tempRow][tempCol]);
				}
			}
		}
		return brickNeighbors;
	}

	public void removeWall(AbstractMapTile m) {
		if (!(m instanceof BorderTile)) {
			AbstractMapTile n = new FloorTile(m.getRow(), m.getCol(), this);
			addActor(n);
			map[m.getRow()][m.getCol()] = n;
			layout[m.getRow()][m.getCol()] = 0;
			m.remove();
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