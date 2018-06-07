package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Boomerang;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class BoomerangSubWeapon extends SubWeapon{
	/**
	 * the texture of the subweapon
	 */
	private static Texture boomerangTexture = Assets.manager.get(Assets.boomerang_icon);
	/**
	 * the volume of the shoot sound, out of 1.0
	 */
	private static final float SHOOT_VOLUME = 6.0f;
	/**
	 * the sound used upon shooting
	 */
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.boomerang_fire), SHOOT_VOLUME);
	
	public BoomerangSubWeapon(int ammo) {
		super("Boomerang", boomerangTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		float randomAngle = source.randomShootAngle() * 0.25f;
		v.setAngle(source.getGunRotation());
		source.getStage().addActor(
				new Boomerang(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation() + randomAngle, source.getAngularVelocity() * 1.2f));
		source.applySecondaryForce(12.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(3.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}

	@Override
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 1.2) + 10);
		stats.addStat("Projectile Speed", (int)(85 * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") + 2);
		stats.addStat("Max Bounce", source.getStatValue("Max Bounce") + 1);
		stats.addStat("Lifetime", source.getStatValue("Lifetime") * 2);
		return stats;
	}
}
