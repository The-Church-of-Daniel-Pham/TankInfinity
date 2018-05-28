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

	public static final float SPEED = 0;
	public static final float MIN_ZOOM = 0;
	public static final float MAX_ZOOM = 0;
	
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
		for (Player player : players) {
			if (!player.tank.isDestroyed()) {
				if (count == 0) {
					maxX = minX = player.tank.getX();
					maxY = minY = player.tank.getY();
				}
				else {
					maxX = Math.max(maxX, player.tank.getX());
					maxY = Math.max(maxY, player.tank.getY());
					minX = Math.min(minX, player.tank.getX());
					minY = Math.min(minY, player.tank.getY());
				}
				count += 1;
			}
		}
		
		if (count > 0) {
			super.zoom = Math.max(1.0f, Math.max((maxX - minX) / (super.viewportWidth), (maxY - minY) / (super.viewportHeight)) + 0.2f);
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
