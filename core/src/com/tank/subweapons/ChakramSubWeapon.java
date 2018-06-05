package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Chakram;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ChakramSubWeapon extends SubWeapon {
	
	private static Texture chakramTexture = Assets.manager.get(Assets.chakram_icon);
	
	public ChakramSubWeapon(int ammo) {
		super("Chakram", chakramTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		float randomAngle = source.randomShootAngle() * 0.4f;
		v.setAngle(source.getGunRotation());
		source.getStage().addActor(
				new Chakram(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation() + randomAngle));
		source.applySecondaryForce(10.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(3.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 1.1) + 5);
		stats.addStat("Projectile Speed", (int)(90 * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 2 + 1);
		stats.addStat("Max Bounce", (int)(source.getStatValue("Max Bounce") * 2.5) + 3);
		stats.addStat("Lifetime", (int)(source.getStatValue("Lifetime") * 2.5));
		return stats;
	}
}
