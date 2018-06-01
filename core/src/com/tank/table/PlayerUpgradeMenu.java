package com.tank.table;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tank.game.Player;
import com.tank.utils.Assets;

public class PlayerUpgradeMenu extends Table{
	protected final Player player;
	protected Label playerNameLabel;
	protected boolean changed = false;
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public PlayerUpgradeMenu(final Player player) {
		this.player = player;
		
		super.setDebug(false);
		super.defaults().width(300).height(100).space(25).center();
		
		buildTable();
	}
	
	public void buildTable() {
		super.clearChildren();
		
		playerNameLabel = new Label(player.getName(), skin);
		final Image upgrade = new Image(player.custom.getTexture("preview"));
		final TextButton rightButton = new TextButton(">", skin);
		final TextButton leftButton = new TextButton("<", skin);
		final TextButton disableButton = new TextButton("Disable", skin);
		
		rightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.custom.cycleCustom("tank color", 1);
				tankPreviewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.custom.getTexture("preview"))));
				event.stop();
			}
		});

		leftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.custom.cycleCustom("tank color", -1);
				tankPreviewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.custom.getTexture("preview"))));
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
		
		super.add(playerNameLabel).colspan(3);
		super.row();
		super.add(leftButton).height(50).width(50);
		super.add(tankPreviewImage).width(200).height(200);
		super.add(rightButton).height(50).width(50);
		super.row();
		super.add(disableButton).colspan(3);
		changed = false;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public String getCustomName() {
		if (player.isEnabled()) {
			// if player is enabled, set new name
			return playerNameLabel.getText();
		}
		else {
			// otherwise set as same as old
			return player.getName();
		}
	}
}