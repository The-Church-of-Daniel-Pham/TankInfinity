package com.tank.stats;

import java.util.LinkedHashMap;
import com.badlogic.gdx.graphics.Texture;
import com.tank.utils.Assets;
import com.tank.utils.LinkyList;

public class Customization {
	private LinkedHashMap<String, String> custom;
	private LinkyList treads;
	private LinkyList guns;

	public Customization() {
		custom = new LinkedHashMap<String, String>();
		treads = new LinkyList (new String[] {"default", "red", "blue", "green", "yellow", "purple", "tan"}, 0);
		guns = new LinkyList (new String[] {"default", "red", "blue", "green", "yellow", "purple", "tan"}, 0);
	}

	public void addCustom(String cust, String val) {
		custom.put(cust, val);
	}

	public String getCustomValue(String cust) {
		return custom.get(cust);
	}

	public boolean hasCustom(String cust) {
		return (custom.containsKey(cust));
	}
	
	public void cycleCustom(String cust, int n) {
		if (cust.equals("tread")) {
			
		}
		else if (cust.equals("gun")) {
			
		}
	}

	public Texture getTexture(String cust) {
		String val = getCustomValue(cust);
		if (cust.equals("tread")) {
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
			else if (val.equals("purple")) {
				return Assets.manager.get(Assets.tread_purple);
			}
			else if (val.equals("tan")) {
				return Assets.manager.get(Assets.tread_tan);
			}
			else {
				return Assets.manager.get(Assets.tread_default);
			}
		}
		else if (cust.equals("gun")) {
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
			else if (val.equals("purple")) {
				return Assets.manager.get(Assets.gun_purple);
			}
			else if (val.equals("tan")) {
				return Assets.manager.get(Assets.gun_tan);
			}
			else {
				return Assets.manager.get(Assets.gun_default);
			}
		}
		return null;	//not found
	}
}
