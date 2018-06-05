package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Laser;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class LaserSubWeapon extends SubWeapon{
	
	private static Texture laserTexture = Assets.manager.get(Assets.laser_icon);
	
	public LaserSubWeapon(int ammo) {
		super("Laser", laserTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		v.setAngle(source.getGunRotation());
		source.getStage().addActor(
				new Laser(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation()));
		source.applySecondaryForce(30.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(2.5f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 1.2) + 10);
		stats.addStat("Projectile Speed", (int)(300 * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 2 + 2);
		stats.addStat("Max Bounce", source.getStatValue("Max Bounce") * 12);
		stats.addStat("Lifetime", source.getStatValue("Lifetime") / 3);
		return stats;
	}
}
