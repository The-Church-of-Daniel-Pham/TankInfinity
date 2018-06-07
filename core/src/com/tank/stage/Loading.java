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
        		"Canada has a strategic maple syrup reserve to ensure global supply in case of emergency.",
        		"Some enemy tanks cannot rotate their guns; they can only rotate their treads to aim at you.",
        		"Tanks can push other tanks around a little bit, allowing for pins to work.",
        		"If you are flush against a wall and can't rotate, you can use your gun to recoil out.",
        		"When Boomerangs are shot, they also move in an arc based on how fast the tank is rotating.",
        		"Caltrops deal damage and slow down the enemy--use this to your advantage.",
        		"Caltrops can be used as a temporary wall against bullets.",
        		"Be wary when entering corriders; you can get easily cornered.",
        		"Explosions continue to deal damage while they are visible, so don't wander into one!",
        		"Lasers can deal lots of damage by repeatedly deflecting against walls or by going through multiple tanks.",
        		"While the Moose Stampede is perhaps the most powerful weapon, using it at the right time is difficult.",
        		"The Pellet Shot is most effective at short range against lower armored tanks.",
        		"Rockets can destroy walls as well as deal massive damage to enemy tanks.",
        		"More powerful projectiles will destroy weaker ones. Projectiles of equal durability destroy each other.",
        		"The Vampiric Fang and Laser is 100% accurate, firing directly at your cursor.",
        		"Beware of the Artillery Tank-- it cannot fire moving projectiles, but you must keep moving to avoid its shells.",
        		"The Peashooter is a white small enemy tank that doesn't rotate its gun. It is the most common enemy in the game.",
        		"The Free Peashooter is a dark-tan small enemy tank that gets to rotate its gun. It will be moving and shooting at the same time.",
        		"The Landminer is a black small enemy tank that can drop landmines. These are very dangerous, so either avoid them or destroy them from afar.",
        		"The Bigshot is a teal square enemy tank that fires slow, high-damage Rockets. They also have high armor.",
        		"A level 1 player tank can have up to three active bullets. Increase this with the max projectile upgrade.",
        		"Max projectile count only affects your normal bullets, not subweapon ones.",
        		"All subweapons have an equal chance of spawning.",
        		"Shooting projectiles and getting hit by projectiles deal knockback.",
        		"The Vampiric Fang does low, but armor-piercing, damage.",
        		"Range does not affect projectile damage--we didn't bother coding that.",
        		"Landmines explode after a certain amount of time if they are not triggered.",
        		"The Chakram has the second highest amount of bounces per bullet, behind the laser.",
        		"A tank not facing you will not shoot at you. Similarly, tanks will only shoot at you with a direct line of sight (except Artillery Tanks).",
        		"The Bigshots have a rather reload time after firing a rocket. So do player tanks, when they fire rockets.",
        		"Be careful on shooting subweapons with a long reload time if you plan on shooting anything again immediately.",
        		"You can use portals to escape--not all enemy tanks need to be destroyed to enter it.",
        		"The Artillery does have a three second countdown, but the actual explosion occurs some time after that countdown.",
        		"The damage dealt by a Vampiric Fang is the amount of health restored to the shooter.",
        		"Our highscore is Level 13. Our highscore by clearing all enemies each level is Level 9.",
        		"We apologize if by random chance, you spawn with many enemy tanks surrounding you.",
        		"You can't outrun bullets, but you can outrun rockets.",
        		"As level number increases, enemy tanks get stronger (more damage, less damage taken, etc.).",
        		"Choose your upgrades wisely! You can't take back an upgrade once selected.",
        		"You restore 20% of your max HP upon moving on to the next level, and 40% of your max HP upon picking up a Repair Kit.",
        		"The Quit button is an option.",
        		"In multiplayer games, only one tank has to survive to the portal. On the next level, all dead players will respawn with 10% HP.",
        		"There are no assists--only the player whose projectile that destroys the enemy will gain exp.",
        		"Projectiles are not 100% accurate when you shoot them. The accuracy goes down even more when on the move.",
        		"Less items will spawn if you take too long on a level.",
        		"Yes, moving by shooting in the opposite direction is valid.",
        		"The Anti-Bullet Wall blocks all projectiles going through it, but not explosions. It's not invincible however.",
        		"The Anti-Bullet Wall can slow enemies that try to walk through it. It's pretty useful for escaping.",
        		"The amount of Moose, Caltrops, and Pellets that are fired per shot is dependent on your max projectile count.",
        		"Explosions will destroy any opposing bullets that enter it.",
        		"There is no friendly fire. Similarly, you cannot destroy ally bullets.",
        		"The slowest reloading weapons are the Moose Stampede, Rocket, Anti-Bullet Wall, Landmine, and Artillery (in that order).",
        		"The fastest reloading weapons, apart from the standard bullet, are the Vampiric Fang and Laser.",
        		"Accuracy and Spread are different. Accuracy increases the chance of a bullet going down the line, Spread is the variable range of a bullet angle.",
        		"You shoot much less accurately if you are moving around. Use the Stability upgrade if you like to move and shoot for more accuracte shots.",
        		"Ironically, the Rocket is the best weapon against Bigshots, and the Artillery is the best weapon against Artillery Tanks.",
        		"Having more players doesn't make tanks stronger or have more of them. You just have to EXP ration correctly.",
        		"Rockets, Artillery, and Landmine are the only weapons that deal explosion damage.",
        		"The explosion that happens when a tank is destroyed is purely cosmetic.",
        		"Max bounce and lifetime are useful upgrades when you want your bullets to affect enemies far away.",
        		"This tip has a 2.5% chance of appearing."};
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