package com.tank.stage;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.game.Player;

public class LevelCamera extends OrthographicCamera {
	protected int width;
	protected int height;
	protected ArrayList<Player> players;
	protected boolean freeCamEnabled;

	public static final float BORDER_ZOOM = 0.5f;

	public LevelCamera(int width, int height, ArrayList<Player> players) {
		this.width = width;
		this.height = height;
		this.players = players;
	}

	private void chase() {
		int count = 0;
		float maxX = 0;
		float maxY = 0;
		float minX = 0;
		float minY = 0;
		for (Player p : players) {
			if (p.isEnabled()) {
				if (!p.tank.isDestroyed()) {
					if (count == 0) {
						maxX = minX = p.tank.getX();
						maxY = minY = p.tank.getY();
					} else {
						maxX = Math.max(maxX, p.tank.getX());
						maxY = Math.max(maxY, p.tank.getY());
						minX = Math.min(minX, p.tank.getX());
						minY = Math.min(minY, p.tank.getY());
					}
					count += 1;
				}
			}
		}

		if (count > 0) {
			super.zoom = Math.min(Math.max(1.0f,
					Math.max((maxX - minX) / (super.viewportWidth), (maxY - minY) / (super.viewportHeight))
							+ BORDER_ZOOM), Math.min(width * AbstractMapTile.SIZE / super.viewportWidth, height * AbstractMapTile.SIZE / super.viewportHeight));
			super.position.x = MathUtils.clamp((minX + maxX) / 2, (super.viewportWidth * zoom) / 2f,
					width * AbstractMapTile.SIZE - (super.viewportWidth * zoom) / 2f);
			super.position.y = MathUtils.clamp((minY + maxY) / 2, (super.viewportHeight * zoom) / 2f,
					height * AbstractMapTile.SIZE - (super.viewportHeight * zoom) / 2f);
			super.update();
		}
	}

	@Override
	public void update() {
		chase();
	}
}
