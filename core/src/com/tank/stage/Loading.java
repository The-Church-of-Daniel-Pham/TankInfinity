package com.tank.stage;

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
	private static String [] facts;

	protected Skin skin = Assets.manager.get(Assets.skin);
	protected Texture backdrop = Assets.manager.get(Assets.backdrop);
	protected Texture loading_tank = Assets.manager.get(Assets.loading_tank);

	static {
		facts = new String[] {"Canada is the second largest country in the world, right after Russia.", 
        		"Canada's lowest recorded temperature was -63  degrees Celcius in 1947.", 
        		"Canada has more lakes than the rest of the world's lakes combined.", 
        		"Canada consumes more macaroni and cheese than any other nation in the world.", 
        		"Residents of Churchill, Canada, leave their cars unlocked to offer an escape for pedestrians who might encounter Polar Bears.", 
        		"Licence plates in the Canadian Northwest Territories are shaped like polar bears.", 
        		"Canada has the largest coastline in the world.", 
        		"In Newfoundland, Canada, the Atlantic Ocean sometimes freezes so people play hockey on it.", 
        		"With 1,896 km , the Yonge Street in Canada, is the longest street in the world.", 
        		"The U.S. / Canada Border is the longest international border in the world and it lacks military defense.", 
        		"Canada has no weapons of mass destruction since 1984 and has signed treaties repudiating their possession.", 
        		"\"Canada\" is an Iroquoian language word meaning \"Village.\"", 
        		"Canada's official phone number is 1-800-O-CANADA.", 
        		"Large parts of Canada have less gravity than the rest of Earth. The phenomenon was discovered in the 1960s.", 
        		"Police Departments in Canada give out \"positive tickets\" when they see people doing something positive.", 
        		"Canada consumes the most doughnuts and has the most doughnut shops per capita of any country in the world.", 
        		"The North American Beaver is the national animal of Canada.", 
        		"The Hawaiian Pizza was invented in Canada and is the most popular pizza in Australia.", 
        		"People from Canada can order a portrait of Queen Elizabeth II and have it shipped to them for free.", 
        		"Canada has a strategic maple syrup reserve to ensure global supply in case of emergency."};
	}
	
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
		return facts[(int) (Math.random() *  facts.length)];
    }
}