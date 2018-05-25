package com.tank.stage;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.vehicles.PlayerTank;

public class LevelCamera extends OrthographicCamera implements InputProcessor {
	protected int width;
	protected int height;
	protected ArrayList<PlayerTank> players;
	protected boolean freeCamEnabled;

	public static final float SPEED = 0;
	public static final float MIN_ZOOM = 0;
	public static final float MAX_ZOOM = 0;
	
	public LevelCamera(int width, int height, ArrayList<PlayerTank> players) {
		this.width = width;
		this.height = height;
		this.players = players;
	}
	
	private void chase() {
		super.position.x = MathUtils.clamp(players.get(0).getX(), super.viewportWidth / 2f,
				width * AbstractMapTile.SIZE - super.viewportWidth / 2f);
		super.position.y = MathUtils.clamp(players.get(0).getY(), super.viewportHeight / 2f,
				width * AbstractMapTile.SIZE - super.viewportHeight / 2f);
		super.update();
	}
	
	private void viewFullMap() {
		super.position.set(width * AbstractMapTile.SIZE / 2f, height * AbstractMapTile.SIZE / 2f, 0);
		super.zoom = (height * AbstractMapTile.SIZE / super.viewportHeight);
		super.update();
	}
	
	private void resetZoom() {
		super.zoom = 1.0f;
		super.update();
	}
	
	public void update() {
		// continuous polling - can hold
				// freecam movement
				if (freeCamEnabled) {
					if (Gdx.input.isKeyPressed(Keybinds.CAMERA_UP)) {
						super.translate(0f, LevelCamera.SPEED, 0f);
					}
					if (Gdx.input.isKeyPressed(Keybinds.CAMERA_LEFT)) {
						super.translate(-LevelCamera.SPEED, 0f, 0f);
					}
					if (Gdx.input.isKeyPressed(Keybinds.CAMERA_DOWN)) {
						super.translate(0f, -LevelCamera.SPEED, 0f);
					}
					if (Gdx.input.isKeyPressed(Keybinds.CAMERA_RIGHT)) {
						super.translate(LevelCamera.SPEED, 0f, 0f);
					}
					if (Gdx.input.isKeyPressed(Keybinds.CAMERA_ZOOM_OUT) && super.zoom <= LevelCamera.MAX_ZOOM) {
						super.zoom += 0.01f;
					}
					if (Gdx.input.isKeyPressed(Keybinds.CAMERA_ZOOM_IN) && super.zoom >= LevelCamera.MIN_ZOOM) {
						super.zoom -= 0.01f;
					}
					super.update();
				}
				// chase movement
				else {
					chase();
				}
				// System.out.println(super.position + " " + super.zoom + " zoom");
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// toggle keys - tap only
		// to turn freecam on/off
		if(keycode == Keybinds.CAMERA_TOGGLE_FREECAM) {
            if (freeCamEnabled) {
            	// resume chase
            	freeCamEnabled = false;
            	resetZoom();
            }
            else {
            	// freed
            	freeCamEnabled = true;
            }
        }
		// to reset zoom
		if (keycode == Keybinds.CAMERA_RESET_ZOOM && freeCamEnabled) {
			resetZoom();
		}
		// to view full map
		if (keycode == Keybinds.CAMERA_VIEW_FULL_MAP && freeCamEnabled) {
			viewFullMap();
		}
        return false;	// if returns true, multiplexer would stop
	}
	
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
