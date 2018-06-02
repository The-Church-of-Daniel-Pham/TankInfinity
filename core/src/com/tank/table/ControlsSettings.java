package com.tank.table;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tank.controls.ControlConstants;
import com.tank.controls.KeyControl;
import com.tank.controls.TankController;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;

import javax.xml.bind.annotation.XmlType;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;

public class ControlsSettings extends Table{
	private TankInfinity game;
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

	private TextField forwardText;
	private TextField backtext;
	private TextField rTurnText;
	private TextField lTurnText;
	private TextField shootText;
	private TextField subShootText;
	private TextField rShiftText;
	private TextField lShiftText;
	private TextField pauseText;


	public ControlsSettings(TankInfinity game) {
		this.game = game;
		
		super.setFillParent(true);
		super.setDebug(false);

		if(keyShoot.equalsIgnoreCase("unknown"))
		{
			keyShoot = "Left Click";
		}

		if(keySubShoot.equalsIgnoreCase("soft left"))
		{
			keySubShoot = "Right Click";
		}


		TextButton applyButton = new TextButton("Apply", skin);
		applyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				saveKeys();
				event.stop();
			}
		});

		Label forwardLabel = new Label("Move Forward", skin, STYLE_NAME);
		forwardText = new TextField(keyForward, skin, STYLE_NAME);

		Label backLabel = new Label("Move Back", skin, STYLE_NAME);
		backtext = new TextField(keyBack, skin, STYLE_NAME);

		Label rTurnLabel = new Label("Turn Right", skin, STYLE_NAME);
		rTurnText = new TextField(keyTurnRight, skin, STYLE_NAME);

		Label lTurnLabel = new Label("Turn Left", skin, STYLE_NAME);
		lTurnText = new TextField(keyTurnLeft, skin, STYLE_NAME);

		Label shootLabel = new Label("Shoot", skin, STYLE_NAME);
		shootText = new TextField(keyShoot, skin, STYLE_NAME);

		Label subShootLabel = new Label("Shoot sub weapon", skin, STYLE_NAME);
		subShootText = new TextField(keySubShoot, skin, STYLE_NAME);

		Label rShiftLabel = new Label("Shift subs right", skin, STYLE_NAME);
		rShiftText = new TextField(keyRshift, skin, STYLE_NAME);

		Label lShiftLabel = new Label("Shift subs left", skin, STYLE_NAME);
		lShiftText = new TextField(keyLshift, skin, STYLE_NAME);

		Label pauseLabel = new Label("Pause", skin, STYLE_NAME);
		pauseText = new TextField(keyPause, skin, STYLE_NAME);

		Table left = new Table();
		left.defaults().width(300).height(50).space(25);
		Table right = new Table();
		right.defaults().width(300).height(50).space(25);

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

		right.add(applyButton);

		super.defaults().top().space(25);
		super.add(left);
		super.add(right);
	}

	public void saveKeys()
	{
		game.players.get(0).controls.setKey("UP", new KeyControl(Input.Keys.valueOf(forwardText.getText().toUpperCase().substring(0,1)), 0));
	}
}
