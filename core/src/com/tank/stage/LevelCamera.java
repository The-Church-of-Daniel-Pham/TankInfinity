package com.tank.stage;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tank.actor.vehicles.PlayerTank;

public class LevelCamera extends OrthographicCamera implements InputProcessor {
	protected int width;
	protected int height;
	protected ArrayList<PlayerTank> playerTanks;
	protected boolean freeCamEnabled;

	public static final float SPEED = 0;
	public static final float MIN_ZOOM = 0;
	public static final float MAX_ZOOM = 0;
	
	public LevelCamera(int width, int height, ArrayList<PlayerTank> playerTanks) {
		
	}
	
	private void chase() {
		
	}
	
	private void viewFullMap() {
		
	}
	
	private void resetZoom() {
		
	}
	
	public void update() {
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
