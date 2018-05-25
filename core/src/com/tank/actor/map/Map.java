package com.tank.actor.map;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.map.tiles.BorderTile;
import com.tank.actor.map.tiles.FloorTile;
import com.tank.actor.map.tiles.WallTile;
import com.tank.stage.Level;
import com.tank.utils.mapgenerator.MazeMaker;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Group {
	public int[][] layout;
	public AbstractMapTile[][] map;
	public Level level;

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
		this.level = level;
		MazeMaker mazeGen = new MazeMaker(width, height);
		mazeGen.createMaze(0, 0);
		layout = mazeGen.getMaze();
		map = new AbstractMapTile[layout.length][layout[0].length];
		for (int row = layout.length - 1; row >= 0; row--) {
			for (int col = 0; col < layout[row].length; col++) {
				if (layout[row][col] == 0) {
					AbstractMapTile tile = new FloorTile(layout.length - (1 + row), col, this); // polymorphic for
																								// simplicity
					// in this class
					map[row][col] = tile;
					super.addActor(tile); // kinda redundant, but may come in handy later
				} else if (layout[row][col] == 1) {
					// same as for grass, but for brick
					AbstractMapTile tile = new WallTile(layout.length - (1 + row), col, this);
					map[row][col] = tile;
					super.addActor(tile);
				}
			}
		}
	}

	public void setTilesVisibility(int minRow, int minCol, int maxRow, int maxCol, boolean vis) {
		// can us clamp later on
		minRow--;
		minCol--;
		maxRow++;
		maxCol++;
		if (maxRow > level.getMapHeight() - 1) {
			maxRow = level.getMapHeight() - 1;
		}
		if (maxCol > level.getMapWidth() - 1) {
			maxCol = level.getMapWidth() - 1;
		}
		if (minRow < 0) {
			minRow = 0;
		}
		if (minCol < 0) {
			minCol = 0;
		}
		for (int r = minRow; r <= maxRow; r++) {
			for (int c = minCol; c <= maxCol; c++) {
				map[r][c].inView = vis;
			}
		}
	}

	/**
	 * 
	 * @return the width, in pixels
	 */
	public int getSizeX() {
		return layout[0].length * AbstractMapTile.SIZE;
	}

	/**
	 * 
	 * @return the height, in pixels
	 */
	public int getSizeY() {
		return layout.length * AbstractMapTile.SIZE;
	}

	/**
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @return an integer array of size 2 {row, col} that represents the tile the
	 *         given pixel coordinates lie on
	 */
	public int[] getTileAt(float x, float y) {
		// due to rounding down with int tankMapRow = (int)((40*128-super.getY())/128),
		// which "rounds back up" when converting back to world coords
		int mapCol = (int) (x / AbstractMapTile.SIZE);
		int mapRow = (int) ((level.getMapHeight() * AbstractMapTile.SIZE - (y + 1)) / AbstractMapTile.SIZE);
		return new int[] { mapRow, mapCol };
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
	public ArrayList<AbstractMapTile> getBrickNeighbors(int row, int col) {
		ArrayList<AbstractMapTile> brickNeighbors = new ArrayList<AbstractMapTile>();
		for (int yOffset = -1; yOffset <= 1; yOffset++) {
			for (int xOffset = -1; xOffset <= 1; xOffset++) {
				int tempRow = row + yOffset;
				int tempCol = col + xOffset;
				if (tempRow < 0 || tempRow >= level.getMapHeight() || tempCol >= level.getMapWidth() || tempCol < 0) // edge
				{
					// handle edge vertices separately
					AbstractMapTile border = new BorderTile(layout.length - (1 + tempRow), tempCol, this); // see
																											// constructor
					brickNeighbors.add(border); // only in group for now, may add to array later
				} else if (layout[tempRow][tempCol] == 1) // normal brick in bounds
				{
					brickNeighbors.add(map[tempRow][tempCol]);
				}
			}
		}
		return brickNeighbors;
	}

	public void removeBrick(AbstractMapTile m) {
		if (!(m instanceof BorderTile)) {
			AbstractMapTile n = new FloorTile(layout.length - (1 + m.getRow()), m.getCol(), this);
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
	}
}