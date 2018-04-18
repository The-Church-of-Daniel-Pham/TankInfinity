package com.ttr;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Main game class. Starts yby setting the screen to LoadingScreen. Disposes of Assets when closed.
 */

import com.badlogic.gdx.Game;
import com.ttr.screen.LoadingScreen;
import com.ttr.utils.Assets;

public class TankTankRevolution extends Game {

	@Override
	public void create() {
		setScreen(new LoadingScreen());
	}
	@Override
	public void dispose() {
		Assets.dispose();
	}
}