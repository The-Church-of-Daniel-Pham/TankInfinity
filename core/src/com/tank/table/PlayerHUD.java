package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.tank.game.Player;
import com.tank.utils.Assets;

public class PlayerHUD extends Table{
	protected Player player;
	protected ProgressBar reloadBar;
	protected ProgressBar healthBar;
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public PlayerHUD(Player player) {
		this.player = player;
		
		super.setFillParent(false);
		super.setDebug(false);
		super.defaults().width(150).height(25).space(25).center();
		
		Label nameLabel = new Label(player.getName(), skin);
		nameLabel.setAlignment(Align.left);
		final Image leftSubImage = new Image(Assets.manager.get(Assets.sub_empty));
		final Image centerSubImage = new Image(Assets.manager.get(Assets.sub_empty));
		final Image rightSubImage = new Image(Assets.manager.get(Assets.sub_empty));
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
		float completion = player.tank.getReloadTime() / player.tank.getLastReloadTime();;
		// reload time out of max reload time (inverse of rate of fire)
		//System.out.println(completion);
		reloadBar.setValue(completion);
		healthBar.setValue(player.tank.getHealth());
	}
}
