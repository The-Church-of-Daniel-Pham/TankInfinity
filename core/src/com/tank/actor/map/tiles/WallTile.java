package com.tank.actor.map.tiles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.tank.actor.map.Map;
import com.tank.interfaces.Collidable;

public class WallTile extends AbstractMapTile implements Collidable {
	protected static Texture tex;
	
	public WallTile(int row, int col, Map map) {
		super(row, col, map);
	}
	

	@Override
	public void build() {
		// TODO Auto-generated method stub
		
	}
	
	public Polygon getHitbox() {
		float[] f = new float[8];
		f[0] = getX();
		f[1] = getY();
		f[2] = getX() + SIZE;
		f[3] = getY();
		f[4] = getX() + SIZE;
		f[5] = getY() + SIZE;
		f[6] = getX();
		f[7] = getY() + SIZE;
		return new Polygon(f);
	}

	@Override
	public void checkCollisions(ArrayList<Collidable> other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Collidable> getNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}

}
