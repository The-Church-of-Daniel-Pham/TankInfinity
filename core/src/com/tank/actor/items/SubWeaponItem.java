package com.tank.actor.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.subweapons.ArtillerySubWeapon;
import com.tank.subweapons.BoomerangSubWeapon;
import com.tank.subweapons.ChakramSubWeapon;
import com.tank.subweapons.LandMineSubWeapon;
import com.tank.subweapons.LaserSubWeapon;
import com.tank.subweapons.PelletsSubWeapon;
import com.tank.subweapons.RocketSubWeapon;
import com.tank.subweapons.SubWeapon;
import com.tank.subweapons.VampiricFangSubWeapon;
import com.tank.utils.Assets;

public class SubWeaponItem extends AbstractItem{
	public SubWeapon sub;
	public static Texture box = Assets.manager.get(Assets.mysterybox);
	public static final float BOX_SIZE = 110;
	public static final float SCALE = 0.6f;
	public float rotationAnimTime = 0f;
	
	public SubWeaponItem(int row, int col) {
		this(col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
				row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
				null);
	}
	
	public SubWeaponItem(float x, float y, SubWeapon sub) {
		super(x, y, box);
		this.sub = sub;
		setOrigin(box.getWidth() / 2, box.getHeight() / 2);
		setHeight(BOX_SIZE);
		setWidth(BOX_SIZE);
		setScale(SCALE);
		rotationAnimTime = (float)(Math.random() * 10f);
		initializeHitbox();
	}
	
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	public void act(float delta) {
		rotationAnimTime += delta;
		while (rotationAnimTime >= 10f) rotationAnimTime -= 10f;
		
		float rotationPosMult = 0f;
		if (rotationAnimTime >= 5f) {
			rotationPosMult = (7.5f - rotationAnimTime) / 2.5f;
		}
		else {
			rotationPosMult = (rotationAnimTime - 2.5f) / 2.5f;
		}
		
		setRotation(30f * rotationPosMult);
		initializeHitbox();
	}
	
	public SubWeapon getSubWeapon() {
		if (sub == null)
			sub = randomSubWeapon();
		return sub;
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(45);
		f[0] = x+ v.x;
		f[1] = y +v.y;
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
	
	public static SubWeapon randomSubWeapon() {
		int random = (int)(Math.random() * 8);
		switch (random){
			case 0:
				return new RocketSubWeapon(2);
			case 1:
				return new ChakramSubWeapon(3);
			case 2:
				return new BoomerangSubWeapon(3);
			case 3:
				return new LandMineSubWeapon(2);
			case 4:
				return new PelletsSubWeapon(3);
			case 5:
				return new ArtillerySubWeapon(2);
			case 6:
				return new LaserSubWeapon(2);
			case 7:
				return new VampiricFangSubWeapon(2);
		}
		return null;
	}
}
