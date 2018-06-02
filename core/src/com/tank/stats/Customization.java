package com.tank.stats;

import java.util.LinkedHashMap;
import com.badlogic.gdx.graphics.Texture;
import com.tank.utils.Assets;
import com.tank.utils.CycleList;

public class Customization {
	private LinkedHashMap<String, String> custom;
	private CycleList<String> colors;

	public Customization() {
		custom = new LinkedHashMap<String, String>();
		colors = new CycleList<String> (new String[] {"default", "red", "blue", "green", "yellow", "purple", "tan"}, 0, true);
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

	public Texture getTexture(String name) {
		String val = getCustomValue("tank color");
		if (name.equals("tread")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.tread_red);
			}
			else if (val.equals("blue")) {
				return Assets.manager.get(Assets.tread_blue);
			}
			else if (val.equals("green")) {
				return Assets.manager.get(Assets.tread_green);
			}
			else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.tread_yellow);
			}
			else {
				return Assets.manager.get(Assets.tread_red);
			}
		}
		else if (name.equals("gun")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.gun_red);
			}
			else if (val.equals("blue")) {
				return Assets.manager.get(Assets.gun_blue);
			}
			else if (val.equals("green")) {
				return Assets.manager.get(Assets.gun_green);
			}
			else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.gun_yellow);
			}
			else {
				return Assets.manager.get(Assets.gun_red);
			}
		}
		else if (name.equals("cursor")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.crosshairs_red);
			}
			else if (val.equals("blue")) {
				return Assets.manager.get(Assets.crosshairs_blue);
			}
			else if (val.equals("green")) {
				return Assets.manager.get(Assets.crosshairs_green);
			}
			else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.crosshairs_yellow);
			}
			else {
				return Assets.manager.get(Assets.crosshairs_default);
			}
		}
		else if (name.equals("preview")) {
			if (val.equals("red")) {
				return Assets.manager.get(Assets.tank_preview_red);
			}
			else if (val.equals("blue")) {
				return Assets.manager.get(Assets.tank_preview_blue);
			}
			else if (val.equals("green")) {
				return Assets.manager.get(Assets.tank_preview_green);
			}
			else if (val.equals("yellow")) {
				return Assets.manager.get(Assets.tank_preview_yellow);
			}
			else {
				return Assets.manager.get(Assets.tank_preview_red);
			}
		}
		return null;	//not found
	}
}
