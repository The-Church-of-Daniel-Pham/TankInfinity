package com.tank.utils;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;

public class Constants {
	// Window
	public static CycleList<Integer> RESOLUTIONS = new CycleList<Integer>(
			new Integer[] { 1280, 720, 1366, 768, 1600, 900, 1680, 1050, 1920, 1080, 1920, 1200, 2560, 1440, 3840, 2160,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight() },
			16, true);
	public static CycleList<String> WINDOW_MODES = new CycleList<String>(
			new String[] { "Fullscreen", "Windowed Borderless", "Windowed" }, 0, true);
	public static CycleList<String> VSYNC = new CycleList<String>(new String[] { "On", "Off" }, 0, true);
	public static CycleList<String> FPS_COUNTER = new CycleList<String>(new String[] { "On", "Off" }, 1, true);
	public static float CLEAR_COLOR = 25f / 255f;

	public static int DEFAULT_WIDTH = 1920;
	public static int DEFAULT_HEIGHT = 1080;
	public static final int LEVEL1_WIDTH = 40;
	public static final int LEVEL1_HEIGHT = 40;
	
	public static void createResolutionsCycleList() {
		RESOLUTIONS = new CycleList<Integer>(
				new Integer[] { 1280, 720, 1366, 768, 1600, 900, 1680, 1050, 1920, 1080, 1920, 1200, 2560, 1440, 3840, 2160,
						Gdx.graphics.getWidth(), Gdx.graphics.getHeight() },
				16, true);
	}

	public static void updateVideo() {
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
		switch (VSYNC.getIndex()) {
		case 0:	// if vSync is on
			Gdx.graphics.setVSync(true);
			break;
		case 1:	//if Vsync is off
			Gdx.graphics.setVSync(false);
			break;
		}
		switch (FPS_COUNTER.getIndex()) {
		case 0:	// if fps counter is on
			
			break;
		case 1:	//if fps counter is off
			
			break;
		}
	}
	
	public static String formatTime(float time) {
		long t = (long) (1000 * time);	// to ms
        final long min = TimeUnit.MILLISECONDS.toMinutes(t);
        final long sec = TimeUnit.MILLISECONDS.toSeconds(t - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d", min, sec);
    }
}
