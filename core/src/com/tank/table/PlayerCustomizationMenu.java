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
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public PlayerCustomizationMenu(final Player player) {
		this.player = player;
		
		super.setDebug(false);
		super.defaults().width(200).height(75).space(25).center();
		
		Label playerLabel = new Label(player.getName(), skin);
		playerLabel.setAlignment(Align.center);
		
		player.tank.setCustom("tread", player.getDefaultColor());
		Label treadColorLabel = new Label("Tread Color ", skin);
		treadColorLabel.setAlignment(Align.right);
		final Label treadColorValueLabel = new Label(player.tank.getCustom("tread"), skin);
		treadColorValueLabel.setAlignment(Align.center);
		final TextButton rightTreadButton = new TextButton(">", skin);
		final TextButton leftTreadButton = new TextButton("<", skin);
		
		player.tank.setCustom("gun", player.getDefaultColor());
		Label gunColorLabel = new Label("Gun Color ", skin);
		gunColorLabel.setAlignment(Align.right);
		final Label gunColorValueLabel = new Label(player.tank.getCustom("gun"), skin);
		gunColorValueLabel.setAlignment(Align.center);
		final TextButton rightGunButton = new TextButton(">", skin);
		final TextButton leftGunButton = new TextButton("<", skin);
		
		rightTreadButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.tank.cycleCustom("tread", 1);
				treadColorValueLabel.setText(player.tank.getCustom("tread"));
				event.stop();
			}
		});

		leftTreadButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.tank.cycleCustom("tread", -1);
				treadColorValueLabel.setText(player.tank.getCustom("tread"));
				event.stop();
			}
		});
		
		rightGunButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.tank.cycleCustom("gun", 1);
				gunColorValueLabel.setText(player.tank.getCustom("gun"));
				event.stop();
			}
		});

		leftGunButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.tank.cycleCustom("gun", -1);
				gunColorValueLabel.setText(player.tank.getCustom("gun"));
				event.stop();
			}
		});
		
		super.add(playerLabel).colspan(4);
		super.row();
		super.add(treadColorLabel).width(150);
		super.add(leftTreadButton).width(50).spaceRight(0);
		super.add(treadColorValueLabel).width(100).spaceLeft(0).spaceRight(0);
		super.add(rightTreadButton).width(50).spaceLeft(0);
		super.row();
		super.add(gunColorLabel).width(150);
		super.add(leftGunButton).width(50).spaceRight(0);
		super.add(gunColorValueLabel).width(100).spaceLeft(0).spaceRight(0);
		super.add(rightGunButton).width(50).spaceLeft(0);
		
	}
}
