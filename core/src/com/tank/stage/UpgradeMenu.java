package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.table.PlayerUpgradeMenu;
import com.tank.utils.Assets;

public class UpgradeMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	protected int countEnabled;
	private Skin skin = Assets.manager.get(Assets.skin);

	public UpgradeMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	private void buildTable() {
		uiTable.setFillParent(true);
		uiTable.setDebug(true); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(500).height(400).space(50).center();
		Label title = new Label("Upgrades", skin);
		title.setAlignment(Align.top);
		uiTable.add(title).expand().top().colspan(2);
		uiTable.padTop(50);
		uiTable.row();
		for (int i = 0; i < game.players.size(); i++) {
			uiTable.add(new PlayerUpgradeMenu(game.players.get(i)));
			if (i % 2 == 1) {
				uiTable.row();
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		countEnabled = 0;
		for (Player p : game.players) {
			if (p.isEnabled()) {
				countEnabled++;
			}
		}
	}

}
