package com.tank.utils;

public class LinkyList {
	private Object[] values;
	private int index;
	
	public LinkyList(Object[] arr, int i) {
		values = arr;
		index = i;
	}
	
	public Object getIndex() {
		return index;
	}
	
	public Object getCurrent() {
		return values[index];
	}
	
	public void incrementIndexBy(int n) {
		if ((index + n) < values.length && (index + n) >= 0) {
			index += n;
		}
	}
}
