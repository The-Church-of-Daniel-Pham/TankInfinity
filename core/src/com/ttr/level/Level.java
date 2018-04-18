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
import com.ttr.map.Map;
import com.ttr.map.MazeMaker;
import com.ttr.tank.Tank;
import com.ttr.utils.Constants;

public class Level extends Stage {
	public int width;
	public int height;
	public static Tank playerTank;

	public Level(int width, int height) {
		super(new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		this.width = width;
		this.height = height;
		
		MazeMaker lgen = new MazeMaker(width, height);
		lgen.createMaze(0, 0);
		Map level = new Map(lgen.getMaze());
		addActor(level);
		
		playerTank = new Tank(256, 256, 0, 0);
		addActor(playerTank);
		
		// replace default stage OrthographicCamera with LevelCamera
		getViewport().setCamera(new LevelCamera(width, height, playerTank));
	}
}