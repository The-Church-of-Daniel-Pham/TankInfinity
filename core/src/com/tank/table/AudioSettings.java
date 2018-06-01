package com.tank.table;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.tank.media.MediaMusic;
import com.tank.media.MediaSound;
import com.tank.utils.Assets;

public class AudioSettings extends Table{
	protected Slider musicSlider;
	protected Slider sfxSlider;
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
		musicSlider = new Slider(0, 1.0f, 0.1f, false, skin);
		musicSlider.setValue(MediaMusic.getGlobalVolume());
		sfxSlider = new Slider(0, 1.0f, 0.1f, false, skin);
		sfxSlider.setValue(MediaSound.getGlobalVolume());

		super.add(musicLabel).right();
		super.add(musicSlider);
		super.row();
		super.add(sfxLabel).right();
		super.add(sfxSlider);
	}
	
	public float getMusicVol() {
		return musicSlider.getValue();
	}
	
	public float getSfxVol() {
		return sfxSlider.getValue();
	}
}
