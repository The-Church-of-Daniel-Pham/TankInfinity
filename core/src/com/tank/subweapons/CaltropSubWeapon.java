package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Caltrop;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class CaltropSubWeapon extends SubWeapon{
	
	private static Texture caltropTexture = Assets.manager.get(Assets.caltrop_icon);
	
	public CaltropSubWeapon(int ammo) {
		super("Caltrops", caltropTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		v.setAngle(source.getGunRotation());
		int count = 10 + source.getStatValue("Max Projectile") * 2;
		for (int i = 0; i < count; i++) {
			float randomAngle = source.randomShootAngle() * 3.0f;
			source.getStage().addActor(
					new Caltrop(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation() + randomAngle));
		}
		source.applySecondaryForce(12.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(3.5f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 0.4) + 3);
		stats.addStat("Projectile Speed", (int)(((Math.random() * 50) + 30) * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", Math.max(source.getStatValue("Projectile Durability") / 2, 1));
		stats.addStat("Lifetime", source.getStatValue("Lifetime") * 2 + 50);
		return stats;
	}
}
