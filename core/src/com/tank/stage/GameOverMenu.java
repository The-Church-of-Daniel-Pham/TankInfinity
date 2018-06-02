package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.ui.Background;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;

public class GameOverMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture red = Assets.manager.get(Assets.red);
	
	public GameOverMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		Background darken = new Background(red);
		// scale dark to fit screen
		darken.setFill(true);
		super.addActor(darken);
		super.addActor(buildTable());
	}
	
	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(300).height(100).space(25).center();
		
		// Add widgets to the table here.
		Label gameOverNotif = new Label("Game Over!", skin);
		gameOverNotif.setAlignment(Align.left);
		TextButton mainMenuButton = new TextButton("Main Menu", skin);
		mainMenuButton.getLabel().setAlignment(Align.left);
		TextButton quitButton = new TextButton("Quit", skin);
		quitButton.getLabel().setAlignment(Align.left);
		
		mainMenuButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 //game.screens.get("Play").dispose();	//delete current game
	        	 //game.screens.remove("Play");	//remove from screens
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
		uiTable.add(gameOverNotif).height(150);
		uiTable.row();
		uiTable.add(mainMenuButton);
		uiTable.row(); 
		uiTable.add(quitButton);
		
		return uiTable;
	}
}
