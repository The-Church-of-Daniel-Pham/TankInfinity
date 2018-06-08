/**
 * @author Daniel Pham
 * The subweapon class for the projectile- Artillery
 * Description: Implements the given subweapon projectile class and spawns it 
 * correctly onto the map, giving the 'projectile' its necessary information 
 * to spawn, as well as stats, playing the necessary projectile sound, setting 
 * the player's fire rate, and giving recoil to the player upon firing. If 
 * necessary, gives the spawned projectile a degree of randomness and/or 
 * spawns multiple projectiles of the given type.
 */
package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.ui.ArtilleryMark;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtillerySubWeapon extends SubWeapon{
	/**
	 * the texture of the subweapon
	 */
	private static Texture artilleryShell = Assets.manager.get(Assets.artillery_icon);
	/**
	 * the volume of the shoot sound, out of 1.0
	 */
	private static final float SHOOT_VOLUME = 0.6f;
	/**
	 * the sound used upon shooting
	 */
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.artillery_fire), SHOOT_VOLUME);
	
	public ArtillerySubWeapon(int ammo) {
		super("Artillery", artilleryShell, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		source.getStage().addActor(
				new ArtilleryMark(source, createStats(source), 3.0f, source.getPlayer().cursor.getStagePos().x,
						source.getPlayer().cursor.getStagePos().y));
		source.applySecondaryForce(150f, source.getGunRotation() + 180);
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(4.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}

	@Override
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 2.5) + 20);
		stats.addStat("Explosion Size", 640);
		stats.addStat("Lifetime", 15);
		return stats;
	}
}
