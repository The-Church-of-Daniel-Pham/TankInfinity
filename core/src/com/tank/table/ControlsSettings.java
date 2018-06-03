package com.tank.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tank.controls.ControlConstants;
import com.tank.controls.KeyControl;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;

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

	private TextButton forwardText;
	private TextButton backtext;
	private TextButton rTurnText;
	private TextButton lTurnText;
	private TextButton shootText;
	private TextButton subShootText;
	private TextButton rShiftText;
	private TextButton lShiftText;
	private TextButton pauseText;
	
	private Thread settingKey;


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

		Label forwardLabel = new Label("Move Forward", skin, STYLE_NAME);
		forwardText = new TextButton(keyForward, skin, STYLE_NAME);
		forwardText.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (settingKey != null && settingKey.isAlive()) {
					settingKey.interrupt();
				}
				settingKey = new Thread() {
					@Override
					public void run() {
						int loops = 0;
						while(true) {
							KeyControl control = pressedControls();
							if (control != null) {
								saveKey("UP", control);
								updateButton(forwardText, "UP");
							}
							loops++;
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								return;
							}
							if (isInterrupted()) return;
							if (loops == 100) return; //10 seconds
							
						}
					}
				};
				settingKey.start();
				event.stop();
			}
		});

		Label backLabel = new Label("Move Back", skin, STYLE_NAME);
		backtext = new TextButton(keyBack, skin, STYLE_NAME);

		Label rTurnLabel = new Label("Turn Right", skin, STYLE_NAME);
		rTurnText = new TextButton(keyTurnRight, skin, STYLE_NAME);

		Label lTurnLabel = new Label("Turn Left", skin, STYLE_NAME);
		lTurnText = new TextButton(keyTurnLeft, skin, STYLE_NAME);

		Label shootLabel = new Label("Shoot", skin, STYLE_NAME);
		shootText = new TextButton(keyShoot, skin, STYLE_NAME);

		Label subShootLabel = new Label("Shoot sub weapon", skin, STYLE_NAME);
		subShootText = new TextButton(keySubShoot, skin, STYLE_NAME);

		Label rShiftLabel = new Label("Shift subs right", skin, STYLE_NAME);
		rShiftText = new TextButton(keyRshift, skin, STYLE_NAME);

		Label lShiftLabel = new Label("Shift subs left", skin, STYLE_NAME);
		lShiftText = new TextButton(keyLshift, skin, STYLE_NAME);

		Label pauseLabel = new Label("Pause", skin, STYLE_NAME);
		pauseText = new TextButton(keyPause, skin, STYLE_NAME);

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

	private void saveKey(String key, KeyControl input)
	{
		game.players.get(0).controls.setKey(key, input);
	}
	
	public KeyControl pressedControls() {
		int key = keyPressed();
		if (key != -1)
			return new KeyControl(key, 0);
		int button = buttonPressed();
		if (button != -1)
			return new KeyControl(button, 1);
		return null;
	}

	private int keyPressed()
	{
		for(int input = 0; input <= 255; input++ ) {
			boolean isPressed = Gdx.input.isKeyPressed(input);
			if(isPressed)
			{
				return input;
			}
		}
		return -1;
	}

	private int buttonPressed()
	{
		for(int input = 0; input <= 4; input++ ) {
			boolean isPressed = Gdx.input.isButtonPressed(input);
			if(isPressed)
			{
				return input;
			}
		}
		return -1;
	}

	private void updateButton(TextButton b, String key)
	{
		String input = Input.Keys.toString(game.players.get(0).controls.getKey(key));
		if(input.equalsIgnoreCase("unknown"))
		{
			input = "Left Click";
		}

		else if(input.equalsIgnoreCase("soft left"))
		{
			input = "Right Click";
		}

		b.setText(input);
	}
}
