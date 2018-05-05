package com.ttr.actor.map;

import com.ttr.utils.Assets;

public class BorderTile extends MapTile {

	public BorderTile(int row, int col) {
		super(row, col);
	}

	public void build() {
		super.addTexture(Assets.manager.get(Assets.crate));	//change later
	}
}
