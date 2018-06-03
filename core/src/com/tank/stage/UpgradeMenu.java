package com.tank.stage;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.table.PlayerUpgradeMenu;
import com.tank.utils.Assets;

public class UpgradeMenu extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	protected int countEnabled;
	private Skin skin = Assets.manager.get(Assets.skin);
	private ArrayList<PlayerUpgradeMenu> pUpgradeMenus;

	public UpgradeMenu(TankInfinity game) {
		super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.game = game;
		pUpgradeMenus = new ArrayList<PlayerUpgradeMenu>();
		uiTable = new Table();
		buildTable();
		super.addActor(uiTable);
	}

	private void buildTable() {
		TextButton continueButton = new TextButton("Continue", skin);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
					game.setScreen(game.screens.get("Play"));
					event.stop();
				
			}
		});
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.
		uiTable.defaults().width(500).height(400).space(50).center();
		Label title = new Label("Upgrades", skin);
		uiTable.add(title).top().colspan(2).height(100).width(250).fill();
		uiTable.padTop(50);
		uiTable.row();
		for (int i = 0; i < game.players.size(); i++) {
			pUpgradeMenus.add(new PlayerUpgradeMenu(game.players.get(i)));
			uiTable.add(pUpgradeMenus.get(i)).expand();
			if (i % 2 == 1) {
				uiTable.row();
			}
		}
		uiTable.add(continueButton).colspan(2).bottom().right().height(100).width(300);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for(PlayerUpgradeMenu m: pUpgradeMenus) {
			if(m.needsToRefresh()) {
				m.refreshMenu();
			}
		}
	}

}
