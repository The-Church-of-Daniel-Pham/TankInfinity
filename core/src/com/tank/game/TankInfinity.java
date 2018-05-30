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
		// create players
		players.add(new Player(true, "Player 1", 1, "red", 1, 1));
		players.add(new Player(false, "Player 2", 2, "blue", 1, 2));
		players.add(new Player(false, "Player 3", 3, "green", 1, 3));
		players.add(new Player(false, "Player 4", 4, "yellow", 1, 4));
		// go to loading screen
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
		if (screen != null) {
			previousScreen = super.getScreen();	//stores screen you were on before switching
			super.setScreen(screen);	//applies switch
		}
	}
	
	@Override
	public void dispose() {
		Assets.dispose();
	}
}
