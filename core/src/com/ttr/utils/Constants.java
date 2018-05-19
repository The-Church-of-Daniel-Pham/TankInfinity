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
	public static int WINDOW_WIDTH = 1920;
	public static int WINDOW_HEIGHT = 1080;
	public static String[] WINDOW_MODES = {"Fullscreen", "Windowed Borderless", "Windowed"};
	public static int WINDOW_MODE_INDEX = 0;
	
	public static void toggleWindowMode() {
		WINDOW_MODE_INDEX = (WINDOW_MODE_INDEX + 1) % 3;	// increase index until 3, then set back to 0
		updateWindowMode();
	}
	
	public static void updateWindowMode() {
		switch (Constants.WINDOW_MODE_INDEX) {
		case 0: // if constant WINDOW_MODE is Fullscreen
			Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height); // set window size
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // set fullscreen
			break;
		case 1: // if constant WINDOW_MODE is Windowed Borderless
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true"); // set window to borderless
			Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height); // set window size
			break;
		case 2: // if constant WINDOW_MODE is Windowed
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false"); // set window to not-borderless
			Gdx.graphics.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT); // set constant windowed size
			break;
		}
	}
}