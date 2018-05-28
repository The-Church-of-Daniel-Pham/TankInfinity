package com.tank.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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
		// create players
		this.game.players.add(new Player("Player 1"));
		this.game.players.add(new Player("Player 2"));
		this.game.players.add(new Player("Player 3"));
		this.game.players.add(new Player("Player 4"));
		super.addActor(buildTable());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(200).height(75).space(25).center();
		
		// Add widgets to the table here.
		for (final Player p : game.players) {
			Table playerTable = new Table();
			playerTable.setDebug(false);
			playerTable.defaults().width(200).height(75).space(25).center();
			
			Label playerLabel = new Label(p.getName(), skin);
			playerLabel.setAlignment(Align.center);
			Label treadColorLabel = new Label("Tread Color ", skin);
			treadColorLabel.setAlignment(Align.right);
			final Label treadColorValueLabel = new Label(p.tank.getCustom("tread"), skin);
			treadColorValueLabel.setAlignment(Align.center);
			final TextButton rightTreadButton = new TextButton(">", skin);
			final TextButton leftTreadButton = new TextButton("<", skin);
			Label gunColorLabel = new Label("Gun Color ", skin);
			gunColorLabel.setAlignment(Align.right);
			final Label gunColorValueLabel = new Label(p.tank.getCustom("gun"), skin);
			gunColorValueLabel.setAlignment(Align.center);
			final TextButton rightGunButton = new TextButton(">", skin);
			final TextButton leftGunButton = new TextButton("<", skin);
			
			rightTreadButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					p.tank.cycleCustom("tread", 1);
					treadColorValueLabel.setText(p.tank.getCustom("tread"));
					System.out.println(p.tank.getCustom("tread"));
					event.stop();
				}
			});

			leftTreadButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					p.tank.cycleCustom("tread", -1);
					treadColorValueLabel.setText(p.tank.getCustom("tread"));
					event.stop();
				}
			});
			
			rightGunButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					p.tank.cycleCustom("gun", 1);
					gunColorValueLabel.setText(p.tank.getCustom("gun"));
					event.stop();
				}
			});

			leftGunButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					p.tank.cycleCustom("gun", -1);
					gunColorValueLabel.setText(p.tank.getCustom("gun"));
					event.stop();
				}
			});
			
			playerTable.add(playerLabel).colspan(4);
			playerTable.row();
			playerTable.add(treadColorLabel).width(150);
			playerTable.add(leftTreadButton).width(50).spaceRight(0);
			playerTable.add(treadColorValueLabel).width(100).spaceLeft(0).spaceRight(0);
			playerTable.add(rightTreadButton).width(50).spaceLeft(0);
			playerTable.row();
			playerTable.add(gunColorLabel).width(150);
			playerTable.add(leftGunButton).width(50).spaceRight(0);
			playerTable.add(gunColorValueLabel).width(100).spaceLeft(0).spaceRight(0);
			playerTable.add(rightGunButton).width(50).spaceLeft(0);
			
			uiTable.add(playerTable).expand();
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
	        	 initiliazeTankPositions();
	        	 game.setScreen(game.screens.get("Play"));
	        	 event.stop();
	         }
	      });
		
		uiTable.row();
		uiTable.add(backButton).width(150).colspan(2).expand().bottom().left();
		uiTable.add(continueButton).colspan(2).expand().bottom().right();

		return uiTable;
	}
	
	private void initiliazeTankPositions() {
		int space = 0;
		for (Player p : game.players) {
			p.tank.setPosition(128 + space, 128);
			space += 256;
		}
	}
}
