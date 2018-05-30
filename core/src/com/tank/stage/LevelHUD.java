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
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class LevelHUD extends Stage implements InputProcessor {
	protected TankInfinity game;
	private Label fpsLabel;
	private static float sinceChange;

	private Skin skin = Assets.manager.get(Assets.skin);

	public LevelHUD(TankInfinity game) {
		super(new ExtendViewport(Constants.PREFERRED_WINDOW_WIDTH, Constants.PREFERRED_WINDOW_HEIGHT));
		this.game = game;
		//cursors
		for (Player p : game.players) {
			if (p.isEnabled()) {
				p.initializeCursor();
				addActor(p.cursor);
				p.cursor.moveOnStageTo(p.tank.getX(), p.tank.getY());
			}
		}
		//ui table
		super.addActor(buildTable());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		//pause by key input, only from player 1
		if (game.players.get(0).controls.pausePressed()) {
			game.getScreen().pause();
		}
		
		// update fps counter
		sinceChange += delta; // add time since last act() to counter
		if (sinceChange >= 1.0f) { // after 1 second or more
			sinceChange = 0; // reset counter
			fpsLabel.setText(Gdx.graphics.getFramesPerSecond() + " FPS"); // update fps label
		}

		// update reload bars
		for (Player p : game.players) {
			if (p.isEnabled()) {
				p.hud.update();
			}
		}
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(200).height(75).pad(25).center();

		// Add widgets to the table here.
		fpsLabel = new Label("0 FPS", skin);
		TextButton pauseButton = new TextButton("Pause", skin);

		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().pause();
				event.stop();
			}
		});
		
		uiTable.add(fpsLabel).width(100).colspan(2).expand().top().left();
		uiTable.add(pauseButton).width(150).colspan(2).expand().top().right();
		uiTable.row();

		for (Player p : game.players) {
			uiTable.add(p.hud).expandX().bottom();
		}

		return uiTable;
	}
}