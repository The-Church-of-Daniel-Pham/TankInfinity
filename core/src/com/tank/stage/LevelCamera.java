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
		super.position.x = MathUtils.clamp(players.get(0).tank.getX(), super.viewportWidth / 2f,
				width * AbstractMapTile.SIZE - super.viewportWidth / 2f);
		super.position.y = MathUtils.clamp(players.get(0).tank.getY(), super.viewportHeight / 2f,
				width * AbstractMapTile.SIZE - super.viewportHeight / 2f);
		super.update();
	}
	
	@Override
	public void update() {
		chase();
	}
}
