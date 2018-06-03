package com.tank.actor.vehicles;

import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.projectiles.Rocket;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class RocketEnemy extends BasicEnemy{

	public RocketEnemy(float x, float y, int level) {
		super(x, y, level);
		tankTexture = Assets.manager.get(Assets.fixed_big);
		reverseTimeChanges = 0.3f;
		distanceForShoot = 7f;
		expGive = 3 + level;
	}
	
	protected void initializeStats() {
		stats.addStat("Damage", 60);
		stats.addStat("Spread", 20);
		stats.addStat("Accuracy", 30);
		stats.addStat("Stability", 30);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 50);
		stats.addStat("Lifetime", 80);
		stats.addStat("Fire Rate", 20);
		stats.addStat("Max Projectile", 6);
		
		stats.addStat("Max Health", 80);
		health = 80;
		stats.addStat("Armor", 30);
		
		stats.addStat("Traction", 80);
		stats.addStat("Acceleration", 100);
		stats.addStat("Angular Acceleration", 100);
		
		stats.addStat("Projectile Durability", 3);
	}
	
	public void attackMode(float delta) {
		float distanceToTarget = getDistanceTo(target);
		if (distanceToTarget < AbstractMapTile.SIZE * distanceForShoot && hasLineOfSight(target.getX(), target.getY())) {
			if (reversing) {
				backingUp(delta);
			}
			else if (forwarding) {
				accelerateForward(delta);
				reverseTime += delta;
				if (reverseTime >= reverseTimeChanges) {
					forwarding = false;
					reverseTime = -delta;
				}
				
			}
			else if (!rotateTowardsTarget(delta, target.getX(), target.getY())) {
				if (cooldownLastShot <= 0f) {
					shoot();
					int fireRate = stats.getStatValue("Fire Rate");
					cooldownLastShot = 6.0f * (1.0f - ((float)(fireRate) / (fireRate + 60)));
				}
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
	
	public void shoot() {
		Vector2 v = new Vector2(160, 0);
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
