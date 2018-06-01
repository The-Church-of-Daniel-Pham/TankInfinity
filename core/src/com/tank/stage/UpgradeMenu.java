package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.screen.PlayScreen;
import com.tank.utils.Assets;

public class UpgradeMenu   extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	protected int countEnabled;
	private Skin skin = Assets.manager.get(Assets.skin);

	public UpgradeMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	private void buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(300).height(100).space(25).center();
		Label title = new Label("Upgrades", skin);
		uiTable.add(title);
		// Add widgets to the table here.
		for (Player p : game.players) {
			p.initializeCustom();
			p.initializeCustomMenu();
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
				if (countEnabled > 0) {
					for (Player p : game.players) {
						// update player names based on text field
						p.setName(p.customMenu.getCustomName());
					}
					game.screens.put("Play", new PlayScreen(game)); // creates or replaces with a new game
					game.setScreen(game.screens.get("Play"));
					event.stop();
				}
			}
		});

		uiTable.row();
		uiTable.add(backButton).width(150).colspan(2).expand().bottom().left();
		uiTable.add(continueButton).colspan(2).expand().bottom().right();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		countEnabled = 0;
		for (Player p : game.players) {
			if (p.isEnabled()) {
				countEnabled++;
			}
		}
	}

}
