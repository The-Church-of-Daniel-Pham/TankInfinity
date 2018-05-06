package com.ttr.actor.map;

import com.ttr.level.Level;
import com.ttr.utils.Assets;

public class FloorTile extends MapTile{

	public FloorTile(int row, int col, Level level) {
		super(row,col, level);
	}
	
	public void build() {
		super.addTexture(Assets.manager.get(Assets.carpet));
	}
}
