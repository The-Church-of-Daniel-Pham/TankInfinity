package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.vehicles.PlayerTank;

public abstract class SubWeapon {
	protected String name;
	protected Texture tex;
	protected int ammo;

	public SubWeapon(String name, Texture tex, int ammo) {
		this.name = name;
		this.tex = tex;
		this.ammo = ammo;
	}

	public Texture getTexture() {
		return tex;
	}
	public int getAmmo() {
		return ammo;
	}
	public void setTexture(Texture tex) {
		this.tex = tex;
	}
	public void addAmmo(int ammo) {
		this.ammo = ammo;
	}
	public abstract void shoot(PlayerTank source);
	
	public boolean equals(Object other) {
		return (other instanceof SubWeapon && ((SubWeapon)other).name.equals(name));
	}
}
