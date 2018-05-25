package com.ttr.actor.map;

import com.ttr.stage.Level;
import com.ttr.utils.Assets;

public class BorderTile extends MapTile {

	public BorderTile(int row, int col, Level level) {
		super(row, col, level);
	}

	public void build() {
		super.addTexture(Assets.manager.get(Assets.stone0));	//change later
	}
}
