package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.tank.game.Player;
import com.tank.utils.Assets;

public class PlayerCustomizationMenu  extends Table{
	protected final Player player;
	protected boolean changed = false;
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public PlayerCustomizationMenu(final Player player, boolean initial) {
		this.player = player;
		
		super.setDebug(false);
		super.defaults().width(200).height(75).space(25).center();
		
		if (initial) {
			enable();
		}
		else {
			disable();
		}
	}
	
	public void enable() {
		super.clearChildren();
		
		Label playerLabel = new Label(player.getName(), skin);
		playerLabel.setAlignment(Align.center);

		Label colorLabel = new Label("Color ", skin);
		colorLabel.setAlignment(Align.right);
		final Label colorValueLabel = new Label(player.custom.getCustomValue("tank color"), skin);
		colorValueLabel.setAlignment(Align.center);
		final TextButton rightButton = new TextButton(">", skin);
		final TextButton leftButton = new TextButton("<", skin);
		final TextButton disableButton = new TextButton("Disable", skin);
		
		rightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.custom.cycleCustom("tank color", 1);
				colorValueLabel.setText(player.custom.getCustomValue("tank color"));
				event.stop();
			}
		});

		leftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.custom.cycleCustom("tank color", -1);
				colorValueLabel.setText(player.custom.getCustomValue("tank color"));
				event.stop();
			}
		});
		
		disableButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changed = true;
				player.enable(false);
				disable();
				event.stop();
			}
		});
		
		super.add(playerLabel).colspan(4);
		super.row();
		super.add(colorLabel).width(150);
		super.add(leftButton).width(50).spaceRight(0);
		super.add(colorValueLabel).width(100).spaceLeft(0).spaceRight(0);
		super.add(rightButton).width(50).spaceLeft(0);
		super.row();
		super.add(disableButton);
		changed = false;
	}
	
	public void disable() {
		super.clearChildren();
		
		final TextButton enableButton = new TextButton("Enable", skin);
		
		enableButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changed = true;
				player.enable(true);
				enable();
				event.stop();
			}
		});
		
		super.add(enableButton);
		changed = false;
	}
	
	public boolean hasChanged() {
		return changed;
	}
}
