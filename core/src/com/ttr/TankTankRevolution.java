package com.ttr;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Main game class. Starts yby setting the screen to LoadingScreen. Disposes of Assets when closed.
 */


import java.util.HashMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.ttr.screen.LoadingScreen;
import com.ttr.utils.Assets;

public class TankTankRevolution extends Game {
	public static InputMultiplexer inputMultiplexer = new InputMultiplexer();
	public static HashMap<String, Screen> screens = new HashMap<String, Screen>();;
	
	@Override
	public void create() {	
		screens.put("Loading", new LoadingScreen(this));
		super.setScreen(screens.get("Loading"));
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public static void addInput(InputProcessor input) {
		inputMultiplexer.addProcessor(input);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public static void removeInput(InputProcessor input) {
		inputMultiplexer.removeProcessor(input);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void dispose() {
		Assets.dispose();
	}
}