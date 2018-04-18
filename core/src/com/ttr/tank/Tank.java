package com.ttr.tank;

/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Base tank, with position, orientation, and gun orientation. Forward with W, backward with S, A and D rotate, bullets fire toward mouse.
 * Left click is single fire or hold for continuous fire at a limited rate.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ttr.map.Map;
import com.ttr.utils.Assets;
import com.ttr.utils.Keybinds;;

public class Tank extends Actor {
	public float x, y, orientation, gunOrientation; // orientation and gunOrientation are in radians
	private Sprite tread, gun;
	public float gunOriginOffset = 28;
	public static float reloadTime;
	
	public static final int SIZE = Assets.manager.get(Assets.tread).getWidth();
	public static final float RATE_OF_FIRE = 1.0f;	// rate of fire is inverse of reload time
	public static final float ANGULAR_VELOCITY = 2f;
	public static final float VELOCITY = 200f;
	
	public Tank(float x, float y, float orientation, float gunOrientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.gunOrientation = gunOrientation;

		tread = new Sprite(Assets.manager.get(Assets.tread));
		tread.setOriginCenter(); // set pivot of tread to center
		gun = new Sprite(Assets.manager.get(Assets.gun_0)); // set pivot of gun to 100 pixels along width (scaled from
															// 256 total), half of height
		gun.setOrigin(Tank.SIZE / 2f - gunOriginOffset, Tank.SIZE / 2f);
	}
	
	private void move(float delta) {
		if (Gdx.input.isKeyPressed(Keybinds.TANK_FORWARD)) {
			float tempY = (float) (y + Math.sin(orientation) * Tank.VELOCITY * delta);
			float tempX = (float) (x + Math.cos(orientation) * Tank.VELOCITY * delta);
			if (Map.isValid(tempX, tempY)) {
				y = tempY;
				x = tempX;
			}

		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_REVERSE)) {
			float tempY = (float) (y - Math.sin(orientation) * Tank.VELOCITY * delta);
			float tempX = (float) (x - Math.cos(orientation) * Tank.VELOCITY * delta);
			if (Map.isValid(tempX, tempY)) {
				y = tempY;
				x = tempX;
			}

		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CW)) {
			orientation -= Tank.ANGULAR_VELOCITY * delta;
		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CCW)) {
			orientation += Tank.ANGULAR_VELOCITY * delta;
		}
	}

	private void fire(float delta) {
		// must use atan2 because the domain of atan is -pi/2 to pi/2
		// in other words when you divide y/x you lose information differentiating
		// quadrants 1/3 & 2/4; atan2 has two parameters so we know which variable the
		// negative sign came from
		// mouse coordinates' origin is on the top left corner while renderer's is on
		// the bottom left
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		getStage().getCamera().unproject(mousePos); // to world coordinates
		gunOrientation = (float) Math.atan2((mousePos.y - y), (mousePos.x - x));

		// if reloadTime needs to be reduced
		if (reloadTime > 0) {
			reloadTime -= delta;
		}
		if (Gdx.input.isButtonPressed(Keybinds.TANK_FIRE_PRIMARY)) {
			// i honestly guess and checked for these constants, using _pivot textures to
			// debug
			// if you can find a more elegant way to find these constants, be my guest
			if (reloadTime <= 0) {
				getStage().addActor(new Bullet((float) (gun.getX() + 64 + 180 * Math.cos(gunOrientation)),
						(float) (gun.getY() + 96 + 180 * Math.sin(gunOrientation)), gunOrientation));
				reloadTime += 1 / Tank.RATE_OF_FIRE;
			}
			// System.out.println(reloadTime);
		}
	}
	
	@Override
	public void act(float delta) {
		move(delta); // tread controls
		fire(delta); // gun controls
	}

	@Override
	public void draw(Batch batch, float alpha) {
		// batch.begin()/end() not required since stage already called its own batch
		tread.setPosition(x - tread.getOriginX(), y - tread.getOriginY());
		tread.setRotation((float) Math.toDegrees(orientation));
		tread.draw(batch);

		gun.setPosition(x - gun.getOriginX() - gunOriginOffset * (float) Math.cos(orientation),
				y - gun.getOriginY() - gunOriginOffset * (float) Math.sin(orientation));
		gun.setRotation((float) Math.toDegrees(gunOrientation));
		gun.draw(batch);
	}

	public String toString() {
		return ("(" + x + ", " + y + ") with " + orientation + " orientation");
	}
}