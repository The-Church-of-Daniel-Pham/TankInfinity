package com.tank.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.tank.controls.ControlConstants;
import com.tank.controls.GamepadController;
import com.tank.controls.KeyControl;
import com.tank.controls.KeyboardMouseController;
import com.tank.controls.TankController;
import com.tank.game.Player;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;

public class ControlsSettingPlayer extends Table{
	private TankInfinity game;
	private Player player;
	private Skin skin = Assets.manager.get(Assets.skin);
	private static final String STYLE_NAME = "medium";
	
	private Label controllerType;
	private int controllerSelected;
	private TextButton nextController;
	private TextButton previousController;

	private TextButton forwardText;
	private TextButton backText;
	private TextButton rTurnText;
	private TextButton lTurnText;
	private TextButton shootText;
	private TextButton subShootText;
	private TextButton rShiftText;
	private TextButton lShiftText;
	private TextButton pauseText;
	private TextButton cursorUp;
	private TextButton cursorDown;
	private TextButton cursorRight;
	private TextButton cursorLeft;
	
	private TextButton defaultButton;
	
	private Table keysTable;
	private Table left;
	private Table right;
	private ScrollPane keysScroll;

	private Thread settingKey;

	public ControlsSettingPlayer(final TankInfinity game, Player player) {
		this.game = game;
		this.player = player;
		
		createControllerTitle();
		createKeysTable();
		createDefaultButton();
		
		super.add(keysScroll).colspan(4);
		super.row();
		super.add(defaultButton).colspan(4);
	}
	
