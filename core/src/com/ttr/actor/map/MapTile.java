package com.ttr.actor.map;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Tile for the Map class. Stores an ArrayList of Textures that layer on top of each other, and the position for the bottom left corner.
 * A MapTile can be created manually by repeatedly using addTexture.
 */

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.ttr.actor.StaticCollider;
import com.ttr.utils.Assets;

public abstract class MapTile extends StaticCollider {
	protected ArrayList<Texture> textureList;
	
	public static final int SIZE = Assets.manager.get(Assets.grass).getWidth();
	
	public MapTile(int row, int col) {
		super.setPosition(col * MapTile.SIZE, row * MapTile.SIZE);
		super.setHitbox(super.getX(), super.getY(), (float) Math.toRadians(super.getRotation()));
		textureList = new ArrayList<Texture>();
		build();
	}
	
	protected void addTexture(Texture tex) {
		textureList.add(tex);
	}
	
	public abstract void build();
	
	@Override
	public float[] getVertices(float x, float y, float orientation) {
		float[] vertices = new float[8];
		vertices[0] = x;
		vertices[1] = y;
		vertices[2] = x + SIZE;
		vertices[3] = y;
		vertices[4] = x + SIZE;
		vertices[5] = y + SIZE;
		vertices[6] = x;
		vertices[7] = y + SIZE;
		return vertices;
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		for (Texture tex : textureList) {
			batch.draw(tex, super.getX(), super.getY());
		}
	}
}