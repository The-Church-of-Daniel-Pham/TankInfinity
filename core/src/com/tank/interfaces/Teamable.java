/**
 * @author The Church of Daniel Pham
 * Description:
 * This interface is used for placing tanks on teams.
 * A team name is sufficient for determining aspects
 * such as friendly fire as there are only two basic
 * teams in TankInfinity.
 */
package com.tank.interfaces;

public interface Teamable {
	/**
	 * Use this to compare with other Teamables and determine if two Teamables are
	 * on the same team or not
	 * 
	 * @return the name of the Teamable's team
	 */
	public String getTeam();
}
