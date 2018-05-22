package com.ttr.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ttr.TankTankRevolution;
import com.ttr.actor.Background;
import com.ttr.utils.Assets;
import com.ttr.utils.Constants;

public class PauseMenu extends Stage implements InputProcessor {
	protected Game game;
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture black = Assets.manager.get(Assets.black);
	
	public PauseMenu(Game game) {
		super(new ScreenViewport());
		this.game = game;
		Background darken = new Background(black);
		// scale dark to fit screen
		darken.setScale((float)Constants.WINDOW_WIDTH/black.getWidth(), (float)Constants.WINDOW_HEIGHT/black.getHeight());
		super.addActor(darken);
		super.addActor(buildTable());
	}
	
	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		TextButton resumeButton = new TextButton("Resume", skin);
		TextButton settingsButton = new TextButton("Settings", skin);
		TextButton mainMenuButton = new TextButton("Main Menu", skin);
		
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
	        	 game.setScreen(TankTankRevolution.screens.get("Settings Menu"));
	        	 event.stop();
	         }
	      });
		
		mainMenuButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(TankTankRevolution.screens.get("Main Menu"));
	        	 event.stop();
	         }
	      });
		
		uiTable.defaults().width(200).height(75).space(25).center();
		uiTable.add(resumeButton);
		uiTable.row();
		uiTable.add(settingsButton);
		uiTable.row(); 
		uiTable.add(mainMenuButton);
		
		return uiTable;
	}
}
