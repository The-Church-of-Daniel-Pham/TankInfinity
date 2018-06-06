package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Rocket;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class RocketSubWeapon extends SubWeapon {
	
	private static Texture rocketTexture = Assets.manager.get(Assets.rocket_icon);
	private static final float SHOOT_VOLUME = 0.6f;
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.rocket_fire), SHOOT_VOLUME);
	
	public RocketSubWeapon(int ammo) {
		super("Rocket", rocketTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		float randomAngle = source.randomShootAngle() * 0.3f;
		v.setAngle(source.getGunRotation());
		source.getStage().addActor(
				new Rocket(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation() + randomAngle));
		source.applySecondaryForce(60.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(5.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 2) + 30);
		stats.addStat("Projectile Speed", (int)(60 * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 2 + 4);
		stats.addStat("Lifetime", source.getStatValue("Lifetime") * 4);
		return stats;
	}
}
