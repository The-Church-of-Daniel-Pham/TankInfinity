/**
 * @author The Church of Daniel Pham
 * Description:
 * This class stores information about the position, 
 * hitbox, and texture of a tile in the map. It also deals
 * with the drawing of the appropriate texture of the tile.
 */
package com.tank.actor.map.tiles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.map.Map;
import com.tank.utils.Assets;

public abstract class AbstractMapTile extends Actor {
	/**
	 * The texture(s) used for drawing this tile
	 */
	protected ArrayList<Texture> textureList;
	// not used
	protected float health;
	/**
	 * the row position of the tile
	 */
	protected int row;
	/**
	 * the column position of the tile
	 */
	protected int col;
	/**
	 * the Map to which this tile belongs
	 */
	protected Map map;
	/**
	 * upon creation, MapTiles are not drawn for efficiency purposes unless it is on
	 * the screen
	 */
	public boolean inView = false;
	// not used
	private static final float HEALTH = 100;
	/**
	 * the size of the tile (a square), in pixels
	 */
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

	/**
	 * adds a texture to be drawn on top of any existing textures in this tile
	 * 
	 * @param tex
	 *            the texture to be added
	 */
	protected void addTexture(Texture tex) {
		textureList.add(tex);
	}

	/**
	 * Randomly chooses the textures to be drawn for the specific tile out of a list
	 * of potential textures
	 */
	protected abstract void build();

	@Override
	public void act(float delta) {
		// TODO add something
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if (inView) {
			for (Texture tex : textureList) {
				batch.draw(tex, super.getX(), super.getY());
			}
		}

	}
 /**
  * 
  * @return the row position of this tile
  */
	public int getRow() {
		return row;
	}
/**
 * 
 * @return the column position of this tile
 */
	public int getCol() {
		return col;
	}
/**
 * 
 * @return the Map to which this tile belongs
 */
	public Map getMap() {
		return map;
	}
}
