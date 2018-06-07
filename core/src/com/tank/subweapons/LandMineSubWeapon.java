package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.projectiles.LandMine;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class LandMineSubWeapon extends SubWeapon{
	/**
	 * the texture of the subweapon
	 */
	private static Texture landmineTexture = Assets.manager.get(Assets.landmine_icon);
	/**
	 * the volume of the shoot sound, out of 1.0
	 */
	private static final float SHOOT_VOLUME = 0.6f;
	/**
	 * the sound used upon shooting
	 */
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.landmine_deploy), SHOOT_VOLUME);
	
	public LandMineSubWeapon(int ammo) {
		super("Land Mine", landmineTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		source.getStage().addActor(
				new LandMine(source, createStats(source), source.getX(), source.getY()));
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(4.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}

	@Override
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 1.5) + 15);
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 5);
		stats.addStat("Lifetime", source.getStatValue("Lifetime") * 10);
		return stats;
	}
	
}