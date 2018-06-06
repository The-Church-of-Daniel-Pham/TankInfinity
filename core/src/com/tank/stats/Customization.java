package com.tank.stats;

import java.util.LinkedHashMap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.tank.utils.Assets;
import com.tank.utils.CycleList;

public class Customization {
	private LinkedHashMap<String, String> custom;
	private CycleList<String> colors;

	public Customization() {
		custom = new LinkedHashMap<String, String>();
		colors = new CycleList<String>(new String[] { "red", "blue", "green", "yellow", "pink", "cyan", "lime", "orange" }, 0, true);
	}

	public void setCustom(String cust, String val) {
		if (cust.equals("tank color")) {
			colors.setCurrent(val);
			custom.put(cust, val);
		}

	}

	public String getCustomValue(String cust) {
		return custom.get(cust);
	}

	public boolean hasCustom(String cust) {
		return (custom.containsKey(cust));
	}

	public void cycleCustom(String cust, int n) {
		if (cust.equals("tank color")) {
			colors.cycleBy(n);
			custom.put(cust, (String) colors.getCurrent());
		}
	}

	public Color getColor() {
		// from colors.psd
		// default to black
		String val = getCustomValue("tank color");
		if (val.equals("red")) {
			return new Color(245f / 255f, 62f / 255f, 48f / 255f, 1.0f);
		}
		else if (val.equals("blue")) {
			return new Color(76f / 255f, 104f / 255f, 192f / 255f, 1.0f);
		}
		else if (val.equals("green")) {
			return new Color(1f / 255f, 161f / 255f, 125f / 255f, 1.0f);
		}
		else if (val.equals("yellow")) {
			return new Color(255f / 255f, 234f / 255f, 61f / 255f, 1.0f);
		}
		else if (val.equals("pink")) {
			return new Color(240f / 255f, 93f / 255f, 143f / 255f, 1.0f);
		}
		else if (val.equals("cyan")) {
			return new Color(0f / 255f, 188f / 255f, 212f / 255f, 1.0f);
		}
		else if (val.equals("lime")) {
			return new Color(123f / 255f, 201f / 255f, 66f / 255f, 1.0f);
		}
		else if (val.equals("orange")) {
			return new Color(253f / 255f, 184f / 255f, 37f / 255f, 1.0f);
		}
		else {
			return new Color(0f / 255f, 0f / 255f, 0f / 255f, 1.0f);
		}
	}

	public Texture getTexture(String name) {
		String val = getCustomValue("tank color");
		// default to red
		if (name.equals("tread")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.player_tank_tread_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.player_tank_tread_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.player_tank_tread_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.player_tank_tread_yellow);
			} else if (val.equals("pink")) {
				return Assets.manager.get(Assets.player_tank_tread_pink);
			} else if (val.equals("cyan")) {
				return Assets.manager.get(Assets.player_tank_tread_cyan);
			} else if (val.equals("lime")) {
				return Assets.manager.get(Assets.player_tank_tread_lime);
			} else if (val.equals("orange")) {
				return Assets.manager.get(Assets.player_tank_tread_orange);
			} else {
				return Assets.manager.get(Assets.player_tank_tread_red);
			}
		} else if (name.equals("gun")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.player_tank_gun_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.player_tank_gun_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.player_tank_gun_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.player_tank_gun_yellow);
			} else if (val.equals("pink")) {
				return Assets.manager.get(Assets.player_tank_gun_pink);
			} else if (val.equals("cyan")) {
				return Assets.manager.get(Assets.player_tank_gun_cyan);
			} else if (val.equals("lime")) {
				return Assets.manager.get(Assets.player_tank_gun_lime);
			} else if (val.equals("orange")) {
				return Assets.manager.get(Assets.player_tank_gun_orange);
			} else {
				return Assets.manager.get(Assets.player_tank_gun_red);
			}
		} else if (name.equals("cursor")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.crosshairs_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.crosshairs_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.crosshairs_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.crosshairs_yellow);
			} else if (val.equals("pink")) {
				return Assets.manager.get(Assets.crosshairs_pink);
			} else if (val.equals("cyan")) {
				return Assets.manager.get(Assets.crosshairs_cyan);
			} else if (val.equals("lime")) {
				return Assets.manager.get(Assets.crosshairs_lime);
			} else if (val.equals("orange")) {
				return Assets.manager.get(Assets.crosshairs_orange);
			} else {
				return Assets.manager.get(Assets.crosshairs_red);
			}
		} else if (name.equals("artillery")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.artillery_crosshairs_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.artillery_crosshairs_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.artillery_crosshairs_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.artillery_crosshairs_yellow);
			} else if (val.equals("pink")) {
				return Assets.manager.get(Assets.artillery_crosshairs_pink);
			} else if (val.equals("cyan")) {
				return Assets.manager.get(Assets.artillery_crosshairs_cyan);
			} else if (val.equals("lime")) {
				return Assets.manager.get(Assets.artillery_crosshairs_lime);
			} else if (val.equals("orange")) {
				return Assets.manager.get(Assets.artillery_crosshairs_orange);
			} else {
				return Assets.manager.get(Assets.artillery_crosshairs_red);
			}
		} else if (name.equals("preview")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.player_tank_preview_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.player_tank_preview_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.player_tank_preview_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.player_tank_preview_yellow);
			} else if (val.equals("pink")) {
				return Assets.manager.get(Assets.player_tank_preview_pink);
			} else if (val.equals("cyan")) {
				return Assets.manager.get(Assets.player_tank_preview_cyan);
			} else if (val.equals("lime")) {
				return Assets.manager.get(Assets.player_tank_preview_lime);
			} else if (val.equals("orange")) {
				return Assets.manager.get(Assets.player_tank_preview_orange);
			} else {
				return Assets.manager.get(Assets.player_tank_preview_red);
			}
		}
		return null; // not found
	}
}
