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
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ttr.actor.DynamicCollider;
import com.ttr.level.Level;
import com.ttr.utils.Assets;
import com.ttr.utils.Keybinds;;

public class Tank extends DynamicCollider implements InputProcessor {
	public static final float RATE_OF_FIRE = 1.0f; // rate of fire is inverse of reload time
	public static final float ANGULAR_VELOCITY = 3.0f;
	public static final float SPEED = 300f;
	public static final int SIZE = Assets.manager.get(Assets.tread).getWidth();
	public static final float SCALE = 1.0f;
	
	private Sprite tread = new Sprite(Assets.manager.get(Assets.tread)); 
	private Sprite gun = new Sprite(Assets.manager.get(Assets.gun_0));
	private Sound  idle_sound = Assets.manager.get(Assets.tank_idle);
	private Sound forward_sound = Assets.manager.get(Assets.tank_move);
	private Sound reverse_sound = Assets.manager.get(Assets.tank_move);
	
	public float tempX, tempY, tempO; // test values to determine if move is on the map
	public float gunOrientation; // in radians
	public float gunOriginOffset = 12f * SCALE;
	public float treadOriginOffset = 4f * SCALE;
	public float hitRadius = 64f * SCALE;
	public static float reloadTime;
	private boolean forwardSoundIsOn = false;
	private boolean	reverseSoundIsOn = false;
	
	public Tank(float x, float y, float orientation, float gunOrientation, Level level) {
		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		this.gunOrientation = gunOrientation;
		super.setLevel(level);
		
		tread.setOrigin(SIZE/2f - treadOriginOffset, SIZE/2f);	// set pivot of tread
		tread.setScale(SCALE);
		gun.setOrigin(SIZE/2f - gunOriginOffset, SIZE/2f);	// set pivot of gun
		gun.setScale(SCALE);
		
		brickHitboxes = new ArrayList<Polygon>();
		collidesAt(0, 0, 0); // fills the instance arrays so that the hitboxes' vertices can render properly
	}
	
	public float[] getVertices(float x, float y, float orientation) {
		float[] vertices = new float[8];
		Vector2 v = new Vector2((float) (hitRadius * Math.cos(orientation)), (float) (hitRadius * Math.sin(orientation)));
		v.rotate(45f);
		for (int i = 0; i < 4; i++) {
			vertices[i * 2] = x + v.x;
			vertices[i * 2 + 1] = y + v.y;
			v.rotate90(1);
		}
		return vertices;
	}
	
	@Override
	public void onCollision() {
		//do nothing, for now
	}

	@Override
	public boolean keyDown(int keycode) {
		// toggle keys - tap only
		// allow tank to pass through obstacles
		if (keycode == Keybinds.TANK_TOGGLE_NOCLIP) {
			super.toggleNoclip();
		}
		return false; // if returns true, multiplexer would stop
	}

	private void move(float delta) {
		
		if (Gdx.input.isKeyPressed(Keybinds.TANK_FORWARD)) {
			tempY = (float) (super.getY() + Math.sin(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			tempX = (float) (super.getX() + Math.cos(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			if (super.getLevel().map.inMap(tempX, tempY) && !collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
				super.setY(tempY);
				super.setX(tempX);
				
				if(!forwardSoundIsOn)
				{
					forward_sound.loop(5f);
					forwardSoundIsOn = true;
				}
			}
			else
			{
				forwardSoundIsOn = false;
				forward_sound.stop();
			}
		}
		else
		{
			forwardSoundIsOn = false;
			forward_sound.stop();
		}
		
		

		if (Gdx.input.isKeyPressed(Keybinds.TANK_REVERSE)) {
			tempY = (float) (super.getY() - Math.sin(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			tempX = (float) (super.getX() - Math.cos(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			if (super.getLevel().map.inMap(tempX, tempY) && !collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
				super.setY(tempY);
				super.setX(tempX);
				
				if(!reverseSoundIsOn)
				{
					reverse_sound.loop(5f);
					reverseSoundIsOn = true;
				}
			}else
			{
				reverseSoundIsOn = false;
				reverse_sound.stop();
			}
		}
		else
		{
			reverseSoundIsOn = false;
			reverse_sound.stop();
		}
		
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CW)) {
			tempO = (float) Math.toRadians(super.getRotation()) - Tank.ANGULAR_VELOCITY * delta;
			if (!collidesAt(super.getX(), super.getY(), tempO)) {
				super.setRotation((float) Math.toDegrees(tempO));
			}
		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CCW)) {
			tempO = (float) Math.toRadians(super.getRotation()) + Tank.ANGULAR_VELOCITY * delta;
			if (!collidesAt(super.getX(), super.getY(), tempO)) {
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
				getStage().addActor(new Bullet((float)((super.getX()) + 150 * SCALE * Math.cos(gunOrientation)), (float)(super.getY() +150 * SCALE * Math.sin(gunOrientation)), gunOrientation, super.getLevel()));
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
		gun.setPosition(super.getX() - gun.getOriginX(), super.getY() - gun.getOriginY());
		gun.setRotation((float) Math.toDegrees(gunOrientation));		
		gun.draw(batch);	
		drawVertices(batch, alpha);
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}