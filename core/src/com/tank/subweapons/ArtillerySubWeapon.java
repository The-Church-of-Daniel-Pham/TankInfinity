package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.ui.ArtilleryMark;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtillerySubWeapon extends SubWeapon{
	
	private static Texture artilleryShell = Assets.manager.get(Assets.artillery_icon);
	
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
		source.setReloadTime(3.0f * (1.0f - ((float) (fireRate) / (fireRate + 60))));
	}
	
	public Stats createStats(PlayerTank source) {
		Stats stats = new Stats();
		stats.addStat("Damage", (int)(source.getStatValue("Damage") * 2.5) + 20);
		stats.addStat("Explosion Size", 640);
		stats.addStat("Lifetime", 15);
		return stats;
	}
}
