package com.ttr.actor.map;

import com.ttr.stage.Level;
import com.ttr.utils.Assets;

public class WallTile extends MapTile {
	public WallTile(int row, int col, Level level) {
		super(row,col, level);
	}
	
	public void build() {
		super.addTexture(Assets.manager.get(Assets.grass0));
		double rand = Math.random();
		if (rand < 0.25) {
			super.addTexture(Assets.manager.get(Assets.stone1));
		}
		else if (rand < 0.5) {
			super.addTexture(Assets.manager.get(Assets.stone2));
		}
		else if (rand < 0.75) {
			super.addTexture(Assets.manager.get(Assets.stone3));
		}
		else {
			super.addTexture(Assets.manager.get(Assets.stone4));
		}
	}
}
