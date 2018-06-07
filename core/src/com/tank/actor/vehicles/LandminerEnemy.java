package com.tank.actor.vehicles;

import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.projectiles.LandMine;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class LandminerEnemy extends FreePeashooterEnemy {
	
	protected float lastLandmineTry;
	private static final float LANDMINE_VOLUME = 0.6f;
	private static MediaSound landmineSound = new MediaSound(Assets.manager.get(Assets.landmine_deploy), LANDMINE_VOLUME);
	
	public LandminerEnemy(float x, float y, int level) {
		super(x, y, level);
		treadTexture = Assets.manager.get(Assets.enemy_landminer_tread);
		gunTexture = Assets.manager.get(Assets.enemy_landminer_gun);
		distanceForShoot = 5f;
		reloadTime = 4.3f;
	}

	protected void initializeStats() {
		stats.addStat("Damage", 30);
		stats.addStat("Spread", 20);
		stats.addStat("Accuracy", 30);
		stats.addStat("Stability", 80);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 75);
		stats.addStat("Lifetime", 50);
		stats.addStat("Fire Rate", 25);
		stats.addStat("Max Projectile", 6);
		
		stats.addStat("Max Health", 60);
		stats.addStat("Armor", 15);
		
		stats.addStat("Traction", 100);
		stats.addStat("Acceleration", 120);
		stats.addStat("Angular Acceleration", 120);
		
		stats.addStat("Projectile Durability", 1);
	}
	@Override
	public void act(float delta) {
		lastLandmineTry += delta;
		super.act(delta);
	}
	
	@Override
	public void honeInMode(float delta) {
		super.honeInMode(delta);
		if (honeInMode) {
			if (lastLandmineTry >= 0.3f) {
				lastLandmineTry = 0f;
				if (cooldownLastShot <= 0 && Math.random() < .25) {
					dropLandMine();
					landmineSound.play();
				}
			}
		}
	}
	
	@Override
	public void attackMode(float delta) {
		float distanceToTarget = getDistanceTo(target);
		if (distanceToTarget < AbstractMapTile.SIZE * distanceForShoot && hasLineOfSight(target.getX(), target.getY())) {
			boolean rotatingGun = rotateGunTowardsTarget(delta, target.getX(), target.getY());
			if (reversing) {
				backingUp(delta);
			}
			else if (forwarding) {
				accelerateForward(delta);
				reverseTime += delta;
				if (reverseTime >= 0.5f) {
					forwarding = false;
					reverseTime = -delta;
				}
				
			}
			else if (rotatingGun) {
				rotateTowardsTarget(delta, target.getX(), target.getY());
				if (lastLandmineTry >= 0.3f) {
					lastLandmineTry = 0f;
					if (cooldownLastShot <= 0 && Math.random() < .4) {
						dropLandMine();
					}
				}
			}
			
			if (!rotatingGun) {
				if (cooldownLastShot <= 0f) {
					if (lastLandmineTry >= 0.3f && Math.random() < .4) {
						lastLandmineTry = 0f;
						dropLandMine();
					}
					else {
						shoot();
						int fireRate = stats.getStatValue("Fire Rate");
						cooldownLastShot = reloadTime * (1.0f - ((float)(fireRate) / (fireRate + 60)));
					}
				}
				if (!reversing && !forwarding) accelerateForward(delta);
			}
		}
		else {
			if (distanceToTarget <= AbstractMapTile.SIZE * 12) {
				attackMode = false;
				honeInMode = true;
				endTargetTile = getTileAt(target.getX(), target.getY());
				requestPathfinding();
			}
			else {
				attackMode = false;
				patrolling = true;
				selectNewEndTargetTile();
				requestPathfinding();
			}
		}
	}
	
	protected Stats levelStats(int levelNum) {
		Stats levelUps = new Stats();
		
		levelUps.addStat("Damage", (int)(1.4 * Math.pow(levelNum - 1, 1.2)));
		levelUps.addStat("Spread", (int)(2.7 * Math.pow(levelNum - 1, 0.6)));
		levelUps.addStat("Accuracy", (int)(3.5 * Math.pow(levelNum - 1, 0.6)));
		levelUps.addStat("Stability", (int)(6 * Math.pow(levelNum - 1, 0.75)));
		levelUps.addStat("Max Bounce", (int)(0.3 * Math.pow(levelNum - 1, 0.3)));
		levelUps.addStat("Lifetime", (int)(2.5 * Math.pow(levelNum - 1, 0.5)));
		levelUps.addStat("Fire Rate", (int)(2.8 * Math.pow(levelNum - 1, 0.6)));
		
		levelUps.addStat("Max Health", (int)(4.2 * Math.pow(levelNum - 1, 1.1)));
		levelUps.addStat("Armor", (int)(3.5 * Math.pow(levelNum - 1, 0.9)));
		
		levelUps.addStat("Projectile Durability", (int)(0.6 * Math.pow(levelNum - 1, 0.7)));
		
		return levelUps;
	}
	
	public void dropLandMine() {
		Stats mineStats = new Stats();
		mineStats.addStat("Damage", (int)(getStatValue("Damage") * 2));
		mineStats.addStat("Projectile Durability", getStatValue("Projectile Durability") * 4);
		mineStats.addStat("Lifetime", getStatValue("Lifetime") * 5);
		getStage().addActor(new LandMine(this, mineStats, getX(), getY()));
		int fireRate = getStatValue("Fire Rate");
		cooldownLastShot = (reloadTime * 1.5f) * (1.0f - ((float) (fireRate) / (fireRate + 60)));
		landmineSound.play();
	}
}
