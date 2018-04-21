package com.ttr.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

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
	public LevelCamera camera;

	public Level(int width, int height) {
		super(new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		this.width = width;
		this.height = height;
		
		map = new Map(width, height, this);
		addActor(map);
		playerTank = new Tank(128, 128, 0, 0, this);
		addActor(playerTank);
		
		// replace default stage OrthographicCamera with LevelCamera
		camera = new LevelCamera(width, height, playerTank);
		super.getViewport().setCamera(camera);
		
		// receive input from multiple sources
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(camera);
		inputMultiplexer.addProcessor(playerTank);
		Gdx.input.setInputProcessor(inputMultiplexer);	// if receives true, stops
	}
}