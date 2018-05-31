package com.tank.utils;

import java.util.ArrayList;

public class CycleList<E> {
	private ArrayList<E> values;
	private int index;
	private boolean loop;
	
	public CycleList() {
		values = new ArrayList<E>();
		index = 0;
		loop = true;
	}
	
	public CycleList(E[] arr, int i, boolean l) {
		values = new ArrayList<E>();
		for (E object : arr) {
			values.add(object);
		}
		index = i;
		loop = l;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}
	
	public E getCurrent() {
		if (values.size() == 0) return null;
		return values.get(index);
	}
	
	public E getNext() {
		if (index < values.size() - 1)
			return values.get(index + 1);
		else if (loop)
			return values.get(0);
		else
			return null;
	}
	
	public E getPrevious() {
		if (index > 0)
			return values.get(index - 1);
		else if (loop)
			return values.get(values.size() - 1);
		else
			return null;
	}
	
	public void addAtCurrent(E object){
		values.add(index, object);
	}
	
	public E get(int index){
		return values.get(index);
	}
	
	public void removeCurrent(){
		if (values.size() != 0) {
			values.remove(index);
			if (index == values.size()) index = 0;
		}
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
		if (values.size() == 0) return;
		if (loop) {
			index = Math.floorMod((index + n), values.size());	//always positive
		}
		else if ((index + n) < values.size() && (index + n) >= 0) {
			index = index+ n;
		}
	}
	
	public int indexOf(Object obj) {
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i).equals(obj)) {
				return i;
			}
		}
		return -1;
	}
}
