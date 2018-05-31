package com.tank.actor.items;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.interfaces.Collidable;
import com.tank.utils.Assets;

public abstract class AbstractItem extends Actor implements Collidable{

	protected Texture tex;
	protected Polygon hitbox;
	protected Texture debug = Assets.manager.get(Assets.vertex);
	
	public AbstractItem(Texture tex) {
		
	}
	
	@Override
	public void checkCollisions(ArrayList<Collidable> other) {
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawVertices(Batch batch, float a) {
		for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
			batch.draw(debug, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1], 0, 0,
					debug.getWidth(), debug.getHeight(), 1, 1, 0, 0, 0, debug.getWidth(), debug.getHeight(), false,
					false);
		}
	}

}
