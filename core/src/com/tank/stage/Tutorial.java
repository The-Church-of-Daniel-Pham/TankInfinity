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
import com.tank.utils.CycleList;

public class Tutorial extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	protected static CycleList<Texture> slides;
	private Background tutorial;
	private Skin skin = Assets.manager.get(Assets.skin);

	public Tutorial(TankInfinity game) {
		// super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
		this.game = game;
		slides = new CycleList<Texture>(new Texture[] { Assets.manager.get(Assets.tutorial_1), Assets.manager.get(Assets.tutorial_2),
				Assets.manager.get(Assets.tutorial_3), Assets.manager.get(Assets.tutorial_4), Assets.manager.get(Assets.tutorial_5),
				Assets.manager.get(Assets.tutorial_6), Assets.manager.get(Assets.tutorial_7), Assets.manager.get(Assets.tutorial_8),
				Assets.manager.get(Assets.tutorial_9), Assets.manager.get(Assets.tutorial_10), Assets.manager.get(Assets.tutorial_11) }, 0, true);
		tutorial = new Background(slides.getCurrent());
		tutorial.setFill(true);
		super.addActor(tutorial);
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	private void buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(250).height(100).space(25);

		// Add widgets to the table here.
		TextButton nextButton = new TextButton("Next", skin, "defaultLight");
		TextButton previousButton = new TextButton("Previous", skin, "defaultLight");
		TextButton backButton = new TextButton("Back", skin, "defaultLight");

		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				slides.cycleBy(1);
				tutorial.setTexture(slides.getCurrent());
				event.stop();
			}
		});

		previousButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				slides.cycleBy(-1);
				tutorial.setTexture(slides.getCurrent());
				event.stop();
			}
		});

		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.previousScreen);
				event.stop();
			}
		});

		uiTable.add(backButton).colspan(2).expand().top().left();
		uiTable.row();
		uiTable.add(previousButton).expandX().bottom().left();
		uiTable.add(nextButton).expandX().bottom().right();
	}
	
	public void goToFirstSlide() {
		slides.setIndex(0);
		tutorial.setTexture(slides.getCurrent());
	}
}