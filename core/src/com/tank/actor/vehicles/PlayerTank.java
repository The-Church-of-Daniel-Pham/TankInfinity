package com.tank.actor.vehicles;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.items.AbstractItem;
import com.tank.actor.items.RepairBoxItem;
import com.tank.actor.items.SubWeaponItem;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.map.tiles.PortalTile;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.projectiles.VampiricFang;
import com.tank.actor.ui.MovingText;
import com.tank.animations.DeathExplosion;
import com.tank.animations.Moose;
import com.tank.game.Player;
import com.tank.interfaces.Collidable;
import com.tank.media.MediaSound;
import com.tank.stage.Level;
import com.tank.stats.Upgrade;
import com.tank.subweapons.SubWeapon;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;
import com.tank.utils.CycleList;

public class PlayerTank extends FreeTank {
	/**
	 * used for spawning bullets the correct distance away from Vehicle's center
	 */
	public static final int TANK_GUN_LENGTH = 135; // probably redundant, check free tank
	private static float angle; // angle between diagonal of rectangle and its base

	protected Player player;
	protected CycleList<SubWeapon> subWeapons;
	protected int playerNumber;

	private final float TREAD_VOLUME = 0.2f;
	private final float ENGINE_VOLUME = 0.6f;
	private final float SHOOT_VOLUME = 0.6f;

	// private MediaSound engine_sound = new
	// MediaSound(Assets.manager.get(Assets.tank_engine), ENGINE_VOLUME);
	// private MediaSound tread_sound = new
	// MediaSound(Assets.manager.get(Assets.tank_tread), TREAD_VOLUME);
	private MediaSound engine_sound = new MediaSound(Gdx.audio.newSound(Gdx.files.internal("audio/tank_engine.wav")),
			ENGINE_VOLUME);
	private MediaSound tread_sound = new MediaSound(Gdx.audio.newSound(Gdx.files.internal("audio/tank_tread.wav")),
			TREAD_VOLUME);
	private MediaSound shoot_sound = new MediaSound(Assets.manager.get(Assets.bullet_fire), SHOOT_VOLUME);

	private boolean treadSoundOn = false;
	private boolean engineSoundOn = false;

	private final int SOME_CONSTANT = 1000;
	private final int THRESH = 20;

	private final int GUN_OFFSET = -8;
	private final int GUN_PIVOT = -12;

	private boolean markedForNextLevel; // Used to progress to next level

	protected float reloadTime;
	protected float lastReloadTime;
	
	protected boolean subRightHeld;
	protected boolean subLeftHeld;
	
	protected int level;
	protected int exp;
	protected int totalExp;
	protected int nextExp;		//Model: 4 + (Level * 2) + (Level ^ 1.5) / 4
	protected ArrayList<Upgrade> selectableUpgrades;
	protected int upgradesLeft;

	public PlayerTank(int playerNumber, Player player) {
		super(0, 0); // defaults
		this.playerNumber = playerNumber;
		this.player = player;
		initializeStats();
		initializeExpLevel();
		subWeapons = new CycleList<SubWeapon>();
		reloadTime = 0;
		markedForNextLevel = false;
		super.setGunOffsetX(GUN_OFFSET);
		super.setGunPivotX(gunTexture.getWidth() / 2 + GUN_PIVOT);
		setWidth(80);
		setHeight(90);
		angle = (float) Math.toDegrees(Math.atan((double) getHeight() / getWidth()));
		selectableUpgrades = new ArrayList<Upgrade>();
		initializeHitbox();
	}

	public PlayerTank(int playerNumber, Player player, float x, float y) {
		super(x, y);
		this.playerNumber = playerNumber;
		this.player = player;
		initializeStats();
		initializeExpLevel();
		subWeapons = new CycleList<SubWeapon>();
		reloadTime = 0;
		markedForNextLevel = false;
		super.setGunOffsetX(GUN_OFFSET);
		super.setGunPivotX(gunTexture.getWidth() / 2 + GUN_PIVOT);
		setWidth(80);
		setHeight(90);
		angle = (float) Math.toDegrees(Math.atan((double) getHeight() / getWidth()));
		selectableUpgrades = new ArrayList<Upgrade>();
		initializeHitbox();
	}

	public PlayerTank(int playerNumber, Player player, int row, int col, float direction) {
		super(col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
				row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2);
		this.playerNumber = playerNumber;
		this.player = player;
		initializeStats();
		initializeExpLevel();
		subWeapons = new CycleList<SubWeapon>();
		reloadTime = 0;
		markedForNextLevel = false;
		super.setGunOffsetX(GUN_OFFSET);
		super.setGunPivotX(gunTexture.getWidth() / 2 + GUN_PIVOT);
		setWidth(80);
		setHeight(90);
		angle = (float) Math.toDegrees(Math.atan((double) getHeight() / getWidth()));
		setRotation(direction);
		selectableUpgrades = new ArrayList<Upgrade>();
		initializeHitbox();
	}

