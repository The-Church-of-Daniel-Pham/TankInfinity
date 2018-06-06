package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.projectiles.Moose;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class MooseStampedeSubWeapon extends SubWeapon{
	
	private static Texture mooseTexture = Assets.manager.get(Assets.moose_icon);
	private static final float SHOOT_VOLUME = 6.0f;
	private static MediaSound shootSound = new MediaSound(Assets.manager.get(Assets.moose_shoot), SHOOT_VOLUME);
	
	public MooseStampedeSubWeapon(int ammo) {
		super("Moose Stampede", mooseTexture, ammo);
	}

	@Override
	public void shoot(PlayerTank source) {
		int mooseCount = (source.getStatValue("Max Projectile") * 5) + 10;
		for (int i = 0; i < mooseCount; i++) {
			source.getStage().addActor(
					new Moose(source, createStats(source),source.getPlayer().cursor.getStagePos().x,
							source.getPlayer().cursor.getStagePos().y));
		}
		int fireRate = source.getStatValue("Fire Rate");
		source.setReloadTime(6.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
		shootSound.play();
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 0.5) + 30);
		stats.addStat("Projectile Speed", (int)(((Math.random() * 50) + 75) * Math.sqrt(source.getStatValue("Projectile Speed"))));
		stats.addStat("Projectile Durability", source.getStatValue("Projectile Durability") * 3 + 1);
		return stats;
	}
}
