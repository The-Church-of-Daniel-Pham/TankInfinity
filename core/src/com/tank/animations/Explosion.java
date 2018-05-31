package com.tank.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.utils.Assets;

public class Explosion extends Actor {
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private static final float FRAME_DURATION = 0.025F;
    private float x, y;

    Animation<TextureRegion> explosionAnimation;
    Texture explosionSheet;

    float stateTime;

    public Explosion(float x, float y) {
        explosionSheet = Assets.manager.get(Assets.explosionSheet);
        stateTime = 0f;
        this.x = x;
        this.y = y;
        create();
    }

    @Override
    public void act(float delta) {

    }

    public void create() {
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }

        explosionAnimation = new Animation<TextureRegion>(FRAME_DURATION, explosionFrames);

    }

    public void draw(Batch spriteBatch, float a) {
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
        spriteBatch.draw(currentFrame, x, y);
    }

    public void dispose() {
        explosionSheet.dispose();
    }
}