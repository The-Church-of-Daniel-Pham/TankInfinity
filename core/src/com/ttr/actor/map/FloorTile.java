package com.ttr.actor.map;

import com.ttr.utils.Assets;

public class FloorTile extends MapTile{

	public FloorTile(int row, int col) {
		super(row,col);
	}
	
	public void build() {
		super.addTexture(Assets.manager.get(Assets.floor));
	}
}
