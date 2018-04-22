package com.ttr.actor.map;

import com.ttr.utils.Assets;

public class GrassTile extends MapTile{
	public static final float CLUMPS_RATE = 0.7f;
	public static final float FLOWERS_RATE = 0.1f;
	public static final float BUSHES_RATE = 0.05f;

	public GrassTile(int row, int col) {
		super(row,col);
	}
	
	public void build() {
		super.addTexture(Assets.manager.get(Assets.grass));
		
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
				super.addTexture(Assets.manager.get(Assets.clumps1));
			}
			else if (clumpsSelection > 0.33 && clumpsSelection <= 0.66)
			{
				super.addTexture(Assets.manager.get(Assets.clumps2));
			}
			else if (clumpsSelection > 0.66)
			{
				super.addTexture(Assets.manager.get(Assets.clumps3));
			}
		}
		if (flowersOn <= FLOWERS_RATE)
		{
			if (flowersSelection <= 0.33)
			{
				super.addTexture(Assets.manager.get(Assets.flowers1));
			}
			else if (flowersSelection > 0.33 && flowersSelection <= 0.66)
			{
				super.addTexture(Assets.manager.get(Assets.flowers2));
			}
			else if (flowersSelection > 0.66)
			{
				super.addTexture(Assets.manager.get(Assets.flowers3));
			}
		}
		if (bushesOn <= BUSHES_RATE)
		{
			if (bushesSelection <= 0.33)
			{
				super.addTexture(Assets.manager.get(Assets.bushes1));
			}
			else if (bushesSelection > 0.33 && bushesSelection <= 0.66)
			{
				super.addTexture(Assets.manager.get(Assets.bushes2));
			}
			else if (bushesSelection > 0.66)
			{
				super.addTexture(Assets.manager.get(Assets.bushes3));
			}
		}
	}
}
