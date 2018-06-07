package com.tank.stats;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.tank.utils.Assets;

public class Upgrade {
	private Texture icon;
	private String name;
	private Stats changes;
	private String desc;
	private static ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	
	static {
		/**
		 * Basic Upgrades - 1 Small Increase (only certain stats)
		 */
		//Attack Power increase
		Stats damageIncrease = new Stats();
		damageIncrease.addStat("Damage", 7);
		upgrades.add(new Upgrade("Damage Up", Assets.manager.get(Assets.damage_icon), damageIncrease, "Increase tank damage by small amount"));
		//Armor increase
		Stats armorIncrease = new Stats();
		armorIncrease.addStat("Armor", 7);
		upgrades.add(new Upgrade("Armor Up", Assets.manager.get(Assets.armor_icon), armorIncrease, "Increase tank defense by small amount"));
		//Health increase
		Stats healthIncrease = new Stats();
		healthIncrease.addStat("Max Health", 25);
		upgrades.add(new Upgrade("Health Up", Assets.manager.get(Assets.health_icon), healthIncrease, "Increase max tank health by small amount"));
		//Accuracy increase
		Stats accuracyIncrease = new Stats();
		accuracyIncrease.addStat("Accuracy", 9);
		upgrades.add(new Upgrade("Accuracy Up", Assets.manager.get(Assets.accuracy_icon), accuracyIncrease, "Increase tank accuracy by small amount"));
		//Spread decrease
		Stats spreadDecrease = new Stats();
		spreadDecrease.addStat("Spread", 9);
		upgrades.add(new Upgrade("Spread Down", Assets.manager.get(Assets.spread_icon), spreadDecrease, "Decreases tank bullet spread by small amount"));
		//Stability increase
		Stats stabilityUp = new Stats();
		stabilityUp.addStat("Stability", 9);
		upgrades.add(new Upgrade("Stability Up", Assets.manager.get(Assets.stability_icon), stabilityUp, "Decreases tank's accuracy loss when moving by small amount"));
		//Fire Rate
		Stats fireUp = new Stats();
		fireUp.addStat("Fire Rate", 6);
		upgrades.add(new Upgrade("Fire Rate Up", Assets.manager.get(Assets.fire_rate_icon), fireUp, "Increases tank's rate of fire by small amount"));
		//Lifetime Increase
		Stats lifetimeUp = new Stats();
		lifetimeUp.addStat("Lifetime", 5);
		upgrades.add(new Upgrade("Lifetime Up", Assets.manager.get(Assets.lifetime_icon), lifetimeUp, "Increases the time before a tank's bullet dissapears by a small amount"));
		/**
		 * Conditional Basic Upgrades - 1 Stat increase with 1 smaller increase (for "stronger" stats)
		 */
		//Bounce Count increase (down Attack)
		Stats bounceUp = new Stats();
		bounceUp.addStat("Max Bounce", 1);
		bounceUp.addStat("Armor", -3);
		upgrades.add(new Upgrade("Bounce Up", Assets.manager.get(Assets.bounce_icon), bounceUp, "Increases maximum bounce, but decreases armor by a miniscule amount"));
		//Projectile Speed increase (down Stability)
		Stats projectileSpeedUp = new Stats();
		projectileSpeedUp.addStat("Projectile Speed", 6);
		projectileSpeedUp.addStat("Stability", -2);
		//upgrades.add(new Upgrade("Projectile Speed Up", null, projectileSpeedUp, "Increases projectile speed, but decreases tank stability by a miniscule amount"));
		//Max Projectile increase (down Fire Rate)
		Stats maxProjectileUp = new Stats();
		maxProjectileUp.addStat("Max Projectile", 1);
		maxProjectileUp.addStat("Fire Rate", -3);
		upgrades.add(new Upgrade("Max Projectile Up", Assets.manager.get(Assets.max_projectile_icon), projectileSpeedUp, "Increases maximium projectile amount, but decreases tank fire rate by a miniscule amount"));
		//Acceleration increase (down Armor)
		Stats accelerationUp = new Stats();
		accelerationUp.addStat("Acceleration", 7);
		accelerationUp.addStat("Angular Acceleration", 7);
		accelerationUp.addStat("Armor", -3);
		//upgrades.add(new Upgrade("Acceleration Up", null, accelerationUp, "Increases acceleration of tank, but decreases tank armor by a miniscule amount"));
		//Traction increase (Speed decrease)
		Stats tractionUp = new Stats();
		tractionUp.addStat("Traction", 8);
		//upgrades.add(new Upgrade("Traction Up", null, tractionUp, "Increases traction of tank, but decreases tank speed by a miniscule amount"));
		//Projectile Durability increase (down Projectile Speed)
		Stats projectileDurabilityUp = new Stats();
		projectileDurabilityUp.addStat("Projectile Durability", 1);
		projectileDurabilityUp.addStat("Damage", -3);
		upgrades.add(new Upgrade("Projectile Durability Up", Assets.manager.get(Assets.durability_icon), projectileDurabilityUp, "Increases projectile durability, but decreases damage by a miniscule amount"));
	}
	
	public Upgrade(String n, Texture i, Stats c, String d) {
		name = n;
		icon = i;
		changes = c;
		desc = d;
	}
	
	public String getName() {
		return name;
	}
	
	public Texture getIcon() {
		if (icon != null)
			return icon;
		else
			return Assets.manager.get(Assets.bullet);
	}
	
	public Stats getChanges() {
		return changes;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public static Upgrade getRandomUpgrade() {
		return upgrades.get((int)(Math.random() * upgrades.size()));
	}
	
	public static ArrayList<Upgrade> getRandomUpgrade(int count) {
		ArrayList<Upgrade> randomUps = new ArrayList<Upgrade>();
		while (randomUps.size() < count && upgrades.size() > 0) {
			randomUps.add(upgrades.remove((int)(Math.random() * upgrades.size())));
		}
		upgrades.addAll(randomUps);
		return randomUps;
	}
}
