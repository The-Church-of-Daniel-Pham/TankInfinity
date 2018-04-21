package com.ttr.actor.map;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Tile for the Map class. Stores an ArrayList of Textures that layer on top of each other, and the position for the bottom left corner.
 * A MapTile can be created manually by repeatedly using addTexture. For common tiles, there are preset build methods, which have probabilities of adding certain layers.
 */

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.ttr.actor.StaticCollider;
import com.ttr.utils.Assets;

public class MapTile extends StaticCollider {
	private ArrayList<Texture> textureList;
	public Polygon tileHitbox;
	
	public static final int SIZE = Assets.manager.get(Assets.grass).getWidth();
	public static final float CLUMPS_RATE = 0.7f;
	public static final float FLOWERS_RATE = 0.1f;
	public static final float BUSHES_RATE = 0.05f;
	public static final float STONE_RATE = 0.2f;
	
	public MapTile(int row, int col) {
		super.setPosition(col * MapTile.SIZE, row * MapTile.SIZE);
		textureList = new ArrayList<Texture>();
		tileHitbox = new Polygon();
	}
	
	private void addTexture(Texture tex) {
		textureList.add(tex);
	}
	
	public void buildGrass() {
		addTexture(Assets.manager.get(Assets.grass));
		
		// bonus features
		double clumpsOn = Math.random();
		double clumpsSelection = Math.random();
		double flowersOn = Math.random();
		double flowersSelection = Math.random();
		double bushesOn = Math.random();
		double bushesSelection = Math.random();
		
		if (clumpsOn <= CLUMPS_RATE)
		{
			if (clumpsSelection <= 0.33)
			{
				addTexture(Assets.manager.get(Assets.clumps1));
			}
			else if (clumpsSelection > 0.33 && clumpsSelection <= 0.66)
			{
				addTexture(Assets.manager.get(Assets.clumps2));
			}
			else if (clumpsSelection > 0.66)
			{
				addTexture(Assets.manager.get(Assets.clumps3));
			}
		}
		if (flowersOn <= FLOWERS_RATE)
		{
			if (flowersSelection <= 0.33)
			{
				addTexture(Assets.manager.get(Assets.flowers1));
			}
			else if (flowersSelection > 0.33 && flowersSelection <= 0.66)
			{
				addTexture(Assets.manager.get(Assets.flowers2));
			}
			else if (flowersSelection > 0.66)
			{
				addTexture(Assets.manager.get(Assets.flowers3));
			}
		}
		if (bushesOn <= BUSHES_RATE)
		{
			if (bushesSelection <= 0.33)
			{
				addTexture(Assets.manager.get(Assets.bushes1));
			}
			else if (bushesSelection > 0.33 && bushesSelection <= 0.66)
			{
				addTexture(Assets.manager.get(Assets.bushes2));
			}
			else if (bushesSelection > 0.66)
			{
				addTexture(Assets.manager.get(Assets.bushes3));
			}
		}
	}
	
	public void buildBrick() {
		double stoneOn = Math.random();
		if (stoneOn <= STONE_RATE)
		{
			addTexture(Assets.manager.get(Assets.stone));
		}
		else
		{
		addTexture(Assets.manager.get(Assets.brick));
		}
	}
	
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