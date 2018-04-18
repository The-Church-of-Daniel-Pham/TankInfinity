package com.ttr.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.ttr.map.MapTile;
import com.ttr.tank.Tank;
import com.ttr.utils.Constants;
import com.ttr.utils.Keybinds;

public class LevelCamera extends OrthographicCamera implements InputProcessor{
	public int width, height;
	public Tank chaseTank;
	public boolean freeCamEnabled = false;

	public static final float SPEED = 2.0f;
	public static final float MIN_ZOOM = 0.5f;
	public static final float MAX_ZOOM = 5.0f;

	public LevelCamera(int width, int height, Tank chaseTank) {
		this.width = width;
		this.height = height;
		this.chaseTank = chaseTank;
		Gdx.input.setInputProcessor(this);
	}

	private void chase() {
		super.position.x = MathUtils.clamp(chaseTank.x, super.viewportWidth / 2f,
				width * MapTile.SIZE - super.viewportWidth / 2f);
		super.position.y = MathUtils.clamp(chaseTank.y, super.viewportHeight / 2f,
				width * MapTile.SIZE - super.viewportHeight / 2f);
		super.update();
	}

	private void viewFullMap() {
		super.position.set(width * MapTile.SIZE / 2f, height * MapTile.SIZE / 2f, 0);
		super.zoom = ((float) height * (float) MapTile.SIZE / (float) Constants.WINDOW_HEIGHT);
		super.update();
	}

	private void resetZoom() {
		super.zoom = 1.0f;
		super.update();
	}

	@Override
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
        return true;
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
