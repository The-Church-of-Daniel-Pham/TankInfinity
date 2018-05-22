package com.ttr.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.ttr.stage.Level;
import com.ttr.utils.Assets;

public abstract class Collider extends LevelActor {
	protected static boolean noclip = false;

	protected Polygon currentHitbox;
	// protected ArrayList<Polygon> brickHitboxes;
	protected Texture vertex = Assets.manager.get(Assets.vertex);
	private final int VERTEX_SIZE = vertex.getWidth();

	public Collider(Level level) {
		super(level);
		currentHitbox = new Polygon(new float[8]);
	}

	public void toggleNoclip() {
		noclip = !noclip;
	}

	public void setNoclip(boolean setting) {
		noclip = setting;
	}

	public boolean getNoclip() {
		return noclip;
	}
	

	public abstract Polygon getHitboxAt(float x, float y, float orientation); // DIY


	public Polygon getHitbox() {
		return currentHitbox;
	}

	// public void setNeighboringBricksHitboxes(float x, float y, float orientation)
	// {
	// brickHitboxes = new ArrayList<Polygon>();
	// brickHitboxes = super.getLevel().map.getHitboxes(super.getLevel().map
	// .getBrickNeighbors(super.getLevel().map.getTileAt(x, y)[0],
	// super.getLevel().map.getTileAt(x, y)[1]));
	// }

	// public ArrayList<Polygon> getNeighboringBricksHitboxes() {
	// return brickHitboxes;
	// }

	public abstract void onCollision(); // DIY

	public void drawVertices(Batch batch, float alpha) {
		// if noclip is not enabled
		
		if (!noclip) {
			// draw own vertices
			// - VERTEX_SIZE/2f shifts the drawing of the vertex so that it's centered
			for (int i = 0; i < getHitbox().getVertices().length / 2; i++) {
				batch.draw(vertex, currentHitbox.getVertices()[i * 2] - VERTEX_SIZE / 2f,
						currentHitbox.getVertices()[i * 2 + 1] - VERTEX_SIZE / 2f);
			}
			// draw neighboring bricks' vertices
			// for (Polygon brickHitbox : brickHitboxes) {
			// for (int i = 0; i < 4; i++) {
			// batch.draw(vertex, brickHitbox.getVertices()[i * 2] - VERTEX_SIZE / 2f,
			// brickHitbox.getVertices()[i * 2 + 1] - VERTEX_SIZE / 2f);
			// }
			// }
		}
		
	}
}
