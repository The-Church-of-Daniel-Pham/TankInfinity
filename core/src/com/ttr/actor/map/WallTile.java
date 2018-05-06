package com.ttr.actor.map;

import com.ttr.level.Level;
import com.ttr.utils.Assets;

public class WallTile extends MapTile {
	public WallTile(int row, int col, Level level) {
		super(row,col, level);
	}
	
	public void build() {
		super.addTexture(Assets.manager.get(Assets.carpet));
		super.addTexture(Assets.manager.get(Assets.crate));
	}
}
