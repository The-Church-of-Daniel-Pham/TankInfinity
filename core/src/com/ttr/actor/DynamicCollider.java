package com.ttr.actor;

public abstract class DynamicCollider extends Collider{
	public abstract boolean collidesAt(float x, float y, float orientation);
}
