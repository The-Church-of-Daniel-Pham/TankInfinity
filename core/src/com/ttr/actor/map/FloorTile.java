package com.ttr.actor.map;

import com.ttr.stage.Level;
import com.ttr.utils.Assets;

public class FloorTile extends MapTile{

	public FloorTile(int row, int col, Level level) {
		super(row,col, level);
	}
	
	public void build() {
		double rand = Math.random();
		if (rand < 0.25) {
			super.addTexture(Assets.manager.get(Assets.grass1));
		}
		else if (rand < 0.5) {
			super.addTexture(Assets.manager.get(Assets.grass2));
		}
		else if (rand < 0.75) {
			super.addTexture(Assets.manager.get(Assets.grass3));
		}
		else {
			super.addTexture(Assets.manager.get(Assets.grass4));
		}
	}
}
