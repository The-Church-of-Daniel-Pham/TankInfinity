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
import com.ttr.utils.Constants;

public class SettingsMenu extends Stage implements InputProcessor{
	private Game game;
	
	public SettingsMenu(Game game) {
		super(new ScreenViewport());
		this.game = game;
		TankTankRevolution.addInput(this);
		super.addActor(buildTable());
	}
	
	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		Skin skin = new Skin(Gdx.files.internal("menu/uiskin.json"));

		TextButton returnButton = new TextButton("Return to Main Menu", skin);
		TextButton resolutionButton = new TextButton(Constants.WINDOW_WIDTH + "x" + Constants.WINDOW_HEIGHT, skin);
		
		returnButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(((TankTankRevolution)game).mainMenuScreen);
	        	 event.stop();
	         }
	      });
		
		resolutionButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 resolutionButton.setText("");
	        	 event.stop();
	         }
	      });

		uiTable.padTop(30);
		uiTable.add(returnButton).padBottom(30);
		
		return uiTable;
	}
}
