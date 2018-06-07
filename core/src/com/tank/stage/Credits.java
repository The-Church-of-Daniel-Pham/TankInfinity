package com.tank.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.ui.Background;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class Credits extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	private Texture credits = Assets.manager.get(Assets.credits);
	private Background creditsBackground;
	private Skin skin = Assets.manager.get(Assets.skin);

	public Credits(TankInfinity game) {
		// super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
		this.game = game;
		creditsBackground = new Background(credits);
		creditsBackground.setFill(true);
		super.addActor(creditsBackground);
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	private void buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(250).height(100).space(25);

		// Add widgets to the table here.
		TextButton backButton = new TextButton("Back", skin);

		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.previousScreen);
				event.stop();
			}
		});

		uiTable.add(backButton).expand().top().left();
	}
}