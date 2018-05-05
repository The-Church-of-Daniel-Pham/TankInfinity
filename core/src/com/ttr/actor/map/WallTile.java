package com.ttr.actor.map;

import com.ttr.utils.Assets;

public class WallTile extends MapTile {
	public WallTile(int row, int col) {
		super(row,col);
	}
	
	public void build() {
		super.addTexture(Assets.manager.get(Assets.floor));
		super.addTexture(Assets.manager.get(Assets.crate));
	}
}
