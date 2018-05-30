package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.ui.Background;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class PauseMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture black = Assets.manager.get(Assets.black);
	
	public PauseMenu(TankInfinity game) {
		super(new ExtendViewport(Constants.PREFERRED_WINDOW_WIDTH, Constants.PREFERRED_WINDOW_HEIGHT));
		this.game = game;
		Background darken = new Background(black);
		// scale dark to fit screen
		darken.setScale(((float)Constants.WINDOW_WIDTH)/black.getWidth(), ((float)Constants.WINDOW_HEIGHT)/black.getHeight());
		super.addActor(darken);
		super.addActor(buildTable());
	}
	
	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(200).height(75).space(25).center();

		// Add widgets to the table here.
		TextButton resumeButton = new TextButton("Resume", skin);
		TextButton settingsButton = new TextButton("Settings", skin);
		TextButton mainMenuButton = new TextButton("Main Menu", skin);
		TextButton quitButton = new TextButton("Quit", skin);
		
		resumeButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.getScreen().resume();
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
		
		mainMenuButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.screens.get("Play").dispose();	//delete current game
	        	 game.screens.remove("Play");	//remove from screens
	        	 game.setScreen(game.screens.get("Main Menu"));
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
		
		uiTable.add(resumeButton);
		uiTable.row();
		uiTable.add(settingsButton);
		uiTable.row(); 
		uiTable.add(mainMenuButton);
		uiTable.row(); 
		uiTable.add(quitButton);
		
		return uiTable;
	}
}