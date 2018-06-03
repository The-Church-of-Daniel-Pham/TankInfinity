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
		colors = new CycleList<String>(new String[] { "red", "blue", "green", "yellow" }, 0, true);
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
		String val = getCustomValue("tank color");
		if (val.equals("red")) {
			return new Color(204f / 255f, 4f / 255f, 4f / 255f, 1.0f);
		}
		else if (val.equals("blue")) {
			return new Color(4f / 255f, 112f / 255f, 204f / 255f, 1.0f);
		}
		else if (val.equals("green")) {
			return new Color(4f / 255f, 204f / 255f, 93f / 255f, 1.0f);
		}
		else if (val.equals("yellow")) {
			return new Color(204f / 255f, 197f / 255f, 4f / 255f, 1.0f);
		}
		else {
			return new Color(0f / 255f, 0f / 255f, 0f / 255f, 1.0f);
		}
	}

	public Texture getTexture(String name) {
		String val = getCustomValue("tank color");
		if (name.equals("tread")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.tread_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.tread_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.tread_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.tread_yellow);
			} else {
				return Assets.manager.get(Assets.tread_red);
			}
		} else if (name.equals("gun")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.gun_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.gun_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.gun_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.gun_yellow);
			} else {
				return Assets.manager.get(Assets.gun_red);
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
			} else {
				return Assets.manager.get(Assets.crosshairs_black);
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
			} else {
				return Assets.manager.get(Assets.artillery_crosshairs_black);
			}
		} else if (name.equals("preview")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.tank_preview_red);
			} else if (val.equals("blue")) {
				return Assets.manager.get(Assets.tank_preview_blue);
			} else if (val.equals("green")) {
				return Assets.manager.get(Assets.tank_preview_green);
			} else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.tank_preview_yellow);
			} else {
				return Assets.manager.get(Assets.tank_preview_red);
			}
		}
		return null; // not found
	}
}
