package com.ttr.actor;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.ttr.utils.Assets;

public abstract class Collider extends LevelActor {
	public static boolean noclip = false;
	public Polygon hitbox;
	public ArrayList<Polygon> brickHitboxes;
	public Texture vertex = Assets.manager.get(Assets.vertex);;

	public void toggleNoclip() {
		noclip = !noclip;
	}

	public void setNoclip(boolean setting) {
		noclip = setting;
	}

	public boolean getNoclip() {
		return noclip;
	}

	public abstract float[] getVertices(float x, float y, float orientation);

	public void setHitbox(float x, float y, float orientation) {
		hitbox = new Polygon();
		float[] vertices = getVertices(x, y, orientation);
		hitbox.setVertices(vertices);
	}

	public Polygon getHitbox() {
		return hitbox;
	}

	public void setNeighboringBricksHitboxes(float x, float y, float orientation) {
		brickHitboxes = new ArrayList<Polygon>();
		brickHitboxes = super.getLevel().map.getHitboxes(super.getLevel().map
				.getBrickNeighbors(super.getLevel().map.getTileAt(x, y)[0], super.getLevel().map.getTileAt(x, y)[1]));
	}

	public ArrayList<Polygon> getNeighboringBricksHitboxes() {
		return brickHitboxes;
	}

	public abstract void onCollision();

	public void drawVertices(Batch batch, float alpha) {
		// if noclip is not enabled
		if (!noclip) {
			// draw own vertices
			for (int i = 0; i < 4; i++) {
				batch.draw(vertex, getHitbox().getVertices()[i * 2], getHitbox().getVertices()[i * 2 + 1]);
			}
			// draw neighboring bricks' vertices
			for (Polygon brickHitbox : brickHitboxes) {
				for (int i = 0; i < 4; i++) {
					batch.draw(vertex, brickHitbox.getVertices()[i * 2], brickHitbox.getVertices()[i * 2 + 1]);
				}
			}
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		// regular draw
		super.draw(batch, alpha);
		drawVertices(batch, alpha);
	}
}
