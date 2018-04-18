package com.ttr.map;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: 2D Array of MapTiles. Constructor reads through a given array of 0's and 1's and places the corresponding MapTile.
 * To display the Map, each MapTile is drawn by iterating through them all.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Actor {
	private static MapTile[][] map;

	public Map(int[][] layout) {
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
			}
		}
	}
	
	public static int getSizeX() {
		return map[0].length * MapTile.SIZE;
	}
	
	public static int getSizeY() {
		return map.length * MapTile.SIZE;
	}
	
	public static boolean isValid(float x, float y) {
		return x > 0 && x < getSizeX() && y > 0 && y < getSizeY();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col].draw(batch);
			}
		}
	}
}