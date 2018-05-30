package com.tank.actor.vehicles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.projectiles.Bullet;
import com.tank.game.Player;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.subweapons.SubWeapon;
import com.tank.utils.Assets;

public class PlayerTank extends FreeTank {
	/**
	 * used for spawning bullets the correct distance away from Vehicle's center
	 */
	public static final int TANK_GUN_LENGTH = 135; // probably redundant, check free tank
	private static float angle;	//angle between diagonal of rectangle and its base
	
	protected Player player;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;

	private final float TREAD_VOLUME = 0.2f;
	private final float ENGINE_VOLUME = 0.6f;
    private final float SHOOT_VOLUME = 0.6f;

	private MediaSound engine_sound = new MediaSound(Assets.manager.get(Assets.tank_engine), ENGINE_VOLUME);
	private MediaSound tread_sound = new MediaSound(Assets.manager.get(Assets.tank_tread), TREAD_VOLUME);
	private MediaSound shoot_sound = new MediaSound(Assets.manager.get(Assets.bullet_fire), SHOOT_VOLUME);

	private boolean treadSoundOn = false;
	private boolean engineSoundOn = false;

	private final int SOME_CONSTANT = 1000;
	private final int THRESH = 20;

	protected float reloadTime;

	public PlayerTank(int playerNumber, Player player) {
		super(0, 0); // defaults
		this.playerNumber = playerNumber;
		this.player = player;
		initializeStats();
		reloadTime = 0;
		selectedWeapon = 0;
		super.setGunOffsetX(-12);
		super.setGunPivotX(treadTexture.getWidth() / 2 + super.getGunOffsetX());
		setWidth(80);
		setHeight(90);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}

	public PlayerTank(int playerNumber, Player player, float x, float y) {
		super(x, y);
		this.playerNumber = playerNumber;
		this.player = player;
		initializeStats();
		reloadTime = 0;
		selectedWeapon = 0;
		super.setGunOffsetX(-12);
		super.setGunPivotX(treadTexture.getWidth() / 2 - super.getGunOffsetX());
		setWidth(80);
		setHeight(90);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}

	protected void initializeStats() {
		stats.addStat("Damage", 35);
		stats.addStat("Spread", 40);
		stats.addStat("Accuracy", 50);
		stats.addStat("Stability", 50);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 75);
		stats.addStat("Lifetime", 80);
		stats.addStat("Fire Rate", 30);
		stats.addStat("Max Projectile", 2);
		
		maxHealth = health = 100;
		stats.addStat("Armor", 15);
		
		stats.addStat("Traction", 100); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 120);
		stats.addStat("Angular Acceleration", 120);
		
		stats.addStat("Projectile Durability", 1);
	}

	public void setMapPosition(int row, int col) {
		int x = col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		int y = row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		super.setPosition(x, y);
	}

	/**
	 * updates the velocity based on user input and tank stats
	 */
	public void act(float delta) {
		if(isDestroyed()) return;
		if (player.controls.downPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration") * 10f, 180 + getRotation());
		} else if (player.controls.upPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration")  * 10f, getRotation());
		}
		if (player.controls.leftPressed()) {
			super.applyAngularForce(delta * stats.getStatValue("Angular Acceleration")  * 2.5f);
		} else if (player.controls.rightPressed()) {
			super.applyAngularForce(-1 * delta * stats.getStatValue("Angular Acceleration")  * 2.5f);
		}
		super.applyFriction(delta);
		super.move(delta);
		// Gun Pointing
		if (player.cursor.getStagePos() != null) {
			super.pointGunToPoint(player.cursor.getStagePos().x, player.cursor.getStagePos().y);
		}
		// Firing
		if (player.controls.firePressed() && reloadTime < 0.01 && bulletCount < stats.getStatValue("Max Projectile")) { // if almost done reloading, allow for rounding
			int fireRate = stats.getStatValue("Fire Rate");
			reloadTime = 2.0f * (1.0f - ((float)(fireRate) / (fireRate + 60)));
			shoot();
			bulletCount++;
		} else if (reloadTime > 0) {
			reloadTime -= delta;
		}

		playSoundEffects();
	}

    public void playSoundEffects() {
        if (!engineSoundOn) {
            engine_sound.play();
            engine_sound.loop();
            engineSoundOn = true;
        }

        if (getVelocity().len() > 0 && !treadSoundOn) {
            tread_sound.play();
            tread_sound.loop();
            tread_sound.setVolume((getVelocity().len()) / (getVelocity().len() + SOME_CONSTANT));
            treadSoundOn = true;
        }
        else if (treadSoundOn) {
            tread_sound.setVolume((getVelocity().len()) / (getVelocity().len() + SOME_CONSTANT));
        }

        if (getVelocity().len() <= THRESH) {
            treadSoundOn = false;
            tread_sound.stop();
        }
    }

	@Override
	public void draw(Batch batch, float a) {
		treadTexture = player.custom.getTexture("tread");
		gunTexture = player.custom.getTexture("gun");
		super.draw(batch, a);
	}

	public void shoot() {
		Vector2 v = new Vector2(TANK_GUN_LENGTH, 0);
		float randomAngle = randomShootAngle();
		v.setAngle(getGunRotation());
		getStage().addActor(new Bullet(this, createBulletStats(), getX() + v.x, getY() + v.y, super.gunRotation + randomAngle));
		shoot_sound.play();
	}
	
	public float randomShootAngle() {
		float spreadRange = 45f * (1.0f - (stats.getStatValue("Spread") / (stats.getStatValue("Spread") + 100.0f)));
		double accuracy = 1.0f + 0.05f * (float)Math.sqrt(stats.getStatValue("Accuracy"));
		accuracy *= 1.0f - (getVelocity().len() / (getVelocity().len() + 1000.0f));
		float randomAngle = spreadRange * (float)Math.pow(Math.random(), accuracy);
		if (Math.random() < 0.5) randomAngle *= -1;
		return randomAngle;
	}
	
	public Stats createBulletStats() {
		Stats bulletStats = new Stats();
		bulletStats.addStat("Damage", stats.getStatValue("Damage"));
		bulletStats.addStat("Projectile Speed", (int)(75 * Math.sqrt(stats.getStatValue("Projectile Speed"))));
		bulletStats.addStat("Projectile Durability", stats.getStatValue("Projectile Durability"));
		bulletStats.addStat("Max Bounce", stats.getStatValue("Max Bounce"));
		return bulletStats;
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(angle);
		f[0] = x+ v.x;
		f[1] = y +v.y;
		v.rotate(180-2*angle);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(2*angle);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(180-2*angle);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public float getReloadTime() {
		return reloadTime;
	}
	
	@Override
	public String getTeam() {
		return "PLAYERS";
	}
}
