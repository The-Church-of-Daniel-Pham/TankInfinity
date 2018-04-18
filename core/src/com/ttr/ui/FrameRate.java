package com.ttr.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * A nicer class for showing framerate that doesn't spam the console
 * like Logger.log()
 * 
 * @author William Hartman
 */
public class FrameRate{
    private static long lastTimeCounted = TimeUtils.millis();
    private static float sinceChange;
    private static float frameRate;
    private static BitmapFont font = new BitmapFont();
    private static SpriteBatch batch = new SpriteBatch();

    private static void update() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            frameRate = Gdx.graphics.getFramesPerSecond();
        }
    }

    public static void render() {
        update();
    	batch.begin();
        font.draw(batch, (int)frameRate + " fps", 0, Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    public static void dispose() {
        font.dispose();
        batch.dispose();
    }
}
