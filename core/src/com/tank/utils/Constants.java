package com.tank.utils;

import com.badlogic.gdx.Gdx;

public class Constants {
	// Window
	public static CycleList<Integer> RESOLUTIONS = new CycleList<Integer>(new Integer[] { 1280, 720, 1366, 768, 1600, 900, 1680, 1050,
			1920, 1080, 1920, 1200, 2560, 1440, 3840, 2160, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() }, 16,
			true);
	public static CycleList<String> WINDOW_MODES = new CycleList<String>(
			new String[] { "Fullscreen", "Windowed Borderless", "Windowed" }, 0, true);
	public static boolean VSYNC_ENABLED = true;

	public static final int LEVEL1_WIDTH = 40;
	public static final int LEVEL1_HEIGHT = 40;

	public static void updateWindow() {
		switch (WINDOW_MODES.getIndex()) {
		case 0: // if constant WINDOW_MODE is Fullscreen
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // set fullscreen
			RESOLUTIONS.setIndex(16);
			break;
		case 1: // if constant WINDOW_MODE is Windowed Borderless
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true"); // set window to borderless
			// set window size
			Gdx.graphics.setWindowedMode((Integer) RESOLUTIONS.getCurrent(), (Integer) RESOLUTIONS.getNext());
			break;
		case 2: // if constant WINDOW_MODE is Windowed
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false"); // set window to not-borderless
			// set window size
			Gdx.graphics.setWindowedMode((Integer) RESOLUTIONS.getCurrent(), (Integer) RESOLUTIONS.getNext());
			break;
		}
		Gdx.graphics.setVSync(VSYNC_ENABLED);
	}

	public static void toggleVsync(boolean state) {
		VSYNC_ENABLED = state;
	}
}
