package com.tank.actor.map.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tank.actor.map.Map;

public class BorderTile extends WallTile{
	public BorderTile(int row, int col, Map map) {
		super(row, col, map);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		//draw nothing, for now
	}
}
