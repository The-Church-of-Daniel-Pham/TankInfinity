package com.tank.actor.map.tiles;

import com.tank.actor.map.Map;
import com.tank.utils.Assets;

public class FloorTile extends AbstractMapTile {	
	public FloorTile(int row, int col, Map map) {
		super(row, col, map);
	}

	@Override
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
