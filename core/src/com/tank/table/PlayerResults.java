package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tank.game.Player;
import com.tank.utils.Assets;

public class PlayerResults extends Table{
	protected Player player;
	
	private Skin skin = Assets.manager.get(Assets.skin);
	private Label levelLabel;
	private Label expLabel;
	private Label killsLabel;
	
	public PlayerResults(Player player) {
		this.player = player;
		
		super.setFillParent(false);
		super.setDebug(false);
		super.defaults().pad(10);
		
		Table left = new Table();
		left.defaults().height(50).pad(10);
		Image iconImage = new Image(player.custom.getTexture("preview"));
		iconImage.setSize(200, 200);
		Label nameLabel = new Label(player.getName(), skin, "medium");
		left.add(nameLabel);
		left.row();
		left.add(iconImage).width(200).height(200);
		
		Table right = new Table();
		right.defaults().height(50).pad(10);
		levelLabel = new Label("LV " + player.tank.getLevel(), skin, "medium");
		levelLabel.setFontScale(0.75f);
		expLabel = new Label(player.tank.getTotalExp() + " Total EXP", skin, "medium");
		expLabel.setFontScale(0.75f);
		killsLabel = new Label(player.tank.getKillCount() + " Kills", skin, "medium");
		killsLabel.setFontScale(0.75f);
		right.add(levelLabel);
		right.row();
		right.add(expLabel);
		right.row();
		right.add(killsLabel);
		
		super.add(left);
		super.add(right).bottom();
		
		super.setBackground(skin.getDrawable("list"));
	}
	
	public void updateMenu() {
		if (player.isEnabled()) {
			levelLabel.setText("LV " + player.tank.getLevel());
			expLabel.setText(player.tank.getTotalExp() + " Total EXP");
			killsLabel.setText(player.tank.getKillCount() + " Kills");
		}
	}
}
