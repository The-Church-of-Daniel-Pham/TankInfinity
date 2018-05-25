package com.tank.actor.map.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.map.Map;
import com.tank.utils.Assets;

public class FloorTile extends AbstractMapTile {
	protected static Texture tex = Assets.manager.get(Assets.grass0);
	
	public FloorTile(int row, int col, Map map) {
		super(row, col, map);
		super.addTexture(tex);
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub
		
	}
}
