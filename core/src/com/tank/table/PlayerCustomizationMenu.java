package com.tank.table;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
		super.defaults().width(300).height(100).space(25).center();
		
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
		
		final Image tankPreviewImage = new Image(player.custom.getTexture("preview"));
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
		
		super.add(playerLabel).colspan(3);
		super.row();
		super.add(leftButton).height(50).width(50);
		super.add(tankPreviewImage).width(200).height(200);
		super.add(rightButton).height(50).width(50);
		super.row();
		super.add(disableButton).colspan(3);
		changed = false;
	}
	
	public void disable() {
		super.clearChildren();
		
		Label placeholder = new Label("", skin);
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
		
		super.add(placeholder).colspan(3);
		super.row();
		super.add(placeholder).height(50).width(50);
		super.add(placeholder).width(200).height(200);
		super.add(placeholder).height(50).width(50);
		super.row();
		super.add(enableButton).colspan(3);
		changed = false;
	}
	
	public boolean hasChanged() {
		return changed;
	}
}
