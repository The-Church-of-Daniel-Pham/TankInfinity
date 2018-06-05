package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.tank.table.LevelInfo;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class LevelHUD extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected PlayScreen playscreen;
	protected Table uiTable;
	private Label fpsLabel;
	private static float sinceChange;

	private Skin skin = Assets.manager.get(Assets.skin);

	public LevelHUD(TankInfinity game, PlayScreen playscreen) {
		super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
		this.game = game;
		this.playscreen = playscreen;
		// cursors
		for (Player p : game.players) {
			if (p.isEnabled()) {
				p.initializeCursor();
				addActor(p.cursor);
			}
		}
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		// update fps counter, if on
		if (Constants.FPS_COUNTER.getCurrent().equals("On")) {
			sinceChange += delta; // add time since last act() to counter
			if (sinceChange >= 1.0f) { // after 1 second or more
				sinceChange = 0; // reset counter
				fpsLabel.setText(Gdx.graphics.getFramesPerSecond() + " FPS"); // update fps label
			}
		} else {
			fpsLabel.setText("");
		}

		// update reload bars
		for (Player p : game.players) {
			if (p.isEnabled()) {
				p.hud.update(delta);
			}
		}
	}

	private void buildTable() {
		uiTable.clearChildren();

		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().pad(25);

		// Add widgets to the table here.
		fpsLabel = new Label("0 FPS", skin);
		LevelInfo levelinfo = new LevelInfo(playscreen);
		TextButton pauseButton = new TextButton("Pause", skin);
		Table placeholder = new Table();

		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().pause();
				event.stop();
			}
		});

		uiTable.add(fpsLabel).width(200).height(100).expand().top().left();
		uiTable.add(levelinfo).colspan(2).expand().top();
		uiTable.add(pauseButton).width(200).height(100).expand().top().right();
		uiTable.row();

		for (Player p : game.players) {
			if (p.isEnabled()) {
				uiTable.add(p.hud).width(400).bottom();
			} else {
				// placeholder scaled to fraction of the width of the entire table width
				uiTable.add(placeholder).width(400).bottom();
			}
		}
	}
}