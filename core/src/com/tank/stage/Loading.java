package com.tank.stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.actor.ui.Background;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class Loading extends Stage implements InputProcessor {
	protected TankInfinity game;
	protected Table uiTable;
	private Background tankLoadingBackground;
	private float distance;
	private float percent;

	protected Skin skin = Assets.manager.get(Assets.skin);
	protected Texture backdrop = Assets.manager.get(Assets.backdrop);
	protected Texture loading_tank = Assets.manager.get(Assets.loading_tank);

	public Loading(TankInfinity game) {
		// super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
		this.game = game;
		Background backdropBackground = new Background(backdrop);
		backdropBackground.setFill(true);
		tankLoadingBackground = new Background(loading_tank);
		tankLoadingBackground.setPosition(-loading_tank.getWidth(), 400);
		percent = 0;
		distance = Gdx.graphics.getWidth() + loading_tank.getWidth();
		buildTable();
		super.addActor(backdropBackground);
		super.addActor(tankLoadingBackground);
		super.addActor(uiTable);
	}

	@Override
	public void act(float delta) {
		percent = Interpolation.linear.apply(percent, Assets.manager.getProgress(), 0.1f);
		tankLoadingBackground.setX(percent * distance);
	}

	private void buildTable() {
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiTable.setDebug(false);
		uiTable.bottom().padBottom(100).right().padRight(50);

		// Add widgets to the table here.
		Label tipLabel = new Label(getTip(), skin, "mediumWithBackground");
		tipLabel.setAlignment(Align.topLeft);
		tipLabel.setFontScale(0.75f);
		tipLabel.setWrap(true);
		uiTable.add(tipLabel).width(600).height(150).right();
	}

	private String getTip() {
		 // The name of the file to open.
        String fileName = "ui/menu/loading/canada_facts.txt";
        
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            //select a random line number, where first number of file is number of lines
            int rand = (int) (Math.random() *  Integer.parseInt(bufferedReader.readLine()));
            int count = 0;
            
            while((line = bufferedReader.readLine()) != null) {
            	if (count++ == rand) {
            		return line;
            	}
            }   

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");
        }
        return "";
    }
}