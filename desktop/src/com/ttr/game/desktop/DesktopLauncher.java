package com.ttr.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ttr.TankTankRevolution;
import com.ttr.utils.Constants;

//Authors: Gokul Swaminathan

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); // new config
		config.title = "Tank Tank Revolution";
		config.vSyncEnabled = false; // vertical sync is off
		config.foregroundFPS = 0; // setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // setting to 0 disables background fps throttling

		new LwjglApplication(new TankTankRevolution(), config); // creates the openGL window
		Constants.updateWindowMode();							// update window mode
	}
}