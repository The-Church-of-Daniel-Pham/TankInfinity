package com.tank.table;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.tank.controls.ControlConstants;
import com.tank.utils.Assets;

import javax.xml.bind.annotation.XmlType;
import java.awt.event.KeyEvent;

public class ControlsSettings extends Table{
	private Skin skin = Assets.manager.get(Assets.skin);
	private static final String STYLE_NAME = "medium";
	private static String keyForward = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("UP").getKeyCode());
	private static String keyBack = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("DOWN").getKeyCode());
	private static String keyTurnRight = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("RIGHT").getKeyCode());
	private static String keyTurnLeft = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("LEFT").getKeyCode());
	private static String keyShoot = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("SHOOT").getKeyCode());
	private static String keySubShoot = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("SUB").getKeyCode());
	private static String keyRshift = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("RSHIFT").getKeyCode());
	private static String keyLshift = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("LSHIFT").getKeyCode());
	private static String keyPause = Input.Keys.toString(ControlConstants.DEFAULT_KEYBOARD_CONTROLS.get("PAUSE").getKeyCode());
	
	public ControlsSettings() {
		super.setFillParent(false);
		super.setDebug(false);

		if(keyShoot.equalsIgnoreCase("unknown"))
		{
			keyShoot = "Left Click";
		}

		if(keySubShoot.equalsIgnoreCase("soft left"))
		{
			keySubShoot = "Right Click";
		}

		Label forwardLabel = new Label("Move Forward", skin, STYLE_NAME);
		TextField forwardText = new TextField(keyForward, skin, STYLE_NAME);

		Label backLabel = new Label("Move Back", skin, STYLE_NAME);
		TextField backtext = new TextField(keyBack, skin, STYLE_NAME);

		Label rTurnLabel = new Label("Turn Right", skin, STYLE_NAME);
		TextField rTurnText = new TextField(keyTurnRight, skin, STYLE_NAME);

		Label lTurnLabel = new Label("Turn Left", skin, STYLE_NAME);
		TextField lTurnText = new TextField(keyTurnLeft, skin, STYLE_NAME);

		Label shootLabel = new Label("Shoot", skin, STYLE_NAME);
		TextField shootText = new TextField(keyShoot, skin, STYLE_NAME);

		Label subShootLabel = new Label("Shoot sub weapon", skin, STYLE_NAME);
		TextField subShootText = new TextField(keySubShoot, skin, STYLE_NAME);

		Label rShiftLabel = new Label("Shift subs right", skin, STYLE_NAME);
		TextField rShiftText = new TextField(keyRshift, skin, STYLE_NAME);

		Label lShiftLabel = new Label("Shift subs left", skin, STYLE_NAME);
		TextField lShiftText = new TextField(keyLshift, skin, STYLE_NAME);

		Label pauseLabel = new Label("Pause", skin, STYLE_NAME);
		TextField pauseText = new TextField(keyPause, skin, STYLE_NAME);

		Table left = new Table();
		left.defaults().width(300).height(100).space(25);
		Table right = new Table();
		right.defaults().width(300).height(100).space(25);
		
		right.add(forwardText);
		left.add(forwardLabel);

		right.row();
		left.row();

		right.add(backtext);
		left.add(backLabel);

		right.row();
		left.row();

		right.add(rTurnText);
		left.add(rTurnLabel);

		right.row();
		left.row();

		right.add(lTurnText);
		left.add(lTurnLabel);

		right.row();
		left.row();

		right.add(shootText);
		left.add(shootLabel);

		right.row();
		left.row();

		right.add(subShootText);
		left.add(subShootLabel);

		right.row();
		left.row();

		right.add(rShiftText);
		left.add(rShiftLabel);

		right.row();
		left.row();

		right.add(lShiftText);
		left.add(lShiftLabel);

		right.row();
		left.row();

		right.add(pauseText);
		left.add(pauseLabel);

		right.row();
		left.row();

		super.defaults().top().space(25);
		super.add(left);
		super.add(right);
	}
}
