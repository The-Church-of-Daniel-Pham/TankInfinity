package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
	private Skin skin = Assets.manager.get(Assets.skin);

	public SettingsMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		super.addActor(buildTable());
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(200).height(75).space(25).center();

		// Add widgets to the table here.
		Label windowLabel = new Label("Window Mode ", skin);
		windowLabel.setAlignment(Align.right);
		final TextButton windowModeButton = new TextButton((String) Constants.WINDOW_MODES.getCurrent(), skin);
		Label resolutionLabel = new Label("Resolution ", skin);
		resolutionLabel.setAlignment(Align.right);
		final Label resolutionValueLabel = new Label(
				(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext(), skin);
		resolutionValueLabel.setAlignment(Align.center);
		final TextButton plusButton = new TextButton("+", skin);
		final TextButton minusButton = new TextButton("-", skin);
		Label vsyncLabel = new Label("Vertical Sync ", skin);
		vsyncLabel.setAlignment(Align.right);
		final CheckBox vsyncButton = new CheckBox("", skin);
		vsyncButton.setChecked(Constants.VSYNC_ENABLED);
		TextButton applyButton = new TextButton("Apply", skin);
		TextButton backButton = new TextButton("Back", skin);

		windowModeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.WINDOW_MODES.cycleBy(1);
				windowModeButton.setText((String) Constants.WINDOW_MODES.getCurrent());
				event.stop();
			}
		});

		plusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.RESOLUTIONS.cycleBy(2);
				resolutionValueLabel.setText(
						(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext());
				event.stop();
			}
		});

		minusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.RESOLUTIONS.cycleBy(-2);
				resolutionValueLabel.setText(
						(Integer) Constants.RESOLUTIONS.getCurrent() + " x " + (Integer) Constants.RESOLUTIONS.getNext());
				event.stop();
			}
		});

		vsyncButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.toggleVsync(vsyncButton.isChecked());
				event.stop();
			}
		});

		applyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Constants.updateWindow();
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
		uiTable.add(windowModeButton).width(300).colspan(3).left();
		uiTable.row();
		uiTable.add(resolutionLabel).right();
		uiTable.add(minusButton).width(50).spaceRight(0);
		uiTable.add(resolutionValueLabel).spaceLeft(0).spaceRight(0);
		uiTable.add(plusButton).width(50).spaceLeft(0);
		uiTable.row();
		uiTable.add(vsyncLabel).right();
		uiTable.add(vsyncButton).width(50).colspan(3).left();
		uiTable.row();
		uiTable.add(applyButton).width(150).colspan(4);
		uiTable.row();
		uiTable.add(backButton).width(150).colspan(4);

		return uiTable;
	}
}