	public void setupTank(int row, int col, float direction) {
		engine_sound = new MediaSound(Gdx.audio.newSound(Gdx.files.internal("audio/tank_engine.wav")), ENGINE_VOLUME);
		engineSoundOn = false;
	    tread_sound =  new MediaSound(Gdx.audio.newSound(Gdx.files.internal("audio/tank_tread.wav")), TREAD_VOLUME);
	    treadSoundOn = false;
	    velocity = new Vector2(0, 0);
	    secondaryVelocity = new Vector2(0, 0);
	    angularVelocity = 0;
	    setMapPosition(row, col);
	    setRotation(direction);
	    reloadTime = 0;
	    bulletCount = 0;
	    markedForNextLevel = false;
	    if (!AbstractVehicle.vehicleList.contains(this)) vehicleList.add(this);
	    initializeHitbox();
	}

	protected void initializeStats() {
		stats.addStat("Damage", 35);
		stats.addStat("Spread", 60);
		stats.addStat("Accuracy", 70);
		stats.addStat("Stability", 50);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 75);
		stats.addStat("Lifetime", 60);
		stats.addStat("Fire Rate", 30);
		stats.addStat("Max Projectile", 3);

		stats.addStat("Max Health", 100);
		health = 100;
		stats.addStat("Armor", 15);

