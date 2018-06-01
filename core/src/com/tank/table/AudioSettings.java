package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.tank.utils.Assets;

public class AudioSettings extends Table{
	private Skin skin = Assets.manager.get(Assets.skin);
	
	public AudioSettings() {
		super.setFillParent(false);
		super.setDebug(false); // This is optional, but enables debug lines for tables.
		super.defaults().width(600).height(100).space(25).center();

		// Add widgets to the table here.
		Label musicLabel = new Label ("Music Volume ", skin);
		musicLabel.setAlignment(Align.right);
		Label sfxLabel = new Label ("Sound Effects Volume ", skin);
		sfxLabel.setAlignment(Align.right);
		Slider musicSlider = new Slider(0, 10, 1, false, skin);
		musicSlider.setValue(10);
		Slider sfxSlider = new Slider(0, 10, 1, false, skin);
		sfxSlider.setValue(10);

		super.add(musicLabel).right();
		super.add(musicSlider);
		super.row();
		super.add(sfxLabel).right();
		super.add(sfxSlider);
	}
}
