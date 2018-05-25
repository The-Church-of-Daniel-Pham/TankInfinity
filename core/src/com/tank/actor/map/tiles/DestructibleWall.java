package com.tank.actor.map.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.map.Map;
import com.tank.interfaces.Destructible;

public class DestructibleWall extends WallTile implements Destructible {
	protected static Texture[] tex;
	protected int[] healthThresholds;
	protected Texture[] texArray;
	public DestructibleWall(int row, int col, Map map, int[] thresholds) {
		super(row, col, map);
		healthThresholds = thresholds;
	}
	
	public void damage(Actor source, int damage) {
		
	}
	public void heal(Actor source, int heal) {
		
	}
	public void destroy() {
		remove();
	}
	public boolean isDestroyed() {
		return (getStage() == null);
	}

}
