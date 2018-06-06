package com.tank.actor.vehicles;

import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Rocket;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class BigShotEnemy extends PeashooterEnemy{

	public BigShotEnemy(float x, float y, int level) {
		super(x, y, level);
		tankTexture = Assets.manager.get(Assets.enemy_big_shot_fixed);
		
		reverseTimeChanges = 0.3f;
		distanceForShoot = 7f;
		reloadTime = 5.0f;
		rotateThreshold = 4.0f;
		onTileThreshold = 90;
		gunLength = 100;
		diagonalLength = 110;
		expGive = (int)Math.pow(2 + level, 1.1);
	}
	
	protected void initializeStats() {
		stats.addStat("Damage", 60);
		stats.addStat("Spread", 20);
		stats.addStat("Accuracy", 30);
		stats.addStat("Stability", 30);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 50);
		stats.addStat("Lifetime", 70);
		stats.addStat("Fire Rate", 20);
		stats.addStat("Max Projectile", 6);
		
		stats.addStat("Max Health", 80);
		health = 80;
		stats.addStat("Armor", 30);
		
		stats.addStat("Traction", 150);
		stats.addStat("Acceleration", 120);
		stats.addStat("Angular Acceleration", 100);
		
		stats.addStat("Projectile Durability", 3);
	}
	
	protected Stats levelStats(int levelNum) {
		Stats levelUps = new Stats();
		
		levelUps.addStat("Damage", (int)(1.1 * Math.pow(levelNum - 1, 1.3)));
		levelUps.addStat("Spread", (int)(1.5 * Math.pow(levelNum - 1, 0.7)));
		levelUps.addStat("Accuracy", (int)(2 * Math.pow(levelNum - 1, 0.5)));
		levelUps.addStat("Stability", (int)(3 * Math.pow(levelNum - 1, 0.3)));
		levelUps.addStat("Max Bounce", (int)(0 * Math.pow(levelNum - 1, 0.7)));
		levelUps.addStat("Lifetime", (int)(2 * Math.pow(levelNum - 1, 0.7)));
		levelUps.addStat("Fire Rate", (int)(1 * Math.pow(levelNum - 1, 0.6)));
		
		levelUps.addStat("Max Health", (int)(6 * Math.pow(levelNum - 1, 1.2)));
		levelUps.addStat("Armor", (int)(3 * Math.pow(levelNum - 1, 0.95)));
		
		levelUps.addStat("Projectile Durability", (int)(0.7 * Math.pow(levelNum - 1, 0.75)));
		
		return levelUps;
	}
	
	public void shoot() {
		Vector2 v = new Vector2(gunLength, 0);
		float randomAngle = randomShootAngle() * 0.75f;
		v.setAngle(getRotation());
		getStage().addActor(new Rocket(this, createBulletStats(), getX() + v.x, getY() + v.y, getRotation() + randomAngle));
		applySecondaryForce(50.0f * (float) Math.sqrt(stats.getStatValue("Projectile Speed")), getRotation() + 180);
		shoot_sound.play();
	}
	
	@Override
	public Stats createBulletStats() {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(getStatValue("Damage")));
		stats.addStat("Projectile Speed", (int)(60 * Math.sqrt(getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", getStatValue("Projectile Durability"));
		stats.addStat("Lifetime", getStatValue("Lifetime"));
		return stats;
	}

}
