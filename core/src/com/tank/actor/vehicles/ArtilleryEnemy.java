package com.tank.actor.vehicles;

import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.ui.ArtilleryMark;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtilleryEnemy extends PeashooterEnemy{
	
	public ArtilleryEnemy(float x, float y, int level) {
		super(x, y, level);
		tankTexture = Assets.manager.get(Assets.enemy_artillery_fixed);
		
		reverseTimeChanges = 0.2f;
		distanceForShoot = 10f;
		distanceForTrack = 15f;
		reloadTime = 5.0f;
		rotateThreshold = 6.0f;
		onTileThreshold = 80;
		gunLength = 110;
		diagonalLength = 120;
		mass = 250f;
		expGive = (int)Math.pow(2 + level, 1.1);
	}
	
	protected void initializeStats() {
		stats.addStat("Damage", 55);
		stats.addStat("Spread", 10);
		stats.addStat("Accuracy", 40);
		stats.addStat("Stability", 10);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 50);
		stats.addStat("Lifetime", 70);
		stats.addStat("Fire Rate", 20);
		stats.addStat("Max Projectile", 6);
		
		stats.addStat("Max Health", 70);
		health = 80;
		stats.addStat("Armor", 25);
		
		stats.addStat("Traction", 170);
		stats.addStat("Acceleration", 120);
		stats.addStat("Angular Acceleration", 150);
		
		stats.addStat("Projectile Durability", 3);
	}
	
	protected Stats levelStats(int levelNum) {
		Stats levelUps = new Stats();
		
		levelUps.addStat("Damage", (int)(1.5 * Math.pow(levelNum - 1, 1.3)));
		levelUps.addStat("Spread", (int)(1.3 * Math.pow(levelNum - 1, 0.7)));
		levelUps.addStat("Accuracy", (int)(2 * Math.pow(levelNum - 1, 0.5)));
		levelUps.addStat("Stability", (int)(1 * Math.pow(levelNum - 1, 0.3)));
		//levelUps.addStat("Max Bounce", (int)(0 * Math.pow(levelNum - 1, 0.7)));
		//levelUps.addStat("Lifetime", (int)(0 * Math.pow(levelNum - 1, 0.7)));
		levelUps.addStat("Fire Rate", (int)(1.5 * Math.pow(levelNum - 1, 0.8)));
		
		levelUps.addStat("Max Health", (int)(5 * Math.pow(levelNum - 1, 1.1)));
		levelUps.addStat("Armor", (int)(2 * Math.pow(levelNum - 1, 1.05)));
		
		//levelUps.addStat("Projectile Durability", (int)(0 * Math.pow(levelNum - 1, 0.75)));
		
		return levelUps;
	}
	
	@Override
	public void honeInMode(float delta) {
		if (isOnPath()) setNextTarget(path.removeFirst());
		float distanceToTarget = getDistanceTo(target);
		if (distanceToTarget <= AbstractMapTile.SIZE * distanceForTrack) {
			if (distanceToTarget >= AbstractMapTile.SIZE * distanceForShoot) {
				timeSinceLastPathfind += delta;
				if (timeSinceLastPathfind >= 20f) {
					endTargetTile = getTileAt(target.getX(), target.getY());
					requestPathfinding();
				}
				else if (path.isEmpty() || onTile(endTargetTile) || targetPos == null) {
					endTargetTile = getTileAt(target.getX(), target.getY());
					requestPathfinding();
				}
				moveByTargetTile(delta);
			}
			else {
				honeInMode = false;
				attackMode = true;
			}
		}
		else {
			target = null;
			honeInMode = false;
		}
	}
	
	@Override
	public void attackMode(float delta) {
		float distanceToTarget = getDistanceTo(target);
		if (distanceToTarget < AbstractMapTile.SIZE * distanceForShoot) {
			if (reversing) {
				backingUp(delta);
			}
			else if (forwarding) {
				accelerateForward(delta);
				reverseTime += delta;
				if (reverseTime >= reverseTimeChanges + (reverseTimeThreshold / 3)) {
					forwarding = false;
					reverseTime = -delta;
				}
				
			}
			else if (!rotateTowardsTarget(delta, target.getX(), target.getY())) {
				if (cooldownLastShot <= 0f) {
					shoot();
					int fireRate = stats.getStatValue("Fire Rate");
					cooldownLastShot = reloadTime * (1.0f - ((float)(fireRate) / (fireRate + 60)));
				}
			}
		}
		else {
			if (distanceToTarget <= AbstractMapTile.SIZE * distanceForTrack) {
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
	
	public boolean rotateTowardsTarget(float delta, float x, float y) {
		float targetRotation = (float) Math.toDegrees(Math.atan2((y - getY()), (x - getX())));
		float rotationDifference = targetRotation - getRotation();
		while (rotationDifference < -180f) {
			rotationDifference += 360f;
		}
		while (rotationDifference > 180f) {
			rotationDifference -= 360f;
		}
		int direction = 0;
		if (rotationDifference > 20f) direction = 1;
		else if (rotationDifference < -20f) direction = -1;
		
		if (direction == 1) turnLeft(delta); else if (direction == -1) turnRight(delta);
		return (direction != 0);
	}
	
	public void shoot() {
		float x = target.getX() + (randomShootAngle() * 20f);
		float y = target.getY()  + (randomShootAngle() * 20f);
		getStage().addActor(new ArtilleryMark(this, createBulletStats(), 3.0f, x, y));
		applySecondaryForce(10.0f * (float) Math.sqrt(stats.getStatValue("Projectile Speed")), getRotation() + 180);
		shoot_sound.play();
	}
	
	@Override
	public Stats createBulletStats() {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(getStatValue("Damage")));
		stats.addStat("Explosion Size", 640);
		stats.addStat("Lifetime", 15);
		return stats;
	}
}
