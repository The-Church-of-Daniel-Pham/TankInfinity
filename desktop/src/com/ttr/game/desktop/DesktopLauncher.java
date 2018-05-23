package com.ttr.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ttr.TankInfinity;
import com.ttr.utils.Constants;

//Authors: Gokul Swaminathan

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); // new config
		config.title = "Tank Infinity";
		config.addIcon("menu/dpham_32.png", FileType.Internal);	// app icon; cannot put in assets since this class is created earlier
		config.resizable = false;	// cannot resize window manually
		config.vSyncEnabled = false; // vertical sync is true
		config.foregroundFPS = 0; // setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // setting to 0 disables foreground fps throttling

		new LwjglApplication(new TankInfinity(), config); // creates the openGL window
		Constants.updateWindow();							// update window mode and resolution
	}
}