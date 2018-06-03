package com.tank.table;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tank.game.Player;
import com.tank.stats.Upgrade;
import com.tank.utils.Assets;

public class PlayerUpgradeMenu extends Table {
	protected final Player player;
	private Skin skin = Assets.manager.get(Assets.skin);
	private Texture empty = Assets.manager.get(Assets.vertex);
	private Image firstImage;
	private Image secondImage;
	private Image thirdImage;
	private Image fourthImage;

	public PlayerUpgradeMenu(final Player player) {
		this.player = player;

		super.setDebug(false);
		super.defaults().width(75).height(75).space(15).center();

		buildTable();
	}

	public void refreshMenu() {
		buildTable();
	}

	public boolean needsToRefresh() {
		if (player == null || !player.isEnabled() || player.tank == null || player.tank.isDestroyed())
			return false;
		// if player exp changes return true? or if selectableupgrades.size() changes?
		return true;
	}

	public void buildTable() {
		super.clearChildren();
		if (player == null || !player.isEnabled() || player.tank == null || player.tank.isDestroyed())
			return;
		super.setSkin(skin);
		add(new Label(player.getName(), skin)).colspan(3).width(250);
		row();
		ArrayList<Upgrade> u = player.tank.getSelectableUpgrades();

		add("").fill();
		if (u != null && u.size() > 0) {
			firstImage = new Image(u.get(0).getIcon());
		} else {
			firstImage = new Image(empty);
		}
		add(firstImage);
		add("").fill();
		row();
		if (u != null && u.size() > 1) {
			secondImage = new Image(u.get(1).getIcon());
		} else {
			secondImage = new Image(empty);
		}
		add(secondImage);
		add("").fill();
		if (u != null && u.size() > 2) {
			thirdImage = new Image(u.get(2).getIcon());
		} else {
			thirdImage = new Image(empty);
		}
		add(thirdImage);
		row();
		add("").fill();
		if (u != null && u.size() > 2) {
			fourthImage = new Image(u.get(3).getIcon());
		} else {
			fourthImage = new Image(empty);
		}
		add(fourthImage);
		add("").fill();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

}