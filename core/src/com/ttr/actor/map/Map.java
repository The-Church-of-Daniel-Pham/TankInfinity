package com.ttr.actor.map;
/**
 * @author Samuel
 * @version April 21st 2018
 * 
 * Description: Array of MapTiles in the layout from MazeMaker. To display the Map, each MapTile is drawn by iterating through them all.
 */

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ttr.level.Level;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Group {
	public int[][] layout;
	public MapTile[][] map;
	public Level level;

	public Map(int width, int height, Level level) {
		this.level = level;
		MazeMaker mazeGen = new MazeMaker(width, height);
		mazeGen.createMaze(0, 0);
		layout = mazeGen.getMaze();
		map = new MapTile[layout.length][layout[0].length];
		for (int row = layout.length - 1; row >= 0; row--) {
			for (int col = 0; col < layout[row].length; col++) {
				MapTile tile = new MapTile(layout.length - (1 + row), col);
				if (layout[row][col] == 0) {
					tile.buildGrass();
				} else if (layout[row][col] == 1) {
					tile.buildBrick();
				}
				map[row][col] = tile;
				super.addActor(tile);	//kinda redundant, but may come in handy later
			}
		}
	}
	
	public int getSizeX() {
		return layout[0].length * MapTile.SIZE;
	}
	
	public int getSizeY() {
		return layout.length * MapTile.SIZE;
	}
	
	public int[] getTileAt(float x, float y) {
		// due to rounding down with int tankMapRow = (int)((40*128-super.getY())/128),
		// which "rounds back up" when converting back to world coords
		int mapCol = (int) (x / MapTile.SIZE);
		int mapRow = (int) ((level.height * MapTile.SIZE - y) / MapTile.SIZE);
		return new int[] {mapRow, mapCol};
	}
	
	public boolean inMap(float x, float y) {
		return x > 0 && x < getSizeX() && y > 0 && y < getSizeY();
	}
	
	public ArrayList<MapTile> getBrickNeighbors(int row, int col) {
		ArrayList<MapTile> brickNeighbors = new ArrayList<MapTile>();
		for (int yOffset = -2; yOffset <= 2; yOffset++) {
			for (int xOffset = -2; xOffset <= 2; xOffset++) {
				int tempRow = row + yOffset;
				int tempCol = col + xOffset;
				if (tempRow < 0 || tempRow >= level.height || tempCol >= level.width || tempCol < 0) // edge
				{
					//handle edge vertices separately
					MapTile border = new MapTile(layout.length - (1 + tempRow), tempCol);	// see constructor
					border.buildBrick();
					brickNeighbors.add(border);
				}
				else if (level.map.layout[tempRow][tempCol] == 1) // normal brick in bounds
				{
					brickNeighbors.add(level.map.map[tempRow][tempCol]);
				}
			}
		}
		return brickNeighbors;
	}
	
	public ArrayList<Polygon> getHitboxes(ArrayList<MapTile> tiles) {
		ArrayList<Polygon> hitboxes = new ArrayList<Polygon>();
		for (MapTile tile : tiles) {
			tile.setHitbox(tile.getX(), tile.getY(), (float) Math.toRadians(tile.getRotation()));
			hitboxes.add(tile.getHitbox());
		}
		return hitboxes;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		for (Actor tile : super.getChildren()) {
				tile.draw(batch, alpha);
		}
	}
}