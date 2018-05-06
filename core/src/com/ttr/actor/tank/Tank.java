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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ttr.actor.DynamicCollider;
import com.ttr.actor.map.MapTile;
import com.ttr.level.Level;
import com.ttr.utils.Assets;
import com.ttr.utils.AudioUtils;
import com.ttr.utils.Keybinds;;

public class Tank extends DynamicCollider implements InputProcessor {
	public static final float RATE_OF_FIRE = 1.0f; // rate of fire is inverse of reload time
	public static final float ANGULAR_VELOCITY = 2.0f;
	public static final float SPEED = 400f;
	public static final int SIZE = Assets.manager.get(Assets.tread).getWidth();
	public static final float SCALE = 2.0f;

	private Texture tread = Assets.manager.get(Assets.tread);
	private Texture gun = Assets.manager.get(Assets.gun_0);
	private Sound engine_sound = Assets.manager.get(Assets.tank_engine);
	private Sound tread_sound = Assets.manager.get(Assets.tank_tread);

	public float bulletFireOffset = 75f * SCALE;
	public float hitRadius = 60f * SCALE;

	public float treadOriginOffset = 4f * SCALE;
	public float gunOriginOffset = 12f * SCALE;
	public float treadOriginX = SIZE / 2f - treadOriginOffset;
	public float treadOriginY = SIZE / 2f;
	public float gunOriginX = SIZE / 2f - gunOriginOffset;
	public float gunOriginY = SIZE / 2f;

	public float gunOrientation; // in radians
	public static float reloadTime;
	public boolean moving = false;
	private boolean treadSoundOn = false;
	private boolean engineSoundOn = false;

	private long treadSound_id;
	private final float TREAD_VOLUME = 0.2f;
	private final float ENGINE_VOLUME = 0.6f;
	private final long FADE_TIME = 300000000; // 0.3 seconds

	public Tank(float x, float y, float orientation, float gunOrientation, Level level) {
		super(level);
		super.setX(x);
		super.setY(y);
		super.setRotation((float) Math.toDegrees(orientation));
		this.gunOrientation = gunOrientation;
		
		
	}

	public Polygon getHitboxAt(float x, float y, float orientation) {
		float[] vertices = new float[8];
		Vector2 v = new Vector2((float) (hitRadius * Math.cos(orientation)),
				(float) (hitRadius * Math.sin(orientation)));
		v.rotate(45f);
		for (int i = 0; i < 4; i++) {
			vertices[i * 2] = x + v.x;
			vertices[i * 2 + 1] = y + v.y;
			v.rotate90(1);
		}
		return new Polygon(vertices);
	}

	@Override
	public void onCollision() {
		// do nothing, for now
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
		moving = false;
		if (Gdx.input.isKeyPressed(Keybinds.TANK_FORWARD)) {
			float tempY = (float) (super.getY() + Math.sin(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			float tempX = (float) (super.getX() + Math.cos(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			attemptTranslationTo(tempX, tempY);
		}

		if (Gdx.input.isKeyPressed(Keybinds.TANK_REVERSE)) {
			float tempY = (float) (super.getY() - Math.sin(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			float tempX = (float) (super.getX() - Math.cos(Math.toRadians(super.getRotation())) * Tank.SPEED * delta);
			attemptTranslationTo(tempX, tempY);
		}

		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CW)) {
			float tempO = (float) Math.toRadians(super.getRotation()) - Tank.ANGULAR_VELOCITY * delta;
			attemptRotationTo(tempO);
		}
		if (Gdx.input.isKeyPressed(Keybinds.TANK_ROTATE_CCW)) {
			float tempO = (float) Math.toRadians(super.getRotation()) + Tank.ANGULAR_VELOCITY * delta;
			attemptRotationTo(tempO);
		}
	}

	private void attemptTranslationTo(float tempX, float tempY) {
		
		if (!collidesAt(tempX, tempY, (float) Math.toRadians(super.getRotation()))) {
			super.setY(tempY);
			super.setX(tempX);
			moving = true;
			updateHitbox();
		} else if (!collidesAt(tempX, getY(), (float) Math.toRadians(super.getRotation()))) {
			super.setX(tempX);
			moving = true;
			updateHitbox();
		} else if (!collidesAt(getX(), tempY, (float) Math.toRadians(super.getRotation()))) {
			super.setY(tempY);
			moving = true;
			updateHitbox();
		}
	}

	private void attemptRotationTo(float tempO) {
		if (!collidesAt(super.getX(), super.getY(), tempO)) {
			super.setRotation((float) Math.toDegrees(tempO));
			moving = true;
			updateHitbox();
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
				getStage().addActor(new Bullet((float) ((super.getX()) + bulletFireOffset * Math.cos(gunOrientation)),
						(float) (super.getY() + bulletFireOffset * Math.sin(gunOrientation)), gunOrientation,
						super.getLevel()));
				reloadTime += 1 / Tank.RATE_OF_FIRE;
			}
			// System.out.println(reloadTime);
		}
	}

	private void sound() {
		if (moving) {
			// testing to see whether leaving the engine sound on is better
			// engine_sound.stop();
			// engineSoundOn = false;
			if (!treadSoundOn) {
				treadSound_id = tread_sound.loop(TREAD_VOLUME);
				treadSoundOn = true;
			}
		} else { // not moving
			if (!engineSoundOn) {
				engine_sound.loop(ENGINE_VOLUME);
				engineSoundOn = true;
			}
			if (treadSoundOn) {
				AudioUtils.fade0ut(tread_sound, TREAD_VOLUME, FADE_TIME, treadSound_id);
				treadSoundOn = false;
			}
		}
	}

	@Override
	public void act(float delta) {
		move(delta); // tread controls
		fire(delta); // gun controls
		sound();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		// batch.begin() and batch.end() not required since stage already called its own
		batch.draw(tread, super.getX() - treadOriginX, super.getY() - treadOriginY, treadOriginX, treadOriginY,
				tread.getWidth(), tread.getHeight(), 1, 1, super.getRotation(), 0, 0, tread.getWidth(),
				tread.getHeight(), false, false);
		batch.draw(gun, super.getX() - gunOriginX, super.getY() - gunOriginY, gunOriginX, gunOriginY, gun.getWidth(),
				gun.getHeight(), 1, 1, (float) Math.toDegrees(gunOrientation), 0, 0, gun.getWidth(),
				gun.getHeight(), false, false);
		for(MapTile m: nearbyBricks) {
			m.drawVertices(batch, alpha);
		}
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