package com.tank.game;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.tank.screen.LoadingScreen;
import com.tank.utils.Assets;

public class TankInfinity extends Game {
	public InputMultiplexer inputMultiplexer = new InputMultiplexer();
	public HashMap<String, Screen> screens = new HashMap<String, Screen>();
	public Screen previousScreen;
	public ArrayList<Player> players;
	
	@Override
	public void create() {	
		players = new ArrayList<Player>();
		screens.put("Loading", new LoadingScreen(this));
		super.setScreen(screens.get("Loading"));
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public void addInput(InputProcessor input) {
		inputMultiplexer.addProcessor(input);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public void removeInput(InputProcessor input) {
		inputMultiplexer.removeProcessor(input);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void setScreen(Screen screen) {
		previousScreen = super.getScreen();	//stores screen you were on before switching
		super.setScreen(screen);	//applies switch
	}
	
	@Override
	public void dispose() {
		Assets.dispose();
	}
}
