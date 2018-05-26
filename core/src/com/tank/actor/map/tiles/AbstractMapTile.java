package com.tank.actor.map.tiles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.map.Map;
import com.tank.utils.Assets;

public abstract class AbstractMapTile extends Actor {
	protected ArrayList<Texture> textureList;
	protected float health;
	protected int row;
	protected int col;
	protected Map map;
	public boolean inView = true;
	private static final float HEALTH = 100;
	public static final int SIZE = Assets.manager.get(Assets.grass0).getWidth();

	public AbstractMapTile(int row, int col, Map map) {
		super.setPosition(col * AbstractMapTile.SIZE, row * AbstractMapTile.SIZE);
		this.row = row;
		this.col = col;
		this.map = map;
		textureList = new ArrayList<Texture>();
		health = HEALTH;
		build();
	}

	protected void addTexture(Texture tex) {
		textureList.add(tex);
	}

	protected abstract void build();

	@Override
	public void act(float delta) {
		//TODO add something
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		if (inView) {
			for (Texture tex : textureList) {
				batch.draw(tex, super.getX(), super.getY());
			}
		}

	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public Map getMap() {
		return map;
	}
}
