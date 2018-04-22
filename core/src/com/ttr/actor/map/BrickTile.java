package com.ttr.actor.map;

import com.ttr.utils.Assets;

public class BrickTile extends MapTile {
	public static final float STONE_RATE = 0.2f;
	
	public BrickTile(int row, int col) {
		super(row,col);
	}
	
	public void build() {
		double stoneOn = Math.random();
		if (stoneOn <= STONE_RATE)
		{
			super.addTexture(Assets.manager.get(Assets.stone));
		}
		else
		{
			super.addTexture(Assets.manager.get(Assets.brick));
		}
	}
}
