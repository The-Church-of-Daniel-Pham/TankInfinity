/**
 * @author The Church of Daniel Pham
 * Description:
 * This class represents a WallTile, the walls with which
 * tanks typically collide. It also handles textures and
 * hitboxes (used for collisions). It does not handle
 * collisions itself.
 */
package com.tank.actor.map.tiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.tank.actor.map.Map;
import com.tank.interfaces.Collidable;
import com.tank.utils.Assets;

public class WallTile extends AbstractMapTile implements Collidable {
	/**
	 * the hitbox of the tile, used for collisions
	 */
	protected final Polygon hitbox;
	protected Texture debug = Assets.manager.get(Assets.vertex);

	public WallTile(int row, int col, Map map) {
		super(row, col, map);
		hitbox = initializeHitbox();
	}

	// used for debugging
	public void drawVertices(Batch batch, float a) {
		for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
			batch.draw(debug, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1], 0, 0,
					debug.getWidth(), debug.getHeight(), 1, 1, 0, 0, 0, debug.getWidth(), debug.getHeight(), false,
					false);
		}
	}

	public void draw(Batch batch, float a) {
		super.draw(batch, a);
		// if (inView) {
		// drawVertices(batch, a);
		// }
	}

	/**
	 * Replaces the given tile to a FloorTile in the current location of the given
	 * tile
	 */
	public void destroyWall() {
		map.removeWall(this);
	}

	@Override
	public void build() {
		super.addTexture(Assets.manager.get(Assets.grass0));
		double rand = Math.random();
		if (rand < 0.25) {
			super.addTexture(Assets.manager.get(Assets.stone1));
		} else if (rand < 0.5) {
			super.addTexture(Assets.manager.get(Assets.stone2));
		} else if (rand < 0.75) {
			super.addTexture(Assets.manager.get(Assets.stone3));
		} else {
			super.addTexture(Assets.manager.get(Assets.stone4));
		}
	}

	/**
	 * sets its hitbox based on its current position
	 */
	public Polygon initializeHitbox() {
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
		// never called; tiles don't handle collisions

	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		// never called; tiles don't handle collisions
		return null;
	}

	@Override
	public ArrayList<Collidable> getNeighbors() {
		// never called; tiles don't handle collisions
		return null;
	}

	@Override
	public Polygon getHitbox() {
		return hitbox;
	}

}
