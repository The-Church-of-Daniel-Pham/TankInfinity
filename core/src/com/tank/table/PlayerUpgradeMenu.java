package com.tank.table;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tank.game.Player;
import com.tank.stats.Upgrade;
import com.tank.utils.Assets;

public class PlayerUpgradeMenu extends Table {
	protected final Player player;
	private static Skin skin = Assets.manager.get(Assets.skin);
	private static Texture empty = Assets.manager.get(Assets.vertex);
	private Label playerName;
	private Group topImage;
	private Image topIcon;
	private Image topBack;
	
	private Group leftImage;
	private Image leftIcon;
	private Image leftBack;
	
	private Group rightImage;
	private Image rightIcon;
	private Image rightBack;
	
	private Group bottomImage;
	private Image bottomIcon;
	private Image bottomBack;
	
	private Label description;
	
	private int selected;
	private ArrayList<Boolean> heldButtons;

	public PlayerUpgradeMenu(final Player player) {
		this.player = player;
		
		selected = -1;

		super.setDebug(false);
		super.defaults().width(75).height(75).space(15).center();
		
		heldButtons = new ArrayList<Boolean>();
		for (int i = 0; i < 4; i++) {
			heldButtons.add(false);
		}

		buildTable();
	}

	public void refreshMenu() {
		ArrayList<Upgrade> u = player.tank.getSelectableUpgrades();
		if (u != null && u.size() > 0) {
			topIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(u.get(0).getIcon())));
		} else {
			topIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(empty)));
		}
		if (selected == 0) {
			topBack.setDrawable(skin, "round-light-gray");
			description.setText(u.get(0).getName() + "\n" + u.get(0).getDescription());
		}
		else topBack.setDrawable(skin, "round-dark-gray");
		
		if (u != null && u.size() > 1) {
			leftIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(u.get(1).getIcon())));
		} else {
			leftIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(empty)));
		}
		if (selected == 1) {
			leftBack.setDrawable(skin, "round-light-gray");
			description.setText(u.get(1).getName() + "\n" + u.get(1).getDescription());
		}
		else leftBack.setDrawable(skin, "round-dark-gray");
		
		if (u != null && u.size() > 2) {
			rightIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(u.get(2).getIcon())));
		} else {
			rightIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(empty)));
		}
		if (selected == 2) {
			rightBack.setDrawable(skin, "round-light-gray");
			description.setText(u.get(2).getName() + "\n" + u.get(2).getDescription());
		}
		else rightBack.setDrawable(skin, "round-dark-gray");
		
		if (u != null && u.size() > 3) {
			bottomIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(u.get(3).getIcon())));
		} else {
			bottomIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(empty)));
		}
		if (selected == 3) {
			bottomBack.setDrawable(skin, "round-light-gray");
			description.setText(u.get(3).getName() + "\n" + u.get(3).getDescription());
		}
		else bottomBack.setDrawable(skin, "round-dark-gray");
		
		if (selected == -1) description.setText("");
	}
	
	public void select(int index) {
		selected = index;
	}

	public boolean needsToRefresh() {
		if (player == null || !player.isEnabled() || player.tank == null || player.tank.isDestroyed())
			return false;
		// if player exp changes return true? or if selectableupgrades.size() changes?
		return true;
	}

	public void buildTable() {
		//super.clearChildren();
		//if (player == null || !player.isEnabled() || player.tank == null || player.tank.isDestroyed())
		//	return;
		super.setSkin(skin);
		playerName = new Label(player.getName(), skin);
		add(playerName).colspan(3).width(250);
		row();

		add("").fill();
		
		topIcon = new Image(empty);
		topIcon.setSize(75, 75);
		topBack = new Image(skin.getDrawable("round-dark-gray"));
		topBack.setSize(75, 75);
		topImage = new Group();
		topImage.addActor(topBack);
		topImage.addActor(topIcon);
		topImage.setSize(75, 75);
		add(topImage).fill();
		
		add("").fill();
		row();
		
		leftIcon = new Image(empty);
		leftIcon.setSize(75, 75);
		leftBack = new Image(skin.getDrawable("round-dark-gray"));
		leftBack.setSize(75, 75);
		leftImage = new Group();
		leftImage.addActor(leftBack);
		leftImage.addActor(leftIcon);
		leftImage.setSize(75, 75);
		add(leftImage).fill();
		
		add("").fill();
		
		rightIcon = new Image(empty);
		rightIcon.setSize(75, 75);
		rightBack = new Image(skin.getDrawable("round-dark-gray"));
		rightBack.setSize(75, 75);
		rightImage = new Group();
		rightImage.addActor(rightBack);
		rightImage.addActor(rightIcon);
		rightImage.setSize(75, 75);
		add(rightImage);
		
		row();
		add("").fill();
		
		bottomIcon = new Image(empty);
		bottomIcon.setSize(75, 75);
		bottomBack = new Image(skin.getDrawable("round-dark-gray"));
		bottomBack.setSize(75, 75);
		bottomImage = new Group();
		bottomImage.addActor(bottomBack);
		bottomImage.addActor(bottomIcon);
		bottomImage.setSize(75, 75);
		add(bottomImage).fill();
		
		add("").fill();
		row();
		
		description = new Label("", skin);
		description.setFontScale(0.5f);
		description.setPosition(-100, 0, Align.left);
		Group descLabel = new Group();
		descLabel.addActor(description);
		add(descLabel).colspan(3).width(300);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (player.controls.upPressed() && !heldButtons.get(0)) {
			select(0);
			refreshMenu();
		}
		if (player.controls.leftPressed() && !heldButtons.get(1)) {
			select(1);
			refreshMenu();
		}
		if (player.controls.rightPressed() && !heldButtons.get(2)) {
			select(2);
			refreshMenu();
		}
		if (player.controls.downPressed() && !heldButtons.get(3)) {
			select(3);
			refreshMenu();
		}
		
		heldButtons.set(0, player.controls.upPressed());
		heldButtons.set(1, player.controls.leftPressed());
		heldButtons.set(2, player.controls.rightPressed());
		heldButtons.set(3, player.controls.downPressed());
	}

}