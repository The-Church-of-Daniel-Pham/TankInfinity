package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.NumberUtils;
import com.tank.actor.projectiles.ArtilleryShell;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtilleryMark extends Label {
	private static Skin skin = Assets.manager.get(Assets.skin);
	private static Texture cursorMark = Assets.manager.get(Assets.artilleryMark);
	private AbstractVehicle source;
	private Stats stats;
	private float countdown;
	
	public ArtilleryMark(AbstractVehicle src, Stats stats, float countdown, float x, float y) {
		super("" + ((int)countdown), skin);
		source = src;
		this.stats = stats;
		setX(x - 128);
		setY(y - 128);
		setWidth(256);
		setHeight(256);
		setColor(Color.BLACK);
		setAlignment(Align.center);
		this.countdown = countdown;
	}
	
	@Override
	public void act(float delta) {
		countdown -= delta;
		if (countdown < 0) {
			setText("");
			getStage().addActor(new ArtilleryShell(source, stats, getX() + 128, getY() + 128));
			remove();
		}
		else {
			setText("" + (((int)countdown) + 1));
		}
	}
	
	@Override
	public void draw(Batch batch, float a) {
		batch.setColor(NumberUtils.intToFloatColor(Color.toIntBits(255, 255, 255, (int)(255 * 0.7))));
		batch.draw(cursorMark, super.getX() - (cursorMark.getWidth() / 2) + 128, super.getY() - (cursorMark.getHeight() / 2) + 128,
				cursorMark.getWidth() / 2, cursorMark.getHeight() / 2, cursorMark.getWidth(), cursorMark.getHeight(),
				super.getScaleX(), super.getScaleY(), 0, 0, 0, cursorMark.getWidth(), cursorMark.getHeight(), false, false);
		batch.setColor(Color.WHITE);
		super.draw(batch, 0.7f);
	}
}
