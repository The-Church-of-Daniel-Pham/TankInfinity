package com.ttr.tank;

import java.util.ArrayList;

/**
 * @author Edmond
 * @version April 13th 2018
 * 
 * Description: Base tank, with position, orientation, and gun orientation. Forward with W, backward with S, A and D rotate, bullets fire toward mouse.
 * Left click is single fire or hold for continuous fire at a limited rate.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
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
	public static float[] vertices;
	public static Polygon hitBox;
	public int[][] map;
	Sprite bullet;
	ArrayList<Polygon> edges;

	
	public Tank(float x, float y, float orientation, float gunOrientation, int[][] map) {
		
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.gunOrientation = gunOrientation;

		tread = new Sprite(Assets.manager.get(Assets.tread));
		tread.setOriginCenter(); // set pivot of tread to center
		gun = new Sprite(Assets.manager.get(Assets.gun_0)); // set pivot of gun to 100 pixels along width (scaled from
															// 256 total), half of height
		gun.setOrigin(Tank.SIZE / 2f - gunOriginOffset, Tank.SIZE / 2f);
		hitBox = new Polygon();
		this.map = map;
		canMoveTo(0,0,0); //fills the instance arrays so that the hitboxes made of bullets can render properly
		
		bullet = new Sprite(Assets.manager.get(Assets.dot));
	}
	private boolean canMoveTo(float x, float y, float orientation) {

		Vector2 v = new Vector2((float)(160*Math.cos(orientation)), (float)(160*Math.sin(orientation)));
		v.rotate(45f);
		vertices = new float[8];
		for(int i = 0; i < 4; i++)
		{
			vertices[i*2] = x+v.x;
			vertices[i*2+1] = y+v.y;
			v.rotate90(1);
		}
		hitBox.setVertices(vertices);
		int tankMapCol = (int)(x/128);
		int tankMapRow = (int)((40*128-y)/128);
		edges = new ArrayList<Polygon>();
		for(int yOffset = -2; yOffset <=2; yOffset++)
		{
			for(int xOffset = -2; xOffset <= 2; xOffset++)
			{
				int tempRow = tankMapRow +yOffset, tempCol= tankMapCol+xOffset;
				//System.out.print("[" + tempRow + ", " + tempCol + "]");
				if(tempRow > 0 && tempRow < 40 && tempCol < 40 && tempCol > 0 && map[tempRow][tempCol] == 1)// && !(x ==0 && y ==0))
				{
					int tempX = tempCol*128, tempY = 40*128-tempRow*128; //a tile's top left coordinate, not bottom left, 
												//due to rounding down with int tankMapRow = (int)((40*128-y)/128), 
												//which "rounds back up" when converting back to world coords
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
//				{
//				Vector2 v = new Vector2(64f, 0);
//				v.rotate(45f);
//				vertices = new float[8];
//				for(int i = 0; i < 4; i++)
//				{
//					vertices[i*2] = (t1+x)*128+64 + v.x;
//					vertices[i*2+1] = (39-t2+y)*128+64 +v.y;
//					v.rotate(90f);
//				}
//					edges.add(new Polygon(vertices));
//					
//				}
			}
			//System.out.println();
		}
		//System.out.println();
			for(Polygon p: edges)
			{
				for(int i = 0; i < 4; i++)
				{
					//System.out.print(p.getVertices()[i*2] + " " + p.getVertices()[i*2+1]);
					if(p.contains(hitBox.getVertices()[i*2], hitBox.getVertices()[i*2+1]))
					{
						return false;
					}
					if(hitBox.contains(p.getVertices()[i*2], p.getVertices()[i*2+1]))
						return false;
						
				}
				//System.out.println();
			}
			//System.out.println();
		
		return true;
	}
	
	private void move(float delta) {

		

		if (Gdx.input.isKeyPressed(Keybinds.TANK_FORWARD)) {
			float tempY = (float) (y + Math.sin(orientation) * Tank.VELOCITY * delta);
			float tempX = (float) (x + Math.cos(orientation) * Tank.VELOCITY * delta);
			if (Map.isValid(tempX, tempY) && canMoveTo(tempX, tempY, orientation)) {
				y = tempY;
				x = tempX;
			}

		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_REVERSE)) {
			float tempY = (float) (y - Math.sin(orientation) * Tank.VELOCITY * delta);
			float tempX = (float) (x - Math.cos(orientation) * Tank.VELOCITY * delta);
			if (Map.isValid(tempX, tempY) && canMoveTo(tempX, tempY, orientation)) {
				y = tempY;
				x = tempX;
			}

		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CW)) {
			float tempO = orientation - Tank.ANGULAR_VELOCITY * delta;
			if(canMoveTo(x, y, tempO))
			{
			orientation = tempO;
			}
		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CCW)) {
			float tempO = orientation + Tank.ANGULAR_VELOCITY * delta;
			if(canMoveTo(x, y, tempO))
			{
			orientation = tempO;
			}
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
		for(int i = 0; i < 4; i++)
		{
		bullet.setPosition(hitBox.getVertices()[i*2], hitBox.getVertices()[i*2+1]);
		bullet.draw(batch);
		}
		for(Polygon p: edges)
		{
			for(int i = 0; i < 4; i++)
			{
			bullet.setPosition(p.getVertices()[i*2], p.getVertices()[i*2+1]);
			bullet.draw(batch);
			}
		}
	}

	public String toString() {
		return ("(" + x + ", " + y + ") with " + orientation + " orientation");
	}
}