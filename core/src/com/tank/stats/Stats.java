package com.tank.stats;

import java.util.LinkedHashMap;

public class Stats {
	private LinkedHashMap<String, Integer> stats;
	
	public Stats() {
		stats = new LinkedHashMap<String, Integer>();
	}

	public void addStat(String stat, Integer i) {
		stats.put(stat, i);
	}
	
	public int getStatValue(String stat) {
		return stats.get(stat);
	}
	public boolean hasStat(String stat) {
		return (stats.containsKey(stat));
	}
}
