package com.tank.table;

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
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public PlayerHUD(Player player) {
		this.player = player;
		
		super.setDebug(false);
		super.defaults().width(200).height(50).space(25).center();
		
		Label nameLabel = new Label(player.getName(), skin);
		nameLabel.setAlignment(Align.left);
		reloadBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin);

		super.add(nameLabel).left();
		super.row();
		super.add(reloadBar).width(300);
	}
	
	public void update() {
		float completion = player.tank.getReloadTime() / (1f / player.tank.getStatValue("Rate_Of_Fire"));
		// reload time out of max reload time (inverse of rate of fire)
		System.out.println(completion);
		reloadBar.setValue(completion);
	}
}
