package com.ttr.actor.map;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: 2D Array of MapTiles. Constructor reads through a given array of 0's and 1's and places the corresponding MapTile.
 * To display the Map, each MapTile is drawn by iterating through them all.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Group {
	public int[][] layout;

	public Map(int width, int height) {
		MazeMaker mazeGen = new MazeMaker(width, height);
		mazeGen.createMaze(0, 0);
		layout = mazeGen.getMaze();
		for (int row = layout.length - 1; row >= 0; row--) {
			for (int col = 0; col < layout[row].length; col++) {
				MapTile tile = new MapTile(layout.length - (1 + row), col);
				if (layout[row][col] == 0) {
					tile.buildGrass();
				} else if (layout[row][col] == 1) {
					tile.buildBrick();
				}
				super.addActor(tile);
			}
		}
	}
	
	public int getSizeX() {
		return layout[0].length * MapTile.SIZE;
	}
	
	public int getSizeY() {
		return layout.length * MapTile.SIZE;
	}
	
	public boolean inMap(float x, float y) {
		return x > 0 && x < getSizeX() && y > 0 && y < getSizeY();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		for (Actor tile : super.getChildren()) {
				tile.draw(batch, alpha);
		}
	}
}