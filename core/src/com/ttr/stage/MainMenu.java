package com.ttr.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ttr.TankTankRevolution;

public class MainMenu extends Stage implements InputProcessor{
	private Game game;
	
	public MainMenu(Game game) {
		super(new ScreenViewport());
		this.game = game;
		super.addActor(buildTable());
	}
	
	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		Skin skin = new Skin(Gdx.files.internal("menu/uiskin.json"));

		TextButton startButton = new TextButton("Start Game", skin);
		TextButton quitButton = new TextButton("Quit Game", skin);
		TextButton settingsButton = new TextButton("Settings", skin);
		
		startButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(((TankTankRevolution)game).gameScreen);
	        	 event.stop();
	         }
	      });
		
		quitButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 Gdx.app.exit();
	        	 event.stop();
	         }
	      });
		
		settingsButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(((TankTankRevolution)game).settingsMenuScreen);
	        	 event.stop();
	         }
	      });
		
		uiTable.add(startButton).padBottom(30);
		uiTable.row();
		uiTable.add(quitButton).padBottom(30);
		uiTable.row();
		uiTable.add(settingsButton).padBottom(30); 
		
		return uiTable;
	}
}
