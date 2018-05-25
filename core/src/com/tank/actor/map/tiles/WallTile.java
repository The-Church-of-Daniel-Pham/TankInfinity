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
	
	public void checkCollision(Collidable other) {
		
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ArrayList<Polygon> getHitbox() {
		ArrayList<Polygon> a = new ArrayList<Polygon>();
		float[] f = new float[8];
		f[0] = getX();
		f[1] = getY();
		f[2] = getX() + SIZE;
		f[3] = getY();
		f[4] = getX() + SIZE;
		f[5] = getY() + SIZE;
		f[6] = getX();
		f[7] = getY() + SIZE;
		a.add(new Polygon(f));
		return a;
	}

}
