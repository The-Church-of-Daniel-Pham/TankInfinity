package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.projectiles.LandMine;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class LandMineSubWeapon extends SubWeapon{
	
	private static Texture landMineTexture = Assets.manager.get(Assets.landMine);
	
	public LandMineSubWeapon(int ammo) {
		super("Land Mine", landMineTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		source.getStage().getRoot().addActorAt(1,
				new LandMine(source, createStats(source), source.getX(), source.getY()));
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(4.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 1.5));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 3);
		stats.addStat("Lifetime", source.getStatValue("Lifetime") * 10);
		return stats;
	}
	
}