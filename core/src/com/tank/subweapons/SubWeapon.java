/**
 * @author Daniel
 * Description: Encapsulates the subweapon of a tank. 
 * The SubWeapon classes implement the actual projectiles
 * and provide the link between using a subweapon and 
 * creating the projectiles
 */
package com.tank.subweapons;

import com.badlogic.gdx.graphics.Texture;
import com.tank.actor.vehicles.PlayerTank;
import com.tank.stats.Stats;

public abstract class SubWeapon {
	protected String name;
	protected Texture tex;
	protected int ammo;

	public SubWeapon(String name, Texture tex, int ammo) {
		this.name = name;
		this.tex = tex;
		this.ammo = ammo;
	}

	/**
	 * 
	 * @return name of subweapon
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return texture of subweapon
	 */
	public Texture getTexture() {
		return tex;
	}

	/**
	 * 
	 * @return amount of ammo
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * manually set the texture
	 * 
	 * @param tex
	 *            sets the texture to this
	 */
	public void setTexture(Texture tex) {
		this.tex = tex;
	}

	/**
	 * increments 'ammo' by the given amt
	 * 
	 * @param ammo
	 *            amount of ammo to add
	 */
	public void addAmmo(int ammo) {
		this.ammo += ammo;
	}

	/**
	 * spawns the necessary projectile(s) to complete the subweapon and if
	 * necessary, takes care of the randomness of velocity, etc of some subweapons
	 * 
	 * @param source
	 *            the PlayerTank shooting the projectiles
	 * 
	 */
	public abstract void shoot(PlayerTank source);

	/**
	 * Manipulates the source's stats to create stats appropriate for the given
	 * subweapon
	 * 
	 * @param source
	 *            the tank that will shoot the subweapon
	 * @return the Stats that encapsulates all of the subweapon's stats
	 */
	public abstract Stats createStats(PlayerTank source);

	/**
	 * @return true, if the SubWeapons have the same name, otherwise false
	 */
	public boolean equals(Object other) {
		return (other instanceof SubWeapon && ((SubWeapon) other).name.equals(name));
	}
}
