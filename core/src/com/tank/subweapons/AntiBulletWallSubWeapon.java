package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.AntiBulletWall;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class AntiBulletWallSubWeapon extends SubWeapon{
	
	private static Texture antiBulletWallTexture = Assets.manager.get(Assets.bulletWall);
	private static final float SHOOT_VOLUME = 0.6f;
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.bulletWall_fire), SHOOT_VOLUME);
	
	public AntiBulletWallSubWeapon(int ammo) {
		super("Anti-Bullet Wall", antiBulletWallTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		v.setAngle(source.getGunRotation());
		source.getStage().addActor(new AntiBulletWall(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation()));
		source.applySecondaryForce(30.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(5.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Projectile Speed", (int)(70 * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 15 + 10);
		stats.addStat("Lifetime", source.getStatValue("Lifetime") * 2 + 30);
		return stats;
	}
}
