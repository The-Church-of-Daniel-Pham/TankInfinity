/**
 * Author: Daniel P., Samuel H., Edmond F., Gokul S.
 * Description: Used as a subweapon of tanks, 
 * the artillery shell allows placement of a delayed 
 * explosion onto the level's map.
 */
package com.tank.actor.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.media.MediaSound;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtilleryShell extends Actor {

	/**
	 * The texture which contains every frame of the artillery animation
	 */
	protected static Texture artillerySheet = Assets.manager.get(Assets.air_bomb);
	/**
	 * Animation for the artillery
	 */
	protected static Animation<TextureRegion> artilleryAnimation;
	/**
	 * number of rows in the texture sheet
	 */
	protected static final int FRAMES_ROWS = 4;
	/**
	 * number of columns in the texture sheet
	 */
	protected static final int FRAMES_COLS = 4;
	/**
	 * framerate for the animation
	 */
	protected static final int FPS = 30;
	/**
	 * used to properly size each frame from the texture sheet
	 */
	protected static final int FRAME_WIDTH = artillerySheet.getWidth() / FRAMES_COLS;
	/**
	 * used to properly size each frame from the texture sheet
	 */
	protected static final int FRAME_HEIGHT = artillerySheet.getHeight() / FRAMES_ROWS;

	// create the array used to animate the artillery
	static {
		TextureRegion[][] textureRegions = TextureRegion.split(artillerySheet, artillerySheet.getWidth() / FRAMES_COLS,
				artillerySheet.getHeight() / FRAMES_ROWS);

		TextureRegion[] explosionFrames = new TextureRegion[FRAMES_COLS * FRAMES_ROWS];
		int index = 0;
		for (int i = 0; i < FRAMES_ROWS; i++) {
			for (int j = 0; j < FRAMES_COLS; j++) {
				explosionFrames[index++] = textureRegions[i][j];
			}
		}

		artilleryAnimation = new Animation<TextureRegion>(1.0f / FPS, explosionFrames);
	}
	/**
	 * The AbstractVehicle that fired the artillery shell
	 */
	protected AbstractVehicle source;
	/**
	 * The stats of the projectile, which includes damage and such
	 */
	protected Stats stats;
	/**
	 * The velocity of the projectile, giving direction and speed
	 */
	protected Vector2 velocity;
	/**
	 * The delay of the explosion
	 */
	protected float timeUntilHit;
	/**
	 * Used to calculate if delay is over
	 */
	protected float timePassed;
	/**
	 * The volume of the sound
	 */
	private static final float FALL_VOLUME = 0.6f;
	/**
	 * The falling sound
	 */
	private MediaSound fallSound = new MediaSound(Gdx.audio.newSound(Gdx.files.internal(Assets.artillery_falling.fileName)), FALL_VOLUME);

	public ArtilleryShell(AbstractVehicle src, Stats stats, float x, float y) {
		source = src;
		this.stats = stats;
		timeUntilHit = 1.0f;
		timePassed = (float) (Math.random());
		Vector2 randomDistance = new Vector2(128, 0);
		randomDistance.rotate((float) (Math.random() * 360));
		setX(x + randomDistance.x);
		setY(y + randomDistance.y);
		velocity = randomDistance.cpy().rotate(180).scl(0.5f);
		setRotation(velocity.angle());
		setOriginX(FRAME_WIDTH / 2);
		setOriginY(FRAME_HEIGHT / 2);
		fallSound.loop();
	}

	@Override
	/**
	 * moves the artillery shell for the illusion of perspective and checks if the
	 * explosion delay has been reached yet
	 */
	public void act(float delta) {
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
		timeUntilHit -= delta;
		timePassed += delta;
		if (timeUntilHit <= 0f) {
			getStage().addActor(new DamageExplosion(source, stats, getX(), getY()));
			fallSound.dispose();
			remove();
		} else {
			setScale((2.5f * timeUntilHit) + 1.0f);
		}
	}

	@Override
	/**
	 * draws and properly animates the artillery
	 */
	public void draw(Batch batch, float a) {
		TextureRegion currentFrame = artilleryAnimation.getKeyFrame(timePassed, true);
		batch.draw(currentFrame, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), FRAME_WIDTH, FRAME_HEIGHT, super.getScaleX(), super.getScaleY(),
				getRotation());
		// drawVertices(batch, a);
	}
}
