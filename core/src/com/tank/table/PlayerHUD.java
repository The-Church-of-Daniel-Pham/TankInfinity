package com.tank.table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tank.game.Player;
import com.tank.subweapons.SubWeapon;
import com.tank.utils.Assets;

public class PlayerHUD extends Table{
	protected Player player;
	
	protected Label nameLabel;
	protected Image iconImage;
	protected Label levelLabel;
	protected Group icon;
	
	protected ProgressBar reloadBar;
	
	protected ProgressBar healthBar;
	protected Label healthLabel;
	protected Group health;
	private float healthBlinkingTime;
	
	protected ProgressBar expBar;
	protected Label expLabel;
	protected Group exp;
	
	protected Table infoTable;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Drawable blankSub;
	
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
		levelLabel = new Label("LV 1", skin, "medium");
		levelLabel.setPosition(2, 125);
		levelLabel.setFontScale(0.7f);
		icon = new Group();
		icon.addActor(iconImage);
		icon.addActor(nameLabel);	
		icon.addActor(levelLabel);	
		
		blankSub = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.blank_icon)));
		
		leftSubImage = new Image(blankSub);
		leftSubImage.setSize(30, 30);
		leftSubAmmo = new Label("", skin, "small");
		leftSubAmmo.setAlignment(Align.right);
		leftSubAmmo.setPosition(2, 2, Align.bottomRight);
		leftSubAmmo.setWidth(leftSubImage.getWidth());
		leftSubAmmo.setFontScale(0.75f);
		leftSub = new Group();
		leftSub.addActor(leftSubImage);
		leftSub.addActor(leftSubAmmo);
		
		
		centerSubImage = new Image(blankSub);
		centerSubImage.setSize(60, 60);
		centerSubAmmo = new Label("", skin, "small");
		centerSubAmmo.setAlignment(Align.right);
		centerSubAmmo.setPosition(2, 2, Align.bottomRight);
		centerSubAmmo.setWidth(centerSubImage.getWidth());
		centerSubAmmo.setFontScale(0.75f);
		centerSub = new Group();
		centerSub.addActor(centerSubImage);
		centerSub.addActor(centerSubAmmo);
		
		rightSubImage = new Image(blankSub);
		rightSubImage.setSize(30, 30);
		rightSubAmmo = new Label("", skin, "small");
		rightSubAmmo.setAlignment(Align.right);
		rightSubAmmo.setPosition(2, 2, Align.bottomRight);
		rightSubAmmo.setWidth(rightSubImage.getWidth());
		rightSubAmmo.setFontScale(0.75f);
		rightSub = new Group();
		rightSub.addActor(rightSubImage);
		rightSub.addActor(rightSubAmmo);
		
		reloadBar = new ProgressBar(0.0f, 1.0f, 0.01f, true, skin, "yellow-vertical");
		
		healthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin, "green-horizontal");
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
		
		infoTable.add(health).width(140).height(40).colspan(3);
		infoTable.row();
		infoTable.add(exp).width(140).height(40).colspan(3);
		infoTable.row();
		infoTable.add(leftSub).width(30).height(60).center();
		infoTable.add(centerSub).width(60).height(60).center();
		infoTable.add(rightSub).width(30).height(60).center();
		
		super.add(icon).width(140).height(140);
		super.add(infoTable);
		super.add(reloadBar).width(40).height(140);
		
		super.setBackground(skin.newDrawable("list", 1.0f, 1.0f, 1.0f, 0.75f));
	}
	
	public void update(float delta) {
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
				centerSubImage.setDrawable(blankSub);
				centerSubAmmo.setText("");
				leftSubImage.setDrawable(blankSub);
				leftSubAmmo.setText("");
				rightSubImage.setDrawable(blankSub);
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
		float healthPercent = (float)player.tank.getHealth() / player.tank.getMaxHealth();
		healthBar.setValue(healthPercent);
		healthLabel.setText(player.tank.getHealth() + " HP");
		if (healthPercent < 0.25) {
			healthBlinkingTime += delta;
			while (healthBlinkingTime >= 2.5f) healthBlinkingTime -= 2.5f;
			if (healthBlinkingTime < 0.75f) {
				float percent = ((0.75f - healthBlinkingTime) / 0.75f);
				healthLabel.setColor(1, percent, percent, 1);
			}
			else if (healthBlinkingTime < 1.5f) {
				float percent = ((healthBlinkingTime - 0.75f) / 0.75f);
				healthLabel.setColor(1, percent, percent, 1);
			}
			else {
				healthLabel.setColor(Color.WHITE);
			}
		}
		else {
			healthBlinkingTime = 1.5f;
			healthLabel.setColor(Color.WHITE);
		}
		levelLabel.setText("LV " + player.tank.getLevel());
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
