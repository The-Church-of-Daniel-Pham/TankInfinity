package com.tank.utils;

import com.badlogic.gdx.Gdx;

public class Constants {
	// Window
	public static int[] RESOLUTIONS = {1280, 720, 1366, 768, 1600, 900, 1680, 1050, 1920, 1080, 1920, 1200, 2560, 1440, 3840, 2160};
	public static int RESOLUTION_INDEX = 8;
	public static final int PREFERRED_WINDOW_WIDTH = RESOLUTIONS[RESOLUTION_INDEX];
	public static final int PREFERRED_WINDOW_HEIGHT = RESOLUTIONS[RESOLUTION_INDEX+1];
	public static int WINDOW_WIDTH = Gdx.graphics.getWidth();	//start as screen size, since starting in fullscreen
	public static int WINDOW_HEIGHT = Gdx.graphics.getHeight();
	public static String[] WINDOW_MODES = {"Fullscreen", "Windowed Borderless", "Windowed"};
	public static int WINDOW_MODE_INDEX = 0;	//start in fullscreen
	public static boolean VSYNC_ENABLED = true;
	public static final int LEVEL1_WIDTH = 8;
	public static final int LEVEL1_HEIGHT = 6;
	
	public static void toggleWindowMode() {
		WINDOW_MODE_INDEX = (WINDOW_MODE_INDEX + 1) % WINDOW_MODES.length;	// increase index until end, then set back to 0
	}
	
	public static void updateWindow() {
		switch (WINDOW_MODE_INDEX) {
		case 0: // if constant WINDOW_MODE is Fullscreen
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // set fullscreen
			//reset to entire screen size
			WINDOW_WIDTH = Gdx.graphics.getWidth();
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
		Gdx.graphics.setVSync(VSYNC_ENABLED);
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
	
	public static void toggleVsync(boolean state) {
		VSYNC_ENABLED = state;
	}
}
