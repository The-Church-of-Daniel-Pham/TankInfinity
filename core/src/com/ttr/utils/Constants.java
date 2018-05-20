package com.ttr.utils;

import com.badlogic.gdx.Gdx;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: List of world constants. Can be called from any class by Constant.CONSTANT_NAME.
 */

public class Constants {
	// Window
	public static int[] RESOLUTIONS = {1280, 720, 1366, 768, 1600, 900, 1680, 1050, 1920, 1080};
	public static int RESOLUTION_INDEX = 8;
	public static int WINDOW_WIDTH = Gdx.graphics.getWidth();
	public static int WINDOW_HEIGHT = Gdx.graphics.getHeight();
	public static String[] WINDOW_MODES = {"Fullscreen", "Windowed Borderless", "Windowed"};
	public static int WINDOW_MODE_INDEX = 0;	//start in fullscreen
	
	public static void toggleWindowMode() {
		WINDOW_MODE_INDEX = (WINDOW_MODE_INDEX + 1) % WINDOW_MODES.length;	// increase index until end, then set back to 0
	}
	
	public static void updateWindow() {
		switch (WINDOW_MODE_INDEX) {
		case 0: // if constant WINDOW_MODE is Fullscreen
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // set fullscreen
			//reset to entire screen size
			WINDOW_WIDTH =  Gdx.graphics.getWidth();
			WINDOW_HEIGHT = Gdx.graphics.getHeight();			
			break;
		case 1: // if constant WINDOW_MODE is Windowed Borderless
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true"); // set window to borderless
			Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT); // set window size
			break;
		case 2: // if constant WINDOW_MODE is Windowed
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false"); // set window to not-borderless
			Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT); // set window size
			break;
		}
	}
	
	public static void toggleResolution(boolean increment) {
		if (RESOLUTION_INDEX > 0 && !increment) {	//if decreasing resolution
			RESOLUTION_INDEX -= 2;
		}
		else if (RESOLUTION_INDEX < RESOLUTIONS.length - 2 && increment) {	//if increasing resolution
			RESOLUTION_INDEX += 2;
		}
		WINDOW_WIDTH = RESOLUTIONS[RESOLUTION_INDEX];
		WINDOW_HEIGHT = RESOLUTIONS[RESOLUTION_INDEX + 1];
	}
}