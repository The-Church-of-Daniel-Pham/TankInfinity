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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class SettingsMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	private Skin skin = Assets.manager.get(Assets.skin);

	public SettingsMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	private Table buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(400).height(100).space(25).center();

		// Add widgets to the table here.
		Label windowLabel = new Label("Window Mode ", skin);
		windowLabel.setAlignment(Align.right);
		final Label windowValueLabel = new Label((String) Constants.WINDOW_MODES.getCurrent(), skin);
		windowValueLabel.setAlignment(Align.center);
		final TextButton forwardWindowButton = new TextButton(">", skin);
		final TextButton backwardWindowButton = new TextButton("<", skin);	
		
		Label resolutionLabel = new Label("Resolution ", skin);
		resolutionLabel.setAlignment(Align.right);
		final Label resolutionValueLabel = new Label(
				(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext(), skin);
		resolutionValueLabel.setAlignment(Align.center);
		final TextButton plusResolutionButton = new TextButton("+", skin);
		final TextButton minusResolutionButton = new TextButton("-", skin);
		
		Label vsyncLabel = new Label("Vertical Sync ", skin);
		vsyncLabel.setAlignment(Align.right);
		final Label vsyncValueLabel = new Label((String) Constants.VSYNC.getCurrent(), skin);
		vsyncValueLabel.setAlignment(Align.center);
		final TextButton forwardVsyncButton = new TextButton(">", skin);
		final TextButton backwardVsyncButton = new TextButton("<", skin);
		
		Label fpsLabel = new Label("FPS Counter ", skin);
		fpsLabel.setAlignment(Align.right);
		final Label fpsValueLabel = new Label((String) Constants.FPS_COUNTER.getCurrent(), skin);
		fpsValueLabel.setAlignment(Align.center);
		final TextButton forwardFpsButton = new TextButton(">", skin);
		final TextButton backwardFpsButton = new TextButton("<", skin);
		
		TextButton applyButton = new TextButton("Apply", skin);
		TextButton backButton = new TextButton("Back", skin);

		forwardWindowButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.WINDOW_MODES.cycleBy(1);
				windowValueLabel.setText((String) Constants.WINDOW_MODES.getCurrent());
				event.stop();
			}
		});

		backwardWindowButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.WINDOW_MODES.cycleBy(-1);
				windowValueLabel.setText((String) Constants.WINDOW_MODES.getCurrent());
				event.stop();
			}
		});

		plusResolutionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.RESOLUTIONS.cycleBy(2);
				resolutionValueLabel.setText(
						(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext());
				event.stop();
			}
		});

		minusResolutionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.RESOLUTIONS.cycleBy(-2);
				resolutionValueLabel.setText(
						(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext());
				event.stop();
			}
		});
		
		forwardVsyncButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.VSYNC.cycleBy(1);
				vsyncValueLabel.setText((String) Constants.VSYNC.getCurrent());
				event.stop();
			}
		});

		backwardVsyncButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.VSYNC.cycleBy(-1);
				vsyncValueLabel.setText((String) Constants.VSYNC.getCurrent());
				event.stop();
			}
		});
		
		forwardFpsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.FPS_COUNTER.cycleBy(1);
				fpsValueLabel.setText((String) Constants.FPS_COUNTER.getCurrent());
				event.stop();
			}
		});

		backwardFpsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.FPS_COUNTER.cycleBy(-1);
				fpsValueLabel.setText((String) Constants.FPS_COUNTER.getCurrent());
				event.stop();
			}
		});

		applyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.updateVideo();
				// update resolution text in the case from switching from windowed to fullscreen
				resolutionValueLabel.setText(
						(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext());
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

		uiTable.add(windowLabel).right();
		uiTable.add(backwardWindowButton).width(50).height(50).spaceRight(0);
		uiTable.add(windowValueLabel).spaceLeft(0).spaceRight(0);
		uiTable.add(forwardWindowButton).width(50).height(50).spaceLeft(0);
		uiTable.row();
		uiTable.add(resolutionLabel).right();
		uiTable.add(minusResolutionButton).width(50).height(50).spaceRight(0);
		uiTable.add(resolutionValueLabel).spaceLeft(0).spaceRight(0);
		uiTable.add(plusResolutionButton).width(50).height(50).spaceLeft(0);
		uiTable.row();
		uiTable.add(vsyncLabel).right();
		uiTable.add(backwardVsyncButton).width(50).height(50).spaceRight(0);
		uiTable.add(vsyncValueLabel).spaceLeft(0).spaceRight(0);
		uiTable.add(forwardVsyncButton).width(50).height(50).spaceLeft(0);
		uiTable.row();
		uiTable.add(fpsLabel).right();
		uiTable.add(backwardFpsButton).width(50).height(50).spaceRight(0);
		uiTable.add(fpsValueLabel).spaceLeft(0).spaceRight(0);
		uiTable.add(forwardFpsButton).width(50).height(50).spaceLeft(0);
		uiTable.row();
		uiTable.add(applyButton).width(150).colspan(4);
		uiTable.row();
		uiTable.add(backButton).width(150).colspan(4);

		return uiTable;
	}
}