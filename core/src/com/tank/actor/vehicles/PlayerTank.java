package com.tank.actor.vehicles;

import java.util.ArrayList;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.ui.Cursor;
import com.tank.controls.ControlConstants;
import com.tank.controls.TankController;
import com.tank.media.MediaSound;
import com.tank.stats.Customization;
import com.tank.subweapons.SubWeapon;
import com.tank.utils.Assets;

public class PlayerTank extends FreeTank implements InputProcessor {
	/**
	 * used for spawning bullets the correct distance away from Vehicle's center
	 */
	public static final int TANK_GUN_LENGTH = 135; // probably redundant, check free tank
	private static float angle;	//angle between diagonal of rectangle and its base
	
	protected TankController controls;
	protected Cursor cursor;
	protected Vector3 cursorPos;
	protected ArrayList<SubWeapon> subWeapons;
	protected int selectedWeapon;
	protected int playerNumber;
	protected Customization custom;

	private final float TREAD_VOLUME = 0.2f;
	private final float ENGINE_VOLUME = 0.6f;
    private final float SHOOT_VOLUME = 0.6f;

	private MediaSound engine_sound = new MediaSound(Assets.manager.get(Assets.tank_engine), ENGINE_VOLUME);
	private MediaSound tread_sound = new MediaSound(Assets.manager.get(Assets.tank_tread), TREAD_VOLUME);
	private MediaSound shoot_sound = new MediaSound(Assets.manager.get(Assets.bullet_fire), SHOOT_VOLUME);;

	private boolean treadSoundOn = false;
	private boolean engineSoundOn = false;

	private final int SOME_CONSTANT = 1000;
	private final int THRESH = 20;

	protected float reloadTime;

	public PlayerTank(int playerNumber) {
		super(0, 0); // defaults
		this.playerNumber = playerNumber;
		initializeStats();
		controls = ControlConstants.getPlayerControls(playerNumber);
		reloadTime = 0;
		selectedWeapon = 0;
		cursor = new Cursor();
		cursorPos = new Vector3(0, 0, 0);
		super.setGunOffsetX(-12);
		super.setGunPivotX(treadTexture.getWidth() / 2 + super.getGunOffsetX());
		setWidth(80);
		setHeight(90);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}

	public PlayerTank(int playerNumber, float x, float y) {
		super(x, y);
		this.playerNumber = playerNumber;
		initializeStats();
		controls = ControlConstants.getPlayerControls(playerNumber);
		reloadTime = 0;
		selectedWeapon = 0;
		cursor = new Cursor();
		cursorPos = new Vector3(0, 0, 0);
		super.setGunOffsetX(-12);
		super.setGunPivotX(treadTexture.getWidth() / 2 - super.getGunOffsetX());
		setWidth(80);
		setHeight(90);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
	}

	protected void initializeStats() {
		stats.addStat("Friction", 95); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 1200);
		stats.addStat("Angular_Friction", 98);
		stats.addStat("Angular_Acceleration", 300);
		stats.addStat("Rate_Of_Fire", 1);
	}

	public void setMapPosition(int row, int col) {
		int x = col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		int y = row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		super.setPosition(x, y);
	}
	
	public void centerCursor() {
		Vector2 screenCoor = getStage().stageToScreenCoordinates(new Vector2(getX(), getY()));
		cursorPos = new Vector3(screenCoor.x, screenCoor.y, 0);
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
			super.applyAngularForce(delta * stats.getStatValue("Angular_Acceleration"));
		} else if (controls.rightPressed()) {
			super.applyAngularForce(-1 * delta * stats.getStatValue("Angular_Acceleration"));
		}
		super.applyFriction(delta);
		super.move(delta);
		// Gun Pointing
		if ((cursorPos = controls.getCursor(cursorPos)) != null) {
			Vector3 cursorPos = getStage().getCamera().unproject(this.cursorPos.cpy()); // to world coordinates
			cursorPos.x = MathUtils.clamp(cursorPos.x, getStage().getCamera().frustum.planePoints[0].x, getStage().getCamera().frustum.planePoints[2].x);
			cursorPos.y = MathUtils.clamp(cursorPos.y, getStage().getCamera().frustum.planePoints[0].y, getStage().getCamera().frustum.planePoints[2].y);
			cursor.setPosition(cursorPos.x, cursorPos.y);
			super.pointGunToPoint(cursorPos.x, cursorPos.y);
		}
		// Firing
		if (controls.firePressed() && reloadTime < 0.01) { // if almsot done reloading, allow for rounding
			reloadTime = 1.0f / stats.getStatValue("Rate_Of_Fire");
			shoot();
		} else if (reloadTime > 0) {
			reloadTime -= delta;
		}

		playSoundEffects();
	}

    public void playSoundEffects() {
        if (!engineSoundOn) {
            engine_sound.play();
            engine_sound.loop();
            engineSoundOn = true;
        }

        if (getVelocity().len() > 0 && !treadSoundOn) {
            tread_sound.play();
            tread_sound.loop();
            tread_sound.setVolume((getVelocity().len()) / (getVelocity().len() + SOME_CONSTANT));
            treadSoundOn = true;
        }
        else if (treadSoundOn) {
            tread_sound.setVolume((getVelocity().len()) / (getVelocity().len() + SOME_CONSTANT));
        }

        if (getVelocity().len() <= THRESH) {
            treadSoundOn = false;
            tread_sound.stop();
        }
    }

	@Override
	public void draw(Batch batch, float a) {
		treadTexture = custom.getTexture("tread");
		gunTexture = custom.getTexture("gun");
		super.draw(batch, a);
		cursor.draw(batch, a);
	}

	public void shoot() {
		Vector2 v = new Vector2(TANK_GUN_LENGTH, 0);
		v.setAngle(getGunRotation());
		getStage().addActor(new Bullet(this, getX() + v.x, getY() + v.y, super.gunRotation));
		shoot_sound.play();
	}

	public void switchWeapon(int direction) {
		selectedWeapon += direction;
	}

	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(angle);
		f[0] = x+ v.x;
		f[1] = y +v.y;
		v.rotate(180-2*angle);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(2*angle);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(180-2*angle);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}
	
	public void setCustom(Customization custom) {
		this.custom = custom;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public float getReloadTime() {
		return reloadTime;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
