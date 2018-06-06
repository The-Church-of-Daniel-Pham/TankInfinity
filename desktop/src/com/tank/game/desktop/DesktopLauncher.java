package com.tank.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tank.game.TankInfinity;
import com.tank.utils.Constants;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); // new config
		config.title = "Tank Infinity";
		config.addIcon("ui/icon/tank_icon_32.png", FileType.Internal);	// app icon; cannot put in assets since this class is created earlier
		config.resizable = false;	// cannot resize window manually
		config.vSyncEnabled = false; // vertical sync is true
		config.foregroundFPS = 0; // setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // setting to 0 disables foreground fps throttling

		new LwjglApplication(new TankInfinity(), config); // creates the openGL window
		Constants.updateVideo();							// update window mode, resolution, and vSync
	}
}