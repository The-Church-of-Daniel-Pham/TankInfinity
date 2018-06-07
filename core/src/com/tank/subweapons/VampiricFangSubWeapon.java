package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.VampiricFang;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class VampiricFangSubWeapon extends SubWeapon {
	/**
	 * the texture of the subweapon
	 */
	private static Texture fangTexture = Assets.manager.get(Assets.fang_icon);
	/**
	 * the volume of the shoot sound, out of 1.0
	 */
	private static final float SHOOT_VOLUME = 0.6f;
	/**
	 * the sound used upon shooting
	 */
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.fang_shoot), SHOOT_VOLUME);
	
	public VampiricFangSubWeapon(int ammo) {
		super("Vampiric Fang", fangTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		v.setAngle(source.getGunRotation());
		source.getStage().addActor(
				new VampiricFang(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation()));
		source.applySecondaryForce(12.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(2.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}

	@Override
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") / 2));
		stats.addStat("Projectile Speed", (int)(200 * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") + 1);
		stats.addStat("Lifetime", source.getStatValue("Lifetime"));
		return stats;
	}
}
