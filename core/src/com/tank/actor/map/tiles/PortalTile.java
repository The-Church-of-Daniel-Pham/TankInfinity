package com.tank.actor.map.tiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.tank.actor.map.Map;
import com.tank.interfaces.Collidable;
import com.tank.utils.Assets;

public class PortalTile extends AbstractMapTile implements Collidable {
	protected final Polygon hitbox;
	protected float rotationTime = 0f;
	
	public PortalTile(int row, int col, Map map) {
		super(row, col, map);
		super.setPosition(col * AbstractMapTile.SIZE + (SIZE / 2), row * AbstractMapTile.SIZE + (SIZE / 2));
		setOrigin(SIZE / 2, SIZE / 2);
		hitbox = initializeHitbox();
	}

	@Override
	protected void build() {
		super.addTexture(Assets.manager.get(Assets.portal));
	}
	
	public void act(float delta) {
		rotationTime += delta;
		while (rotationTime > 4f) rotationTime -= 4f;
		setRotation(-rotationTime * 90f);
	}
	
	public void draw(Batch batch, float a) {
		for (Texture tex : textureList) {
			batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
					super.getOriginX(), super.getOriginY(), tex.getWidth(), tex.getHeight(),
					super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, tex.getWidth(),
					tex.getHeight(), false, false);
		}
	}
	
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

	@Override
	public void drawVertices(Batch batch, float a) {
		// TODO Auto-generated method stub
		
	}
}
