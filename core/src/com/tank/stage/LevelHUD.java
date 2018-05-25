package com.tank.stage;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class LevelHUD extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected ArrayList<PlayerTank> players;
	private Label fpsLabel;
	private static float sinceChange;
	private ProgressBar reloadBar;

	private Skin skin = Assets.manager.get(Assets.skin);

	public LevelHUD(TankInfinity game, ArrayList<PlayerTank> players) {
		super(new ExtendViewport(Constants.PREFERRED_WINDOW_WIDTH, Constants.PREFERRED_WINDOW_HEIGHT));
		this.game = game;
		this.players = players;
		super.addActor(buildTable());
	}

	@Override
	public void act(float delta) {
		//update fps counter
		sinceChange += delta;	//add time since last act() to counter
        if(sinceChange >= 1.0f) {	//after 1 second or more
            sinceChange = 0;	//reset counter
    		fpsLabel.setText(Gdx.graphics.getFramesPerSecond() + " FPS");	//update fps label
        }
        
        //update reload bar
		float completion = players.get(0).getReloadTime() / (1 / PlayerTank.RATE_OF_FIRE); 
		// reload time out of max reload time (inverse of rate of fire)
		reloadBar.setValue(completion);
	}

	private Table buildTable() {
		Table uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		fpsLabel = new Label("0 FPS", skin);
		TextButton pauseButton = new TextButton("Pause", skin);
		Label nameLabel = new Label("PLayer " + players.get(0).getPlayerNumber(), skin);
		reloadBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, skin);

		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().pause();
				event.stop();
			}
		});

		uiTable.defaults().width(200).height(75).pad(25).center();
		uiTable.add(fpsLabel).width(100).expand().top().left();
		uiTable.add(pauseButton).width(150).expand().top().right();
		uiTable.row();
		uiTable.add(nameLabel).expandX().bottom().left();
		uiTable.row();
		uiTable.add(reloadBar).width(300).expandX().bottom().left();

		return uiTable;
	}
}