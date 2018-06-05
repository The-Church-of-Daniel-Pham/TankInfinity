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
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtilleryMark extends Label {
	private static Skin skin = Assets.manager.get(Assets.skin);
	private Texture tex;
	private AbstractVehicle source;
	private Stats stats;
	private float countdown;

	public ArtilleryMark(AbstractVehicle src, Stats stats, float countdown, float x, float y) {
		super("" + ((int) countdown), skin);
		source = src;
		this.stats = stats;
		if (src instanceof PlayerTank) {
			tex = ((PlayerTank) src).getPlayer().custom.getTexture("artillery");
			setColor(((PlayerTank) src).getPlayer().custom.getColor());
		} else {
			tex = Assets.manager.get(Assets.artillery_crosshairs_black);
			setColor(Color.BLACK);
		}
		setAlignment(Align.center);
		setX(x - tex.getWidth() / 2);
		setY(y - tex.getHeight() / 2);
		setWidth(tex.getWidth());
		setHeight(tex.getHeight());
		this.countdown = countdown;
	}

	@Override
	public void act(float delta) {
		countdown -= delta;
		if (countdown < 0) {
			setText("");
			getStage().addActor(
					new ArtilleryShell(source, stats, getX() + tex.getWidth() / 2, getY() + tex.getHeight() / 2));
			remove();
		} else {
			setText("" + (((int) countdown) + 1));
		}
	}

	@Override
	public void draw(Batch batch, float a) {
		batch.setColor(NumberUtils.intToFloatColor(Color.toIntBits(255, 255, 255, (int) (255 * 0.7))));
		batch.draw(tex, super.getX() - (tex.getWidth() / 2) + 128, super.getY() - (tex.getHeight() / 2) + 128,
				tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(), super.getScaleX(),
				super.getScaleY(), 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
		batch.setColor(Color.WHITE);
		super.draw(batch, 0.7f);
	}
}
