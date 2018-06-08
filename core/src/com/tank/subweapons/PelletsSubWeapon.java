/**
 * @author Daniel Pham
 * The subweapon class for the projectile- pellet
 * Description: Implements the given subweapon projectile class and spawns it 
 * correctly onto the map, giving the 'projectile' its necessary information 
 * to spawn, as well as stats, playing the necessary projectile sound, setting 
 * the player's fire rate, and giving recoil to the player upon firing. If 
 * necessary, gives the spawned projectile a degree of randomness and/or 
 * spawns multiple projectiles of the given type.
 */
package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Pellet;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class PelletsSubWeapon extends SubWeapon{
	/**
	 * the texture of the subweapon
	 */
	private static Texture pelletsTexture = Assets.manager.get(Assets.pellet_icon);
	/**
	 * the volume of the shoot sound, out of 1.0
	 */
	private static final float SHOOT_VOLUME = 0.6f;
	/**
	 * the sound used upon shooting
	 */
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.pellet_fire), SHOOT_VOLUME);
	
	public PelletsSubWeapon(int ammo) {
		super("Pellet Shot", pelletsTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		Vector2 v = new Vector2(PlayerTank.TANK_GUN_LENGTH, 0);
		v.setAngle(source.getGunRotation());
		int count = 6 + source.getStatValue("Max Projectile") * 4;
		for (int i = 0; i < count; i++) {
			float randomAngle = source.randomShootAngle() * 1.5f;
			source.getStage().addActor(
					new Pellet(source, createStats(source), source.getX() + v.x, source.getY() + v.y, source.getGunRotation() + randomAngle));
		}
		source.applySecondaryForce(15.0f * (float) Math.sqrt(source.getStatValue("Projectile Speed")), source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(3.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}

	@Override
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 0.5) + 2);
		stats.addStat("Projectile Speed", (int)(((Math.random() * 50) + 100) * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", Math.max(source.getStatValue("Projectile Durability") / 2, 1));
		stats.addStat("Lifetime", source.getStatValue("Lifetime"));
		return stats;
	}
}
