package com.tank.actor.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.interfaces.Destructible;

public class DestructibleWall extends WallTile implements Destructible {

	protected int[] healthThresholds;
	protected Texture[] texArray;
	public DestructibleWall(int[] thresholds, Texture[] tex, Texture t, float x, float y) {
		super(t, x, y);
		healthThresholds = thresholds;
		texArray = tex;
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
