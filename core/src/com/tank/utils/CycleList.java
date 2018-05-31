package com.tank.utils;

public class CycleList {
	private Object[] values;
	private int index;
	private boolean loop;
	
	public CycleList(Object[] arr, int i, boolean l) {
		values = arr;
		index = i;
		loop = l;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}
	
	public Object getCurrent() {
		return values[index];
	}
	
	public Object getNext() {
		return values[index + 1];
	}
	
	public Object getPrevious() {
		return values[index - 1];
	}
	
	public void setCurrent(Object obj) {
		if (indexOf(obj) != -1) {
			setIndex(indexOf(obj));
		}
		else {
			// do nothing, index stays put
		}
	}
	
	public void cycleBy(int n) {
		if (loop) {
			index = Math.floorMod((index + n), values.length);	//always positive
		}
		else if ((index + n) < values.length && (index + n) >= 0) {
			index = index+ n;
		}
	}
	
	public int indexOf(Object obj) {
		for (int i = 0; i < values.length; i++)
		{
			if (values[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}
}
