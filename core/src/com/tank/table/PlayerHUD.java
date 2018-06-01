package com.tank.table;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	protected ProgressBar reloadBar;
	protected ProgressBar healthBar;
	private Skin skin = Assets.manager.get(Assets.skin);
	private final Image leftSubImage = new Image(Assets.manager.get(Assets.sub_empty));
	private final Image centerSubImage = new Image(Assets.manager.get(Assets.sub_empty));
	private final Image rightSubImage = new Image(Assets.manager.get(Assets.sub_empty));
	private static TextureRegionDrawable subEmptyTex = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.sub_empty)));
	private SubWeapon lastCenterSubWeapon;
	
	public PlayerHUD(Player player) {
		this.player = player;
		
		super.setFillParent(false);
		super.setDebug(false);
		super.defaults().width(150).height(25).space(25).center();
		
		Label nameLabel = new Label(player.getName(), skin);
		nameLabel.setAlignment(Align.left);
		//final Image leftSubImage = new Image(Assets.manager.get(Assets.sub_empty));
		//final Image centerSubImage = new Image(Assets.manager.get(Assets.sub_empty));
		//final Image rightSubImage = new Image(Assets.manager.get(Assets.sub_empty));
		reloadBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin);
		healthBar = new ProgressBar(0.0f, player.tank.getMaxHealth(), 0.01f, false, skin);

		super.add(nameLabel).left().colspan(3);
		super.row();
		super.add(leftSubImage).width(50).height(50);
		super.add(centerSubImage).width(50).height(50);
		super.add(rightSubImage).width(50).height(50);
		super.row();
		super.add(reloadBar).colspan(3);
		super.row();
		super.add(healthBar).colspan(3);
	}
	
	public void update() {
		if (subChanged()) {
			if (player.tank.getCurrentSubWeapon() != null) {
				centerSubImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.tank.getCurrentSubWeapon().getTexture())));
				leftSubImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.tank.getPrevSubWeapon().getTexture())));
				rightSubImage.setDrawable(new TextureRegionDrawable(new TextureRegion(player.tank.getNextSubWeapon().getTexture())));
			}
			else {
				centerSubImage.setDrawable(subEmptyTex);
				leftSubImage.setDrawable(subEmptyTex);
				rightSubImage.setDrawable(subEmptyTex);
			}
			lastCenterSubWeapon = player.tank.getCurrentSubWeapon();
		}
		float completion = player.tank.getReloadTime() / player.tank.getLastReloadTime();;
		// reload time out of max reload time (inverse of rate of fire)
		//System.out.println(completion);
		reloadBar.setValue(completion);
		healthBar.setValue(player.tank.getHealth());
	}
	
	public boolean subChanged() {
		if (player.tank.getCurrentSubWeapon() == null && lastCenterSubWeapon == null) return false;
		if (player.tank.getCurrentSubWeapon() != null && lastCenterSubWeapon == null) return true;
		if (player.tank.getCurrentSubWeapon() == null && lastCenterSubWeapon != null) return true;
		return !player.tank.getCurrentSubWeapon().equals(lastCenterSubWeapon);
	}
}
