package com.tank.table;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tank.game.Player;
import com.tank.subweapons.SubWeapon;
import com.tank.utils.Assets;

public class PlayerHUD extends Table{
	protected Player player;
	
	protected Label nameLabel;
	protected Image iconImage;
	protected Group icon;
	
	protected ProgressBar reloadBar;
	
	protected ProgressBar healthBar;
	protected Label healthLabel;
	protected Group health;
	
	protected ProgressBar expBar;
	protected Label expLabel;
	protected Group exp;
	
	protected Table infoTable;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	
	private final Image leftSubImage;
	private Label leftSubAmmo;
	private Group leftSub;
	
	private final Image centerSubImage;
	private Label centerSubAmmo;
	private Group centerSub;
	
	private final Image rightSubImage;
	private Label rightSubAmmo;
	private Group rightSub;
	
	private SubWeapon lastCenterSubWeapon;
	private int lastCenterAmmo;
	
	public PlayerHUD(Player player) {
		this.player = player;
		
		super.setFillParent(false);
		super.setDebug(false);
		super.defaults().pad(10).left();
		
		iconImage = new Image(player.custom.getTexture("preview"));
		iconImage.setSize(140, 140);
		nameLabel = new Label(player.getName(), skin, "medium");
		nameLabel.setPosition(2, 2);
		icon = new Group();
		icon.addActor(iconImage);
		icon.addActor(nameLabel);	
		
		leftSubImage = new Image(skin.getDrawable("round-light-gray"));
		leftSubAmmo = new Label("", skin, "small");
		leftSubAmmo.setPosition(38, 2, Align.bottomRight);
		leftSubAmmo.setFontScale(0.6f);
		leftSub = new Group();
		leftSub.addActor(leftSubImage);
		leftSub.addActor(leftSubAmmo);
		
		centerSubImage = new Image(skin.getDrawable("round-light-gray"));
		centerSubAmmo = new Label("", skin, "small");
		centerSubAmmo.setPosition(38, 2, Align.bottomRight);
		centerSubAmmo.setFontScale(0.6f);
		centerSub = new Group();
		centerSub.addActor(centerSubImage);
		centerSub.addActor(centerSubAmmo);
		
		rightSubImage = new Image(skin.getDrawable("round-light-gray"));
		rightSubAmmo = new Label("", skin, "small");
		rightSubAmmo.setPosition(38, 2, Align.bottomRight);
		rightSubAmmo.setFontScale(0.6f);
		rightSub = new Group();
		rightSub.addActor(rightSubImage);
		rightSub.addActor(rightSubAmmo);
		
		reloadBar = new ProgressBar(0.0f, 1.0f, 0.01f, true, skin, "yellow-vertical");
		
		healthBar = new ProgressBar(0.0f, player.tank.getMaxHealth(), 0.01f, false, skin, "green-horizontal");
		healthLabel = new Label("0 HP", skin, "small");
		healthLabel.setPosition(2, 2);
		health = new Group();
		health.addActor(healthBar);
		health.addActor(healthLabel);
		
		expBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin, "blue-horizontal");
		expLabel = new Label("0 EXP", skin, "small");
		expLabel.setPosition(2, 2);
		exp = new Group();
		exp.addActor(expBar);
		exp.addActor(expLabel);
		
		reloadBar.getStyle().background.setMinWidth(40);
		reloadBar.getStyle().knobBefore.setMinWidth(30);
		healthBar.getStyle().background.setMinHeight(40);
		healthBar.getStyle().knobBefore.setMinHeight(30);
		expBar.getStyle().background.setMinHeight(40);
		expBar.getStyle().knobBefore.setMinHeight(30);

		// since there's no colspan
		infoTable = new Table();
		infoTable.setDebug(false);	
		infoTable.defaults().space(10).left();
		
		infoTable.add(health).width(120).height(40).colspan(3);
		infoTable.row();
		infoTable.add(exp).width(120).height(40).colspan(3);
		infoTable.row();
		infoTable.add(leftSub).width(40).height(40).center();
		infoTable.add(centerSub).width(40).height(40).center();
		infoTable.add(rightSub).width(40).height(40).center();
		
		super.add(icon).width(140).height(140);
		super.add(infoTable);
		super.add(reloadBar).width(40).height(140);
		
		super.setBackground(skin.newDrawable("list", 1.0f, 1.0f, 1.0f, 0.75f));
	}
	
	public void update() {
		if (subChanged()) {
			if (player.tank.getCurrentSubWeapon() != null) {
				centerSubImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.tank.getCurrentSubWeapon().getTexture())));
				centerSubAmmo.setText("" + player.tank.getCurrentSubWeapon().getAmmo());
				rightSubImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.tank.getPrevSubWeapon().getTexture())));
				rightSubAmmo.setText("" + player.tank.getPrevSubWeapon().getAmmo());
				leftSubImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.tank.getNextSubWeapon().getTexture())));
				leftSubAmmo.setText("" + player.tank.getNextSubWeapon().getAmmo());
			}
			else {
				centerSubImage.setDrawable(skin.getDrawable("round-light-gray"));
				centerSubAmmo.setText("");
				leftSubImage.setDrawable(skin.getDrawable("round-light-gray"));
				leftSubAmmo.setText("");
				rightSubImage.setDrawable(skin.getDrawable("round-light-gray"));
				rightSubAmmo.setText("");
			}
			lastCenterSubWeapon = player.tank.getCurrentSubWeapon();
			if (player.tank.getCurrentSubWeapon() != null)
				lastCenterAmmo = player.tank.getCurrentSubWeapon().getAmmo();
		}
		float completion = player.tank.getReloadTime() / player.tank.getLastReloadTime();;
		// reload time out of max reload time (inverse of rate of fire)
		//System.out.println(completion);
		reloadBar.setValue(completion);
		healthBar.setValue(player.tank.getHealth());
		healthLabel.setText(player.tank.getHealth() + " HP");
		expBar.setValue(((float) player.tank.getCurrentExp()) / player.tank.getNextExp());
		expLabel.setText(player.tank.getCurrentExp() + " EXP");
		
	}
	
	public boolean subChanged() {
		if (player.tank.getCurrentSubWeapon() == null && lastCenterSubWeapon == null) return false;
		if (player.tank.getCurrentSubWeapon() != null && lastCenterSubWeapon == null) return true;
		if (player.tank.getCurrentSubWeapon() == null && lastCenterSubWeapon != null) return true;
		if (!player.tank.getCurrentSubWeapon().equals(lastCenterSubWeapon)) return true;
		if (lastCenterAmmo != player.tank.getCurrentSubWeapon().getAmmo()) return true;
		return false;
	}
}
