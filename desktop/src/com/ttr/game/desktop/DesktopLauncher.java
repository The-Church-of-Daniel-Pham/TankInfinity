package com.ttr.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ttr.TankTankRevolution;
import com.ttr.utils.Constants;

//Authors: Gokul Swaminathan

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();	// new config
		config.width = Constants.WINDOW_WIDTH;		// sets config width to constant WINDOW_WIDTH
        config.height = Constants.WINDOW_HEIGHT;	// sets config height to constant WINDOW_HEIGHT
	    config.resizable = true;					// window resize is on
	    config.vSyncEnabled = false; 				// vertical sync is off
	    config.foregroundFPS = 0; 					// setting to 0 disables foreground fps throttling
	    config.backgroundFPS = 0; 					// setting to 0 disables background fps throttling
        
        new LwjglApplication(new TankTankRevolution(), config);	// creates the openGL window
	    
	    DisplayMode mode = Gdx.graphics.getDisplayMode();									// finds display mode
	    if (Constants.WINDOW_MODE.equals("Fullscreen")) {									// if constant WINDOW_MODE is Fullscreen
	        Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);	// set windowed size
	        Gdx.graphics.setFullscreenMode(mode);																		// set fullscreen
	    }
	    else if (Constants.WINDOW_MODE.equals("Windowed Borderless")) {						// if constant WINDOW_MODE is Windowed Borderless
	        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");				// set window to borderless
	        Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);	// set windowed size
	    }
		else if (Constants.WINDOW_MODE.equals("Windowed")) {								// if constant WINDOW_MODE is Windowed
	        Gdx.graphics.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);	// set constant windowed size
	    }
	}
}