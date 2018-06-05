package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Pellet;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class PelletsSubWeapon extends SubWeapon{
	
	private static Texture pelletsTexture = Assets.manager.get(Assets.pellet_icon);
	
	public PelletsSubWeapon(int ammo) {
		super("Pellet Shot", pelletsTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		v.setAngle(source.getGunRotation());
		for (int i = 0; i < 10; i++) {
			float randomAngle = source.randomShootAngle() * 1.5f;
			source.getStage().addActor(
					new Pellet(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation() + randomAngle));
		}
		source.applySecondaryForce(15.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(3.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 0.6) + 5);
		stats.addStat("Projectile Speed", (int)(((Math.random() * 50) + 100) * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", Math.max(source.getStatValue("Projectile Durability") / 2, 1));
		stats.addStat("Lifetime", source.getStatValue("Lifetime"));
		return stats;
	}
}
