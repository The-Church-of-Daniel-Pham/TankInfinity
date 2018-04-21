package com.ttr.actor.tank;

import com.badlogic.gdx.graphics.Texture;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Simple projectile, with position and orientation. Continues along straight-line path while within Map bounds.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.ttr.level.Level;
import com.ttr.utils.Assets;

public class Bullet extends Actor {
	private Texture bullet;

	public static final int SIZE = Assets.manager.get(Assets.bullet).getWidth();
	public static final float VELOCITY = 800;

	public Bullet(float x, float y, float orientation) {
		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		super.setOrigin(SIZE/2f, SIZE/2f);	// set origin to center of texture-sized square
		bullet = Assets.manager.get(Assets.bullet);
	}

	@Override
	public void act(float delta) {
		super.setX(super.getX() + (float) Math.cos(Math.toRadians(super.getRotation())) * Bullet.VELOCITY * delta);
		super.setY(super.getY() + (float) Math.sin(Math.toRadians(super.getRotation())) * Bullet.VELOCITY * delta);
		// removes out of bounds balls
		if (!((Level) getStage()).map.inMap(super.getX(), super.getY())) {
			remove();
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(bullet, super.getX(), super.getY(), super.getOriginX(), super.getOriginY(), bullet.getWidth(),
				bullet.getHeight(), super.getScaleX(), super.getScaleY(), super.getRotation(), 0, 0, bullet.getWidth(),
				bullet.getHeight(), false, false);
	}

	public String toString() {
		return ("(" + super.getX() + ", " + super.getY() + ") with " + Math.toRadians(super.getRotation())
				+ " orientation");
	}
}