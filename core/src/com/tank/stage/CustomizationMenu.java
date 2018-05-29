package com.tank.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.projectiles.AbstractProjectile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class CustomizationMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	private Skin skin = Assets.manager.get(Assets.skin);

	public CustomizationMenu(TankInfinity game) {
		super(new ExtendViewport(Constants.PREFERRED_WINDOW_WIDTH, Constants.PREFERRED_WINDOW_HEIGHT));
		this.game = game;
		AbstractVehicle.vehicleList.clear();
		AbstractProjectile.projectileList.clear();
		// create players
		this.game.players.add(new Player("Player 1", 1, "red"));
		this.game.players.add(new Player("Player 2", 2, "blue"));
		this.game.players.add(new Player("Player 3", 3, "green"));
		this.game.players.add(new Player("Player 4", 4, "yellow"));
		super.addActor(buildTable());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(200).height(75).space(25).center();
		
		// Add widgets to the table here.
		for (final Player p : game.players) {
			uiTable.add(p.customMenu).expand();
		}
		
		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(game.previousScreen);
	        	 event.stop();
	         }
	      });
		
		TextButton continueButton = new TextButton("Continue", skin);
		continueButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(game.screens.get("Play"));
	        	 event.stop();
	         }
	      });
		
		uiTable.row();
		uiTable.add(backButton).width(150).colspan(2).expand().bottom().left();
		uiTable.add(continueButton).colspan(2).expand().bottom().right();

		return uiTable;
	}
}
