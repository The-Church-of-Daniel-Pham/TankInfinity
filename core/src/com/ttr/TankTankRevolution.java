package com.ttr;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Main game class. Starts yby setting the screen to LoadingScreen. Disposes of Assets when closed.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.ttr.screen.GameScreen;
import com.ttr.screen.LoadingScreen;
import com.ttr.screen.MainMenuScreen;
import com.ttr.screen.SettingsMenuScreen;
import com.ttr.utils.Assets;

public class TankTankRevolution extends Game {
	public static InputMultiplexer inputMultiplexer = new InputMultiplexer();
	public LoadingScreen loadingScreen;
	public MainMenuScreen mainMenuScreen;
	public SettingsMenuScreen settingsMenuScreen;
	public GameScreen gameScreen;
	
	@Override
	public void create() {	
		loadingScreen = new LoadingScreen(this);
		super.setScreen(loadingScreen);
		mainMenuScreen = new MainMenuScreen(this);
		settingsMenuScreen = new SettingsMenuScreen(this);
		gameScreen = new GameScreen();
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