		stats.addStat("Traction", 100); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 130);
		stats.addStat("Angular Acceleration", 130);

		stats.addStat("Projectile Durability", 1);
	}

	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	protected void initializeExpLevel() {
		level = 1;
		exp = 0;
		totalExp = 0;
		nextExp = 4 + (level * 2) + (int)(Math.pow(level, 1.5) / 4);
		upgradesLeft = 0;
	}

	public void setMapPosition(int row, int col) {
		int x = col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2; // center of tile
		int y = row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		super.setPosition(x, y);
	}

	/**
	 * updates the velocity based on user input and tank stats
	 */
	public void act(float delta) {
		if (isDestroyed())
			return;
		if (player.controls.downPressed()) {
			accelerateBackward(delta);
		} else if (player.controls.upPressed()) {
			accelerateForward(delta);
		}
		if (player.controls.leftPressed()) {
			turnLeft(delta);
		} else if (player.controls.rightPressed()) {
			turnRight(delta);
		}
		super.applyFriction(delta);
		super.move(delta);
		// Gun Pointing
		if (player.cursor.getStagePos() != null) {
			super.pointGunToPoint(player.cursor.getStagePos().x, player.cursor.getStagePos().y);
		}
		// Firing
		if (player.controls.firePressed() && reloadTime < 0.01 && bulletCount < stats.getStatValue("Max Projectile")) {
			// if almost done reloading, allow for rounding
			int fireRate = stats.getStatValue("Fire Rate");
			setReloadTime(1.75f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
			shoot();
		} else if (player.controls.subPressed() && reloadTime < 0.01) {
			if (getCurrentSubWeapon() != null) {
				getCurrentSubWeapon().shoot(this);
				getCurrentSubWeapon().addAmmo(-1);
				if (getCurrentSubWeapon().getAmmo() <= 0) {
					subWeapons.removeCurrent();
				}
			}
		} else if (reloadTime > 0) {
			reloadTime -= delta;
			if (reloadTime <= 0) reloadTime = 0;
		}
		//Switching Subs
		if (player.controls.subRightPressed() && !subRightHeld) {
			if (getNextSubWeapon() != null) subWeapons.cycleBy(1);
		}
		else if (player.controls.subLeftPressed() && !subLeftHeld) {
			if (getPrevSubWeapon() != null) subWeapons.cycleBy(-1);
		}
		subRightHeld = player.controls.subRightPressed();
		subLeftHeld = player.controls.subLeftPressed();
		
		if (!markedForNextLevel) {
			markedForNextLevel = isMarkedForNextLvl();
		}
		playSoundEffects();
	}
	
	public void setReloadTime(float time) {
		reloadTime = lastReloadTime = time;
		if (lastReloadTime == 0) lastReloadTime = 1.0f;
	}

	public void playSoundEffects() {
		if (!engineSoundOn) {
			engine_sound.play();
			engine_sound.loop();
			engineSoundOn = true;
		}
		
		float combinedSpeed = getVelocity().len() + Math.abs(getAngularVelocity());
		if (combinedSpeed > 0 && !treadSoundOn) {
			tread_sound.play();
			tread_sound.loop();
			tread_sound.setVolume((combinedSpeed) / (combinedSpeed + SOME_CONSTANT));
			treadSoundOn = true;
		} else if (treadSoundOn) {
			tread_sound.setVolume((combinedSpeed) / (combinedSpeed + SOME_CONSTANT));
		}

		if (combinedSpeed <= THRESH) {
			treadSoundOn = false;
			tread_sound.stop();
		}
		//System.out.println(combinedSpeed);
	}

	@Override
	public void draw(Batch batch, float a) {
		//if (getCurrentSubWeapon() != null) drawSubWeapon(batch, a);
		treadTexture = player.custom.getTexture("tread");
		gunTexture = player.custom.getTexture("gun");
		super.draw(batch, a);
	}
	
	public void drawSubWeapon(Batch batch, float a) {
		//update textures from customization
		Texture tex = getCurrentSubWeapon().getTexture();
		float scale = Math.min(Math.min(1.0f, 128.0f / tex.getWidth()), 128.0f / tex.getHeight());
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY() + 256,
					tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(), scale, scale,
					0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}

	public void shoot() {
		Vector2 v = new Vector2(TANK_GUN_LENGTH, 0);
		float randomAngle = randomShootAngle();
		v.setAngle(getGunRotation());
		getStage().addActor(
				new Bullet(this, createBulletStats(), getX() + v.x, getY() + v.y, super.gunRotation + randomAngle));
		applySecondaryForce(30.0f * (float) Math.sqrt(stats.getStatValue("Projectile Speed")), getGunRotation() + 180);
		shoot_sound.play();
		
		getStage().addActor(new Moose(getX(), getY()));
	}

	public void switchWeapon(int direction) {
		subWeapons.cycleBy(direction);
	}
	
	public SubWeapon getCurrentSubWeapon() {
		return subWeapons.getCurrent();
	}
	
	public SubWeapon getNextSubWeapon() {
		return subWeapons.getNext();
	}
	
	public SubWeapon getPrevSubWeapon() {
		return subWeapons.getPrevious();
	}
	
	public void pickUpSubWeapon(SubWeaponItem item) {
		SubWeapon sub = item.getSubWeapon();
		getStage().addActor(new MovingText("+" + sub.getAmmo() + " " + sub.getName(), Color.WHITE, 1.5f, new Vector2(0, 200), getX(), getY()));
		int index = subWeapons.indexOf(sub);
		if (index == -1) {
			subWeapons.addAtCurrent(sub);
		}
		else {
			subWeapons.get(index).addAmmo(sub.getAmmo());
			subWeapons.setIndex(index);
		}
		item.destroy();
	}

	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(angle);
		f[0] = x + v.x;
		f[1] = y + v.y;
		v.rotate(180 - 2 * angle);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(2 * angle);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(180 - 2 * angle);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}

	public boolean isMarkedForNextLvl() {
		PortalTile portal = ((Level) getStage()).getMap().getPortalTile();
		int[] currentTile = ((Level) getStage()).getMap().getTileAt(getX(), getY());
		if (currentTile[0] == portal.getRow() && currentTile[1] == portal.getCol()) {
			return true;
		}
		return false;
	}

	public boolean remove() {
		if (super.remove()) {
			if (tread_sound != null)
				tread_sound.dispose();
			if (engine_sound != null)
				engine_sound.dispose();
			if (shoot_sound != null)
				shoot_sound.stop();
			return true;
		}
		return false;
	}
	
	@Override
	/**
	 * From the Collidable interface. The checkCollision method handles all
	 * collisions to this object. This is handled differently for each subclass.
	 * Uses testHitbox to check collisions.
	 * 
	 * @param other
	 *            The other objects this object may collide with
	 */
	public void checkCollisions(ArrayList<Collidable> other) {
		collisions.clear(); // remove collisions calculated from a different frame
		float[] testVertices = testHitbox.getVertices(); // vertices of this instance's hitbox
		// check each Collidable object against this instance
		for (Collidable c : other) {
			
			if (c instanceof AbstractMapTile || c instanceof AbstractVehicle) {
				float[] cTestVertices = c.getHitbox().getVertices(); // vertices of a Collidable object that may collide
																		// with this instance
				for (int i = 0; i < testVertices.length / 2; i++) {
					// check for wall collision by checking if the corners of this instance are
					// contained within another Collidable object
					if (c.getHitbox().contains(testVertices[i * 2], testVertices[i * 2 + 1])) {
						// generate the wall associated with the collision
						Vector2 wall = CollisionEvent.getWallVector(testHitbox, c.getHitbox(), i * 2);
						// create new wall collision event
						collisions.add(new CollisionEvent(c, CollisionEvent.WALL_COLLISION, wall,
								new Vector2(testVertices[i * 2], testVertices[i * 2 + 1])));
					}
					// check for corner collision by checking if the corners of another Collidable
					// object are contained within this instance
					if (testHitbox.contains(cTestVertices[i * 2], cTestVertices[i * 2 + 1])) {
						// create new corner collision event
						Vector2 wall = CollisionEvent.getWallVector(c.getHitbox(), testHitbox, i * 2);
						collisions.add(new CollisionEvent(c, CollisionEvent.CORNER_COLLISION, wall,
								new Vector2(cTestVertices[i * 2], cTestVertices[i * 2 + 1])));
					}
				}
			}
			if (c instanceof AbstractItem) {
				if (c instanceof SubWeaponItem) {
					float[] cTestVertices = c.getHitbox().getVertices();
					for (int i = 0; i < testVertices.length / 2; i++) {
						if ((c.getHitbox().contains(testVertices[i * 2], testVertices[i * 2 + 1])) || 
						(testHitbox.contains(cTestVertices[i * 2], cTestVertices[i * 2 + 1]))){
							pickUpSubWeapon((SubWeaponItem)c);
							break;
						}
					}
				}

				if(c instanceof RepairBoxItem){
					float[] cTestVertices = c.getHitbox().getVertices();
					for (int i = 0; i < testVertices.length / 2; i++) {
						if ((c.getHitbox().contains(testVertices[i * 2], testVertices[i * 2 + 1])) ||
								(testHitbox.contains(cTestVertices[i * 2], cTestVertices[i * 2 + 1]))){
							if(this.getHealth() < this.getMaxHealth()) {
								heal(((RepairBoxItem) c), ((RepairBoxItem) c).restoreHealth(this));
								((RepairBoxItem) c).destroy();
							}
							break;
						}
					}
				}
			}
		}

	}
	
	public int getLevel() {
		return level;
	}
	
	public int getCurrentExp() {
		return exp;
	}
	
	public int getTotalExp() {
		return totalExp;
	}
	
	public int getNextExp() {
		return nextExp;
	}
	
	public void gainExp(int expGained) {
		this.exp += expGained;
		totalExp += expGained;
		if (getStage() != null)
			getStage().addActor(new MovingText("+" + expGained + " EXP", Color.WHITE, 1.5f, new Vector2(0, 120),
					getX() + (float)(100f * Math.random()) - 50f,
					getY() + (float)(100f * Math.random()) - 50f, 0.8f));
		while (exp >= nextExp) {
			if (getStage() != null)
				getStage().addActor(new MovingText("LEVEL UP!", Color.WHITE, 1.5f, new Vector2(0, 200),
						getX() + (float)(100f * Math.random()) - 50f,
						getY() + (float)(100f * Math.random()) - 50f, 1.3f));
			level++;
			exp -= nextExp;
			nextExp = 4 + (level * 2) + (int)(Math.pow(level, 1.5) / 4);
			if (upgradesLeft == 0) {
				selectableUpgrades = Upgrade.getRandomUpgrade(4);
			}
			upgradesLeft++;
		}
	}
	
	public ArrayList<Upgrade> getSelectableUpgrades() {
		return selectableUpgrades;
	}
	
	public void selectUpgrade(int choice) {
		addUpgrade(selectableUpgrades.get(choice));
		upgradesLeft--;
		if (upgradesLeft > 0) {
			selectableUpgrades = Upgrade.getRandomUpgrade(4);
		}
		else {
			selectableUpgrades.clear();
		}
		
	}
	
	public void addUpgrade(Upgrade upgrade) {
		stats.mergeStats(upgrade.getChanges());
	}
	
	@Override
	public ArrayList<Collidable> getNeighbors(){
		ArrayList<Collidable> neighbors = super.getNeighbors();
		neighbors.addAll(AbstractItem.items);
		return neighbors;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public Player getPlayer() {
		return player;
	}

	public float getReloadTime() {
		return reloadTime;
	}
	
	public float getLastReloadTime() {
		if (lastReloadTime <= 0) lastReloadTime = 1.0f;
		return lastReloadTime;
	}

	public boolean isReadyForNextLevel() {
		return markedForNextLevel;
	}

	@Override
	public String getTeam() {
		return "PLAYERS";
	}
}
