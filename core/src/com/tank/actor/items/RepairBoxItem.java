/**
 * @author The Church of Daniel Pham
 * Description:
 * This class represents the repair boxes player tanks can pick up.
 * Specifically, it takes care of displaying the texture and
 * allows player tanks to detect when a health pack is picked up.
 */
package com.tank.actor.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.utils.Assets;

public class RepairBoxItem extends AbstractItem {
	/**
	 * the texture for the repair box
	 */
	public static Texture repairbox = Assets.manager.get(Assets.repairbox);
	/**
	 * used for drawing
	 */
	public static final float SCALE = 1f;
	/**
	 * used for animating the repair box rotation
	 */
	public float rotationTime;
	/**
	 * the width and height, in pixels
	 */
	public static final float PACK_SIZE = 110;

	public RepairBoxItem(int row, int col, String temp) {
		this(col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
				row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2);
	}

	public RepairBoxItem(float x, float y) {
		super(x, y, repairbox);
		setOrigin(repairbox.getWidth() / 2, repairbox.getHeight() / 2);
		setHeight(PACK_SIZE);
		setWidth(PACK_SIZE);
		setScale(SCALE);
		rotationTime = (float) (Math.random() * 10f);
		initializeHitbox();
	}

	/**
	 * sets its hitbox based on its current position
	 */
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	/**
	 * rotates the texture for animation purposes
	 */
	public void act(float delta) {
		rotationTime += delta;
		while (rotationTime >= 10f) {
			rotationTime -= 10f;
		}
		float rotationMult = 0f;
		if (rotationTime >= 5f) {
			rotationMult = (7.5f - rotationTime) / 2.5f;
		} else {
			rotationMult = (rotationTime - 2.5f) / 2.5f;
		}

		setRotation(30f * rotationMult);
		initializeHitbox();
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth() * 0.6f, getHeight() * 0.6f);
		v.setAngle(direction);
		v.rotate(45);
		f[0] = x + v.x;
		f[1] = y + v.y;
		v.rotate(90);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(90);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(90);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}

	/**
	 * Called when a player tank picks up a repair box
	 * 
	 * @param p
	 *            the player tank that has picked up this repair box
	 * @return the amount of health this repair box should restore
	 */
	public int restoreHealth(PlayerTank p) {
		return (int) (p.getMaxHealth() * 0.4);
	}
}
