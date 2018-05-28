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
		super.addActor(buildTable());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.
		
		uiTable.defaults().width(200).height(75).space(25).center();
		// Add widgets to the table here.
		for (final Player p : game.players) {
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
					treadColorValueLabel.setText(p.tank.getCustom("gun"));
					event.stop();
				}
			});

			leftGunButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					p.tank.cycleCustom("gun", -1);
					treadColorValueLabel.setText(p.tank.getCustom("gun"));
					event.stop();
				}
			});
			
			uiTable.add(playerLabel).colspan(4);
			uiTable.row();
			uiTable.add(treadColorLabel).width(200);
			uiTable.add(leftTreadButton).width(50).spaceRight(0);
			uiTable.add(treadColorValueLabel).width(200).spaceLeft(0).spaceRight(0);
			uiTable.add(rightTreadButton).width(50).spaceLeft(0);
			uiTable.row();
			uiTable.add(gunColorLabel).width(200);
			uiTable.add(leftGunButton).width(50).spaceRight(0);
			uiTable.add(gunColorValueLabel).width(200).spaceLeft(0).spaceRight(0);
			uiTable.add(rightGunButton).width(50).spaceLeft(0);
			uiTable.row();
		}
		
		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 game.setScreen(game.previousScreen);
	        	 event.stop();
	         }
	      });
		uiTable.add(backButton).width(150).colspan(4);

		return uiTable;
	}
}
