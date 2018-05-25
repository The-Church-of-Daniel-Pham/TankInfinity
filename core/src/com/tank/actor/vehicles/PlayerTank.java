package com.tank.actor.vehicles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.tiles.WallTile;
import com.tank.actor.ui.Cursor;
import com.tank.controls.TankController;
import com.tank.interfaces.Collidable;
import com.tank.subweapons.SubWeapon;

public class PlayerTank extends FreeTank {

	protected TankController controls;
	protected Cursor cursor;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;
	protected double timeSinceShot;

	public PlayerTank(int player, Texture tTexture, Texture gTexture, float x, float y) {
		super(tTexture, gTexture, x, y);
		playerNumber = player;

	}

	public PlayerTank(int player, Texture tTexture, Texture gTexture, Color color, float x, float y) {
		super(tTexture, gTexture, color, x, y);
		playerNumber = player;
	}

	/**
	 * updates the velocity based on user input and tank stats
	 */
	public void act(float delta) {
		if (controls.downPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration"), 180 + getRotation());
		} else if (controls.upPressed()) {
			super.applyForce(delta * stats.getStatValue("Acceleration"), getRotation());
		}
		if (controls.leftPressed()) {
			super.rotateBy(delta * stats.getStatValue("Angular_Velocity"));
		} else if (controls.rightPressed()) {
			super.rotateBy(delta * -1 * stats.getStatValue("Angular_Velocity"));
		}
		updateVelocityAndMove();
		if(controls.firePressed()) {
			shoot();
		}
	}
	public void shoot() {
		getStage().addActor(new Bullet(null, null, this, getX(), getY(), getRotation()));
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public Polygon getHitbox() {
		float[] f = new float[8];
		Vector2 v = new Vector2((float)(getWidth())/2, 0);
		v.setAngle(getRotation());
		v.rotate(45);
<<<<<<< HEAD
		return new Polygon(f);
=======
		for(int i = 0; i < 4; i++) {
			f[i*2] = v.x;
			f[i*2+1] = v.y;
			v.rotate(90);
		}
		a.add(new Polygon(f));
		return a;
>>>>>>> branch 'master' of https://github.com/The-Church-of-Daniel-Pham/TankInfinity.git
	}

	public void checkCollision(Collidable other) {
<<<<<<< HEAD
		if(other instanceof WallTile || other instanceof AbstractVehicle) {
			Intersector.intersectPolygons(this.getHitbox(), other.getHitbox(), new Polygon());
			//TODO so now what
=======
		ArrayList<Polygon> thisHitbox = getHitbox();
		ArrayList<Polygon> otherHitbox = other.getHitbox();
		for(Polygon t: thisHitbox) {
			for(Polygon o: otherHitbox) {
				if(Intersector.intersectPolygons(t,  o, new Polygon())) {
					//TODO something happens
				}
			}
>>>>>>> branch 'master' of https://github.com/The-Church-of-Daniel-Pham/TankInfinity.git
		}
	}
}
