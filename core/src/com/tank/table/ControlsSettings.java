package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tank.utils.Assets;

public class ControlsSettings extends Table{
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public ControlsSettings() {
		super.setFillParent(false);
		super.setDebug(true); // This is optional, but enables debug lines for tables.
		super.defaults().width(300).height(100).space(25).center();

		// Add widgets to the table here.
		Label upLabel = new Label("Up", skin);
		Label downLabel = new Label("Down", skin);
		Label rightLabel = new Label("Right", skin);
		Label leftLabel = new Label("Left", skin);
		Label shootLabel = new Label("Shoot", skin);
		Label subLabel = new Label("Secondary", skin);
		Label rshiftLabel = new Label("Right Shift", skin);
		Label lshiftLabel = new Label("Left Shift", skin);
		Label pauseLabel = new Label("Pause", skin);
		
		super.add(upLabel);
		super.row();
		super.add(downLabel);
		super.row();
		super.add(rightLabel);
		super.row();
		super.add(leftLabel);
		super.row();
		super.add(shootLabel);
		super.row();
		super.add(subLabel);
		super.row();
		super.add(rshiftLabel);
		super.row();
		super.add(lshiftLabel);
		super.row();
		super.add(pauseLabel);
	}
}
