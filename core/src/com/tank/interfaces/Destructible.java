/**
 * @author The Church of Daniel Pham
 * Description:
 * This interface deals with objects that have a set
 * amount of health, providing methods to manipulate
 * health and to deal with its complete depletion.
 */
package com.tank.interfaces;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Destructible {
	/**
	 * Used to deal damage (i.e. decrease health) to Destructibles
	 * 
	 * @param source
	 *            the actor dealing the damage
	 * @param damage
	 *            the amount of damage dealt
	 */
	public void damage(Actor source, int damage);

	/**
	 * Used to add health to Destructibles
	 * 
	 * @param source
	 *            the actor giving the health
	 * @param heal
	 *            the amount of health given
	 */
	public void heal(Actor source, int heal);

	/**
	 * This removes the actor from the stage, but each Destructible deals with
	 * destruction in its own way, such as removal from its static array
	 */
	public void destroy();

	/**
	 * Can be used to determine if Desctructible should still act if it is removed
	 * by another actor in the same frame, before the Destructible acts
	 * 
	 * @return true if the Destructible is destroyed, false otherwise
	 */
	public boolean isDestroyed();
}
