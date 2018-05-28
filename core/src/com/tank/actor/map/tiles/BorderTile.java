package com.tank.actor.map.tiles;

import com.tank.actor.map.Map;
import com.tank.utils.Assets;

public class BorderTile extends WallTile{
	public BorderTile(int row, int col, Map map) {
		super(row, col, map);
	}
	
	@Override
	public void build() {
		super.addTexture(Assets.manager.get(Assets.grass0));
	}
}
