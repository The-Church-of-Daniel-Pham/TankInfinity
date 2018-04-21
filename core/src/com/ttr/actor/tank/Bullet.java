package com.ttr.actor.tank;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

/**
 * @author Samuel
import com.badlogic.gdx.graphics.Texture;


 * @version April 13th 2018
 * 
 * Description: Simple projectile, with position and orientation. Continues along straight-line path while within Map bounds.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ttr.actor.DynamicCollider;
import com.ttr.actor.map.MapTile;
import com.ttr.level.Level;
import com.ttr.utils.Assets;

public class Bullet extends DynamicCollider {
	public float tempX, tempY; // test values to determine if move is free from collision
	public Polygon bulletHitbox;
	public ArrayList<Polygon> brickHitboxes;
	public Sprite vertex;

	public static final int SIZE = Assets.manager.get(Assets.bullet).getWidth();
	public static final float VELOCITY = 800;

	public Bullet(float x, float y, float orientation, Level level) {
		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		super.setOrigin(SIZE / 2f, SIZE / 2f); // set origin to center of texture-sized square
		super.setTexture(Assets.manager.get(Assets.bullet));
		super.setLevel(level);
		
		bulletHitbox = new Polygon();
		brickHitboxes = new ArrayList<Polygon>();
		vertex = new Sprite(Assets.manager.get(Assets.vertex));
		collidesAt(0, 0, 0);	//set-up vertex arrays
	}

	@Override
	public void act(float delta) {
		tempX = (super.getX() + (float) Math.cos(Math.toRadians(super.getRotation())) * Bullet.VELOCITY * delta);
		tempY = (super.getY() + (float) Math.sin(Math.toRadians(super.getRotation())) * Bullet.VELOCITY * delta);
		if (super.getLevel().map.inMap(tempX, tempY)) {
			if (!collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
				super.setY(tempY);
				super.setX(tempX);
			} else {
				onCollision();
			}
		}
	}

	@Override
	public boolean collidesAt(float x, float y, float orientation) {
		// set-up hitbox
		bulletHitbox = super.getHitbox(x, y, orientation);
		brickHitboxes = super.getLevel().map.getHitboxes(super.getLevel().map
				.getBrickNeighbors(super.getLevel().map.getTileAt(x, y)[0], super.getLevel().map.getTileAt(x, y)[1]));
		// detect collision(s)
		for (Polygon brickHitBox : brickHitboxes) {
			for (int i = 0; i < 4; i++) {
				if (brickHitBox.contains(bulletHitbox.getVertices()[i * 2], bulletHitbox.getVertices()[i * 2 + 1])) {
					return true;
				}
				if (bulletHitbox.contains(brickHitBox.getVertices()[i * 2], brickHitBox.getVertices()[i * 2 + 1])) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public float[] getVertices(float x, float y, float orientation) {
		float[] vertices = new float[8];
		vertices[0] = super.getX();
		vertices[1] = super.getY();
		vertices[2] = super.getX() + SIZE;
		vertices[3] = super.getY();
		vertices[4] = super.getX() + SIZE;
		vertices[5] = super.getY() + SIZE;
		vertices[6] = super.getX();
		vertices[7] = super.getY() + SIZE;
		return vertices;
	}

	@Override
	public void onCollision() {
		super.remove();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		// draw bullet's vertices
		for (int i = 0; i < 4; i++) {
			vertex.setPosition(bulletHitbox.getVertices()[i * 2], bulletHitbox.getVertices()[i * 2 + 1]);
			vertex.draw(batch);
		}
		// draw bricks' vertices
		for (Polygon p : brickHitboxes) {
			for (int i = 0; i < 4; i++) {
				vertex.setPosition(p.getVertices()[i * 2], p.getVertices()[i * 2 + 1]);
				vertex.draw(batch);
			}
		}
	}

}