package com.ttr.actor.tank;

/**
 * @author Edmond
 * @version April 20th 2018
 * 
 * Description: Base tank, with position, orientation, and gun orientation. Forward with W, backward with S, A and D rotate, bullets fire toward mouse.
 * Left click is single fire or hold for continuous fire at a limited rate.
 */

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ttr.level.Level;
import com.ttr.utils.Assets;
import com.ttr.utils.Keybinds;;

public class Tank extends Actor {
	public float gunOrientation; // in radians
	public float tempX, tempY, tempO;	// test values to determine if move wis free from collision
	private Sprite tread, gun, vertex;
	public float gunOriginOffset = 28;
	public static float reloadTime;

	public static final int SIZE = Assets.manager.get(Assets.tread).getWidth();
	public static final float RATE_OF_FIRE = 1.0f; // rate of fire is inverse of reload time
	public static final float ANGULAR_VELOCITY = 2f;
	public static final float VELOCITY = 200f;

	public static float[] vertices;
	public static Polygon hitBox;
	public int[][] map;
	public ArrayList<Polygon> edges;

	public Tank(float x, float y, float orientation, float gunOrientation, int[][] map) {

		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		this.gunOrientation = gunOrientation;

		tread = new Sprite(Assets.manager.get(Assets.tread));
		tread.setOriginCenter(); // set pivot of tread to center
		gun = new Sprite(Assets.manager.get(Assets.gun_0)); // set pivot of gun to 100 pixels along width (scaled from
															// 256 total), half of height
		gun.setOrigin(Tank.SIZE / 2f - gunOriginOffset, Tank.SIZE / 2f);
		hitBox = new Polygon();
		this.map = map;
		canMoveTo(0, 0, 0); // fills the instance arrays so that the hitboxes made of bullets can render
							// properly

		vertex = new Sprite(Assets.manager.get(Assets.vertex));
	}

	private boolean canMoveTo(float x, float y, float orientation) {

		Vector2 v = new Vector2((float) (160 * Math.cos(orientation)), (float) (160 * Math.sin(orientation)));
		v.rotate(45f);
		vertices = new float[8];
		for (int i = 0; i < 4; i++) {
			vertices[i * 2] = x + v.x;
			vertices[i * 2 + 1] = y + v.y;
			v.rotate90(1);
		}
		hitBox.setVertices(vertices);
		int tankMapCol = (int) (super.getX() / 128);
		int tankMapRow = (int) ((40 * 128 - super.getY()) / 128);
		edges = new ArrayList<Polygon>();
		for (int yOffset = -2; yOffset <= 2; yOffset++) {
			for (int xOffset = -2; xOffset <= 2; xOffset++) {
				int tempRow = tankMapRow + yOffset, tempCol = tankMapCol + xOffset;
				// System.out.print("[" + tempRow + ", " + tempCol + "]");
				if (tempRow > 0 && tempRow < 40 && tempCol < 40 && tempCol > 0 && map[tempRow][tempCol] == 1)// &&
																												// !(super.getX()
																												// ==0
																												// &&
																												// super.getY()
																												// ==0))
				{
					int tempX = tempCol * 128, tempY = 40 * 128 - tempRow * 128; // a tile's top left coordinate, not
																					// bottom left,
					// due to rounding down with int tankMapRow = (int)((40*128-super.getY())/128),
					// which "rounds back up" when converting back to world coords
					vertices = new float[8];
					vertices[0] = tempX;
					vertices[1] = tempY;
					vertices[2] = tempX;
					vertices[3] = tempY - 128;
					vertices[4] = tempX + 128;
					vertices[5] = tempY - 128;
					vertices[6] = tempX + 128;
					vertices[7] = tempY;
					edges.add(new Polygon(vertices));
				}
				// {
				// Vector2 v = new Vector2(64f, 0);
				// v.rotate(45f);
				// vertices = new float[8];
				// for(int i = 0; i < 4; i++)
				// {
				// vertices[i*2] = (t1+super.getX())*128+64 + v.super.getX();
				// vertices[i*2+1] = (39-t2+super.getY())*128+64 +v.super.getY();
				// v.rotate(90f);
				// }
				// edges.add(new Polygon(vertices));
				//
				// }
			}
			// System.out.println();
		}
		// System.out.println();
		for (Polygon p : edges) {
			for (int i = 0; i < 4; i++) {
				// System.out.print(p.getVertices()[i*2] + " " + p.getVertices()[i*2+1]);
				if (p.contains(hitBox.getVertices()[i * 2], hitBox.getVertices()[i * 2 + 1])) {
					return false;
				}
				if (hitBox.contains(p.getVertices()[i * 2], p.getVertices()[i * 2 + 1]))
					return false;

			}
			// System.out.println();
		}
		// System.out.println();

		return true;
	}

