package com.tank.stats;

import com.badlogic.gdx.graphics.Texture;

public class Upgrade {
	private Texture icon;
	private Stats changes;
	private String desc;
	
	public Upgrade(Texture i, Stats c, String d) {
		icon = i;
		changes = c;
		desc = d;
	}
	
	public Texture getIcon() {
		return icon;
	}
	
	public Stats getChanges() {
		return changes;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public static Upgrade getRandomUpgrade() {
		int random = 0;
		Stats stats = new Stats();
		switch (random) {
			case 0:
				return new Upgrade(null, stats, "Hello!");
		}
		return null;
	}
}
