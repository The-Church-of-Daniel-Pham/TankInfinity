package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.screen.PlayScreen;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class MainMenu extends Stage implements InputProcessor{
	protected TankInfinity game;
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public MainMenu(TankInfinity game) {
		super(new ExtendViewport(Constants.PREFERRED_WINDOW_WIDTH, Constants.PREFERRED_WINDOW_HEIGHT));
		this.game = game;
		super.addActor(buildTable());
	}
	
	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		TextButton newGameButton = new TextButton("New Game", skin);
		TextButton resumeGameButton = new TextButton("Resume Game", skin);
		TextButton settingsButton = new TextButton("Settings", skin);
		TextButton quitButton = new TextButton("Quit", skin);
		
		newGameButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.screens.put("Play", new PlayScreen(game));	//creates or replaces with a new game
	        	 game.setScreen(game.screens.get("Play"));
	        	 event.stop();
	         }
	      });
		
		resumeGameButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 //goes to existing screen, if it was created
	        	 if (game.screens.get("Play") != null) {
	        		 game.setScreen(game.screens.get("Play"));
	        	 }
	        	 else {
	        		 //do nothing, there is no game to resume
	        	 }
	        	 event.stop();
	         }
	      });
		
		settingsButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(game.screens.get("Settings Menu"));
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
		
		uiTable.defaults().width(200).height(75).space(25).center();
		uiTable.add(newGameButton);
		uiTable.row();
		uiTable.add(resumeGameButton);
		uiTable.row();
		uiTable.add(settingsButton);
		uiTable.row(); 
		uiTable.add(quitButton);
		
		return uiTable;
	}
}