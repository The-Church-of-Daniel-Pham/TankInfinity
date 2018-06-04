package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtilleryShell extends Actor{
	
	protected static Texture artilleryShell =  Assets.manager.get(Assets.artilleryShell);
	protected static Texture artillerySheet =  Assets.manager.get(Assets.air_bomb);
	protected static Animation<TextureRegion> artilleryAnimation;
	protected static final int FRAMES_ROWS = 4;
	protected static final int FRAMES_COLS = 4;
	protected static final int FPS = 30;
	protected static final int FRAME_WIDTH = artillerySheet.getWidth() / FRAMES_COLS;
	protected static final int FRAME_HEIGHT = artillerySheet.getHeight() / FRAMES_ROWS;
	
	static {
		TextureRegion[][] textureRegions = TextureRegion.split(artillerySheet,
				artillerySheet.getWidth() / FRAMES_COLS,
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
	
	
	protected AbstractVehicle source;
	protected Stats stats;
	protected Vector2 velocity;
	protected float timeUntilHit;
	protected float timePassed;
	
	public ArtilleryShell(AbstractVehicle src, Stats stats, float x, float y) {
		source = src;
		this.stats = stats;
		timeUntilHit = 1.0f;
		timePassed = (float)(Math.random());
		Vector2 randomDistance = new Vector2(128, 0);
		randomDistance.rotate((float)(Math.random() * 360));
		setX(x + randomDistance.x);
		setY(y + randomDistance.y);
		velocity = randomDistance.cpy().rotate(180).scl(0.5f);
		setRotation(velocity.angle());
		setOriginX(FRAME_WIDTH / 2);
		setOriginY(FRAME_HEIGHT / 2);
	}
	
	@Override
	public void act(float delta) {
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
		timeUntilHit -= delta;
		timePassed += delta;
		if (timeUntilHit <= 0f) {
			getStage().addActor(new DamageExplosion(source, stats, getX(), getY()));
			remove();
		}
		else {
			setScale((2.5f * timeUntilHit) + 1.0f);
		}
	}
	
	@Override
	public void draw(Batch batch, float a) {
		TextureRegion currentFrame = artilleryAnimation.getKeyFrame(timePassed, true);
		batch.draw(currentFrame, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), FRAME_WIDTH, FRAME_HEIGHT, super.getScaleX(), super.getScaleY(), getRotation());
		//drawVertices(batch, a);
	}
}
