package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tank.utils.Assets;

public class ControlsSettings extends Table{
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public ControlsSettings() {
		super.setFillParent(false);
		super.setDebug(false); // This is optional, but enables debug lines for tables.

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
		
		Table left = new Table();
		left.defaults().width(300).height(100).space(25);
		Table right = new Table();
		right.defaults().width(300).height(100).space(25);
		
		left.add(upLabel);
		left.row();
		left.add(downLabel);
		left.row();
		left.add(rightLabel);
		left.row();
		left.add(leftLabel);
		
		right.add(shootLabel);
		right.row();
		right.add(subLabel);
		right.row();
		right.add(rshiftLabel);
		right.row();
		right.add(lshiftLabel);
		right.row();
		right.add(pauseLabel);
		
		super.defaults().top().space(25);
		super.add(left);
		super.add(right);
	}
}
