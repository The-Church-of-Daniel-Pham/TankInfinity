package com.tank.stage;

import java.util.ArrayList;

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
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.screen.PlayScreen;
import com.tank.table.PlayerResults;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class GameOverMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected PlayScreen playscreen;
	protected Label statsLabel;
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture red = Assets.manager.get(Assets.red);
	private ArrayList<PlayerResults> playerResultsTable;
	
	public GameOverMenu(TankInfinity game, PlayScreen playscreen) {
		super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
		this.game = game;
		this.playscreen = playscreen;
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
		Label gameOverNotif = new Label("Game Over!", skin, "title");
		gameOverNotif.setAlignment(Align.center);
		statsLabel = new Label("Reached level " + " in " + " minutes", skin, "medium");
		statsLabel.setAlignment(Align.center);
		TextButton restartButton = new TextButton("Restart", skin);
		restartButton.getLabel().setAlignment(Align.left);
		TextButton mainMenuButton = new TextButton("Main Menu", skin);
		mainMenuButton.getLabel().setAlignment(Align.left);
		TextButton quitButton = new TextButton("Quit", skin);
		quitButton.getLabel().setAlignment(Align.left);
		
		restartButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.screens.get("Play").dispose();	//delete current game
	        	 game.screens.remove("Play");	//remove from screens
	        	 game.screens.put("Play", new PlayScreen(game)); // creates or replaces with a new game
	        	 game.setScreen(game.screens.get("Play"));
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
		
		uiTable.add(gameOverNotif).width(600).height(150).colspan(4);
		uiTable.row();
		uiTable.add(statsLabel).width(500).colspan(4);
		uiTable.row();
		
		playerResultsTable = new ArrayList<PlayerResults>();
		Table placeholder = new Table();
		for (Player p : game.players) {
			if (p.isEnabled()) {
				PlayerResults playerResult = new PlayerResults(p);
				playerResultsTable.add(playerResult);
				//uiTable.add(playerResult).width(400).height(300);
			}
			else {
				// placeholder scaled to fraction of the width of the entire table width
				//uiTable.add(placeholder).width(400).height(300);
			}
		}
		
		switch (playerResultsTable.size()) {
			case 1:
				uiTable.add(playerResultsTable.get(0)).width(1600).height(300);
				break;
			case 2:
				uiTable.add(playerResultsTable.get(0)).width(800).height(300);
				uiTable.add(playerResultsTable.get(1)).width(800).height(300);
				break;
			case 3:
				uiTable.add(playerResultsTable.get(0)).width(533).height(300);
				uiTable.add(playerResultsTable.get(1)).width(534).height(300);
				uiTable.add(playerResultsTable.get(2)).width(533).height(300);
				break;
			case 4:
				uiTable.add(playerResultsTable.get(0)).width(400).height(300);
				uiTable.add(playerResultsTable.get(1)).width(400).height(300);
				uiTable.add(playerResultsTable.get(2)).width(400).height(300);
				uiTable.add(playerResultsTable.get(3)).width(400).height(300);
				break;
		}
		
		uiTable.row();
		
		uiTable.add(restartButton).colspan(4);
		uiTable.row();
		uiTable.add(mainMenuButton).colspan(4);
		uiTable.row(); 
		uiTable.add(quitButton).colspan(4);
		
		return uiTable;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		statsLabel.setText("Reached level " + playscreen.getLevelNum() + " in " + ((int) playscreen.getTimePlayed() / 60) + " minutes");
		for (PlayerResults playerResult : playerResultsTable) {
			playerResult.updateMenu();
		}
	}
}