	private void move(float delta) {

		if (Gdx.input.isKeyPressed(Keybinds.TANK_FORWARD)) {
			tempY = (float) (super.getY() + Math.sin(Math.toRadians(super.getRotation())) * Tank.VELOCITY * delta);
			tempX = (float) (super.getX() + Math.cos(Math.toRadians(super.getRotation())) * Tank.VELOCITY * delta);
			if (((Level) getStage()).map.inMap(tempX, tempY)
					&& canMoveTo(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
				super.setY(tempY);
				super.setX(tempX);
			}

		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_REVERSE)) {
			tempY = (float) (super.getY() - Math.sin(Math.toRadians(super.getRotation())) * Tank.VELOCITY * delta);
			tempX = (float) (super.getX() - Math.cos(Math.toRadians(super.getRotation())) * Tank.VELOCITY * delta);
			if (((Level) getStage()).map.inMap(tempX, tempY)
					&& canMoveTo(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
				super.setY(tempY);
				super.setX(tempX);
			}

		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CW)) {
			tempO = (float) Math.toRadians(super.getRotation()) - Tank.ANGULAR_VELOCITY * delta;
			if (canMoveTo(super.getX(), super.getY(), tempO)) {
				super.setRotation((float) Math.toDegrees(tempO));
			}
		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CCW)) {
			tempO = (float) Math.toRadians(super.getRotation()) + Tank.ANGULAR_VELOCITY * delta;
			if (canMoveTo(super.getX(), super.getY(), tempO)) {
				super.setRotation((float) Math.toDegrees(tempO));
			}
		}
	}

	private void fire(float delta) {
		// must use atan2 because the domain of atan is -pi/2 to pi/2
		// in other words when you divide super.getY()/super.getX() you lose information
		// differentiating
		// quadrants 1/3 & 2/4; atan2 has two parameters so we know which variable the
		// negative sign came from
		// mouse coordinates' origin is on the top left corner while renderer's is on
		// the bottom left
		Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		getStage().getCamera().unproject(mousePos); // to world coordinates
		gunOrientation = (float) Math.atan2((mousePos.y - super.getY()), (mousePos.x - super.getX()));

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
		// batch.begin() and batch.end() not required since stage already called its own
		// batch
		tread.setPosition(super.getX() - tread.getOriginX(), super.getY() - tread.getOriginY());
		tread.setRotation(super.getRotation());
		tread.draw(batch);

		gun.setPosition(
				super.getX() - gun.getOriginX()
						- gunOriginOffset * (float) Math.cos(Math.toRadians(super.getRotation())),
				super.getY() - gun.getOriginY()
						- gunOriginOffset * (float) Math.sin(Math.toRadians(super.getRotation())));
		gun.setRotation((float) Math.toDegrees(gunOrientation));
		gun.draw(batch);
		// draw tank's vertices
		for (int i = 0; i < 4; i++) {
			vertex.setPosition(hitBox.getVertices()[i * 2], hitBox.getVertices()[i * 2 + 1]);
			vertex.draw(batch);
		}
		// draw bricks' vertices
		for (Polygon p : edges) {
			for (int i = 0; i < 4; i++) {
				vertex.setPosition(p.getVertices()[i * 2], p.getVertices()[i * 2 + 1]);
				vertex.draw(batch);
			}
		}
	}

	public String toString() {
		return ("(" + super.getX() + ", " + super.getY() + ") with " + Math.toRadians(super.getRotation())
				+ " orientation");
	}
}