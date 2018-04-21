package com.ttr.level;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Demo level with Tank, Map and Camera rig. Based off of Stage. Insert enables freecam; Delete returns to chaseCam.
 * Home views the entire Map, PageUp and PageDown zoom, and End resets zoom.
 */

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ttr.actor.map.Map;
import com.ttr.actor.tank.Tank;
import com.ttr.utils.Constants;

public class Level extends Stage {
	public int width;
	public int height;
	public static Tank playerTank;
	public Map map;

	public Level(int width, int height) {
		super(new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		this.width = width;
		this.height = height;
		
		map = new Map(width, height);
		addActor(map);
		playerTank = new Tank(128, 128, 0, 0, map.layout);
		addActor(playerTank);
		
		// replace default stage OrthographicCamera with LevelCamera
		getViewport().setCamera(new LevelCamera(width, height, playerTank));
	}
}