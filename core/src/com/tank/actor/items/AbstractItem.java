/**
 * @author The Church of Daniel Pham
 * Description:
 * This class represents item pickups that spawn throughout the map 
 * and help determine when that item is picked up. The proper 
 * classes that contain the functionality of each specific item 
 * take over from there.
 */
package com.tank.actor.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.interfaces.Collidable;
import com.tank.utils.Assets;

public abstract class AbstractItem extends Actor implements Collidable {
	/**
	 * array of all items in existence. used for detecting collisions
	 */
	public static ArrayList<AbstractItem> items = new ArrayList<AbstractItem>();
	/**
	 * the texture of the item on the map
	 */
	protected Texture tex;
	/**
	 * the hitbox of the item
	 */
	protected Polygon hitbox;
	protected Texture debug = Assets.manager.get(Assets.vertex);

	public AbstractItem(float x, float y, Texture tex) {
		setX(x);
		setY(y);
		this.tex = tex;
		items.add(this);
	}

	@Override
	public void checkCollisions(ArrayList<Collidable> other) {
		// items do not check collisions with tanks; rather, the tanks check the
		// collisions with items
	}

	@Override
	public abstract Polygon getHitboxAt(float x, float y, float direction);

	@Override
	public Polygon getHitbox() {
		// TODO Auto-generated method stub
		return hitbox;
	}

	@Override
	public ArrayList<Collidable> getNeighbors() {
		// this method is never called on items
		return null;
	}

	/**
	 * items are destroyed upon pickup or when the level is disposed of
	 */
	public void destroy() {
		super.remove();
		items.remove(this);
	}

	public void draw(Batch batch, float a) {
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), tex.getWidth(), tex.getHeight(), super.getScaleX(), super.getScaleY(),
				super.getRotation(), 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}

	@Override
	//used for debugging
	public void drawVertices(Batch batch, float a) {
		for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
			batch.draw(debug, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1], 0, 0,
					debug.getWidth(), debug.getHeight(), 1, 1, 0, 0, 0, debug.getWidth(), debug.getHeight(), false,
					false);
		}
	}

}
