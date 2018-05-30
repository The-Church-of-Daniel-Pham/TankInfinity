package com.tank.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.utils.Assets;

public class Explosion extends Actor
{
    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private static final float FRAME_DURATION = 0.025F;
    private float x, y;

    // Objects used
    Animation<TextureRegion> explosionAnimation; // Must declare frame type (TextureRegion)
    Texture explosionSheet;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public Explosion(float x, float y)
    {
        // Load the sprite sheet as a Texture
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

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        explosionAnimation = new Animation<TextureRegion>(FRAME_DURATION, explosionFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
    }

    public void draw(Batch spriteBatch, float a)
    {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
        spriteBatch.draw(currentFrame, x, y); // Draw current frame at (50, 50)
    }

    public void dispose() {
        explosionSheet.dispose();
    }

}