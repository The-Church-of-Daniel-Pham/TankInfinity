package com.tank.actor.map.tiles;

import java.util.ArrayList;
import com.badlogic.gdx.math.Polygon;
import com.tank.actor.map.Map;
import com.tank.interfaces.Collidable;
import com.tank.utils.Assets;

public class WallTile extends AbstractMapTile implements Collidable {
	protected final Polygon hitbox;
	public WallTile(int row, int col, Map map) {
		//TODO set position to x/y coords
		super(row, col, map);
		hitbox = generateHitbox();
	}
	

	@Override
	public void build() {
		super.addTexture(Assets.manager.get(Assets.grass0));
		double rand = Math.random();
		if (rand < 0.25) {
			super.addTexture(Assets.manager.get(Assets.stone1));
		}
		else if (rand < 0.5) {
			super.addTexture(Assets.manager.get(Assets.stone2));
		}
		else if (rand < 0.75) {
			super.addTexture(Assets.manager.get(Assets.stone3));
		}
		else {
			super.addTexture(Assets.manager.get(Assets.stone4));
}
	}
	
	public Polygon generateHitbox() {
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


	@Override
	public Polygon getHitbox() {
		return hitbox;
	}

}