	public void createControllerTitle() {
		controllerType = new Label("", skin, STYLE_NAME);
		controllerType.setAlignment(Align.center);
		nextController = new TextButton(">", skin, STYLE_NAME);
		nextController.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				selectNextController(1);
			}
		});
		
		previousController = new TextButton("<", skin, STYLE_NAME);
		previousController.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				selectNextController(-1);
			}
		});
		super.add(previousController).height(50).width(50);
		super.add(controllerType).height(50).width(400).colspan(2);
		super.add(nextController).height(50).width(50);
		super.row();
	}
	
	public void createKeysTable() {
		keysTable = new Table();
		keysTable.setFillParent(false);
		keysScroll = new ScrollPane(keysTable);
		left = new Table();
		left.defaults().width(300).height(100).space(25);
		right = new Table();
		right.defaults().width(300).height(100).space(25);
		
		super.setFillParent(false);
		super.setDebug(false);

		forwardText = rowCreator("Move Forward", "UP");
		backText = rowCreator("Move Back", "DOWN");
		rTurnText = rowCreator("Turn Right", "RIGHT");
		lTurnText = rowCreator("Turn Left", "LEFT");
		shootText = rowCreator("Shoot", "SHOOT");
		subShootText = rowCreator("Shoot Sub Weapon", "SUB");
		rShiftText = rowCreator("Shift Subs Right", "RSHIFT");
		lShiftText = rowCreator("Shift Subs Left", "LSHIFT");
		pauseText = rowCreator("Pause", "PAUSE");
		cursorUp = rowCreatorCursor("Cursor Up", "CURSOR-UP");
		cursorDown = rowCreatorCursor("Cursor Down", "CURSOR-DOWN");
		cursorRight = rowCreatorCursor("Cursor Right", "CURSOR-RIGHT");
		cursorLeft = rowCreatorCursor("Cursor Left", "CURSOR-LEFT");

		keysTable.add(left);
		keysTable.add(right);
	}
	
	public void createDefaultButton() {
		defaultButton = new TextButton("Reset to Default Controls", skin, STYLE_NAME);
		defaultButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.controls.setToDefault();
				refreshMenu();
			}
		});
	}
	
	public TextButton rowCreator(String labelText, final String key) {
		Label label = new Label(labelText, skin, STYLE_NAME);
		final TextButton button = new TextButton("", skin, STYLE_NAME);
		button.addListener(new ClickListener() {
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
								saveKey(key, control);
								updateButton(button, key);
								return;
							}
							loops++;
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								return;
							}
							if (isInterrupted())
							{
								return;
							}
							if (loops == 1000) {
								return; //10 seconds
							}

						}
					}
				};
				settingKey.start();
				event.stop();
			}
		});
		
		right.add(button);
		left.add(label);

		right.row();
		left.row();
		
		return button;
	}
	
	public TextButton rowCreatorCursor(String labelText, final String key) {
		Label label = new Label(labelText, skin, STYLE_NAME);
		final TextButton button = new TextButton("", skin, STYLE_NAME);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (player.controls instanceof GamepadController) {
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
									saveKey(key, control);
									updateButton(button, key);
									return;
								}
								loops++;
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									return;
								}
								if (isInterrupted())
								{
									return;
								}
								if (loops == 1000) {
									return; //10 seconds
								}
	
							}
						}
					};
					settingKey.start();
					event.stop();
				}
			}
		});
		
		right.add(button);
		left.add(label);

		right.row();
		left.row();
		
		return button;
	}

	private void saveKey(String key, KeyControl input)
	{
		player.controls.setKey(key, input);
	}
	
	public void selectNextController(int change) {
		int nextController = controllerSelected + change;
		if (nextController <= -2) nextController = GamepadController.getControllerAmount() - 1;
		if (nextController >= GamepadController.getControllerAmount()) nextController = -1;
		
		if (nextController != controllerSelected) {
			controllerSelected = nextController;
			if (controllerSelected == -1) {
				TankController keyboardController = new KeyboardMouseController();
				ControlConstants.setPlayerController(player.getNumber(), keyboardController);
				player.setControls(keyboardController);
			}
			else {
				TankController gamepadController = new GamepadController(controllerSelected);
				ControlConstants.setPlayerController(player.getNumber(), gamepadController);
				player.setControls(gamepadController);
			}
		}
		refreshMenu();
	}

	public KeyControl pressedControls() {
		if (player.controls instanceof KeyboardMouseController) {
			int key = keyPressed();
			if (key != -1)
				return new KeyControl(key, 0);
			int button = buttonPressed();
			if (button != -1)
				return new KeyControl(button, 1);
		}
		else if (player.controls instanceof GamepadController) {
			int key = controllerButtonPressed(((GamepadController)player.controls).getController());
			if (key != -1)
				return new KeyControl(key, 0);
			KeyControl axis = controllerAxisMoved(((GamepadController)player.controls).getController());
			if (axis != null)
				return axis;
			KeyControl pov = controllerPOVMoved(((GamepadController)player.controls).getController());
			if (pov != null)
				return pov;
		}
		return null;
	}

	public int keyPressed()
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

	public int buttonPressed()
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
	
	public int controllerButtonPressed(Controller controller) {
		for (int input = 0; input <= 30; input++) {
			if (controller.getButton(input)) {
				return input;
			}
		}
		return -1;
	}
	
	public KeyControl controllerAxisMoved(Controller controller) {
		for (int input = 0; input < 30; input++) {
			if (controller.getAxis(input) > 0.4)
				return new KeyControl(input, 1, 1);
			if (controller.getAxis(input) < -0.4)
				return new KeyControl(input, 1, -1);
		}
		return null;
	}
	
	public KeyControl controllerPOVMoved(Controller controller) {
		PovDirection[] directions = PovDirection.values();
		for (int input = 0; input < 30; input++) {
			for (int direction = 1; direction <= 4; direction++) {
				if (controller.getPov(input).equals(directions[direction])) {
					return new KeyControl(input, 2, direction);
				}
			}
		}
		return null;
	}
	
	public boolean isWaitingForInput() {
		return (settingKey != null && settingKey.isAlive());
	}
	
	public void refreshMenu() {
		updateControlType();
		updateButton(forwardText, "UP");
		updateButton(backText, "DOWN");
		updateButton(rTurnText, "RIGHT");
		updateButton(lTurnText, "LEFT");
		updateButton(shootText, "SHOOT");
		updateButton(subShootText, "SUB");
		updateButton(rShiftText, "RSHIFT");
		updateButton(lShiftText, "LSHIFT");
		updateButton(pauseText, "PAUSE");
		updateCursorButton(cursorUp, "CURSOR-UP");
		updateCursorButton(cursorDown, "CURSOR-DOWN");
		updateCursorButton(cursorLeft, "CURSOR-LEFT");
		updateCursorButton(cursorRight, "CURSOR-RIGHT");
	}
	
	private void updateControlType() {
		if (player.controls instanceof KeyboardMouseController) {
			controllerSelected = -1;
			controllerType.setText("Keyboard & Mouse");
		}
		else if (player.controls instanceof GamepadController) {
			controllerSelected = ((GamepadController)player.controls).getIndex();
			controllerType.setText("Gamepad " + (controllerSelected + 1));
		}
	}

	private void updateButton(TextButton b, String key)
	{
		String input = "";
		if (player.controls instanceof KeyboardMouseController)
			input = getKeyboardInputString(key);
		else if (player.controls instanceof GamepadController)
			input = getGamepadInputString(key);
		b.setText(input);
	}
	
	private void updateCursorButton(TextButton b, String key) {
		String input = "";
		if (player.controls instanceof KeyboardMouseController)
			input = "<Mouse>";
		else if (player.controls instanceof GamepadController)
			input = getGamepadInputString(key);
		b.setText(input);
	}
	
	private String getKeyboardInputString(String key)
	{
		KeyControl control = player.controls.getKeyControl(key);
		
		if(control.getKeyType() == 0)
		{
			return Input.Keys.toString(control.getKeyCode());
		}
		else
		{
			switch(control.getKeyCode())
			{
				case 0: return "Left Click";
				case 1: return "Right Click";
				case 2: return "Center Click";
				case 3: return "Back Click";
				case 4: return "Forward Click";
				default: return "Error";
			}
				
		}
	}
	
	private String getGamepadInputString(String key)
	{
		KeyControl control = player.controls.getKeyControl(key);
		
		if(control.getKeyType() == 0)
		{
			return "Button " + control.getKeyCode();
		}
		else if (control.getKeyType() == 1)
		{
			if (control.getDirection() > 0)
				return "Positive Axis " + control.getKeyCode();
			else
				return "Negative Axis " + control.getKeyCode();
				
		}
		else {
			return "POV " + control.getKeyCode() + " - " + PovDirection.values()[control.getDirection()].toString();
		}
	}
}
