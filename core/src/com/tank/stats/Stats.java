package com.tank.stats;

import java.util.LinkedHashMap;
import java.util.Set;

public class Stats {
	private LinkedHashMap<String, Integer> stats;
	
	public Stats() {
		stats = new LinkedHashMap<String, Integer>();
	}

	public void addStat(String stat, Integer val) {
		stats.put(stat, val);
	}
	
	public void mergeStats(Stats other) {
		for (String key : other.getKeys()) {
			if (stats.containsKey(key)) {
				int newValue = getStatValue(key) + other.getStatValue(key);
				if (newValue < 1) newValue = 1;
				addStat(key, newValue);
			}
			else {
				if (other.getStatValue(key) >= 1)
					addStat(key, other.getStatValue(key));
			}
		}
	}
	
	public Set<String> getKeys() {
		return stats.keySet();
	}
	
	public int getStatValue(String stat) {
		return stats.get(stat);
	}
	public boolean hasStat(String stat) {
		return (stats.containsKey(stat));
	}
}
