package com.tank.media;

/**
 * @author Gokul Swaminathan
 * @version 5.29.18
 */

import com.badlogic.gdx.audio.Sound;

public class MediaSound {

    private Sound sound;
    private float volume;
    private long mediaId;
    private static boolean muted = true;

    public MediaSound(Sound sound, float volume) {
        this.sound = sound;
        this.volume = volume;
    }

    public void dispose() {
    	stop();
        sound.dispose();
    }

    public long loop() {
    	if (!muted) {
    		mediaId = sound.loop();
    	}
        return mediaId;
    }

    public long loop(float vol) {
    	if (!muted) {
	        volume = vol;
	        mediaId = sound.loop(vol);
    	}
        return mediaId;
    }

    public void pause() {
        sound.pause();
    }

    public long play() {
    	if (!muted) {
    		mediaId = sound.play(volume);
    	}
        return mediaId;
    }

    public void resume() {
        sound.resume();
    }

    public void setLooping(boolean isLooping) {
        sound.setLooping(mediaId, isLooping);
    }

    public void setVolume(float vol) {
        volume = vol;
        sound.setVolume(mediaId, vol);
    }

    public void stop() {
        sound.stop();
    }

    public MediaSound itself() {
        return this;
    }

    public void fade(final long time) {
        new Thread() {
            @Override
            public void run() {
                play();
                long startTime = System.nanoTime();
                while (System.nanoTime() - startTime <= time) {
                    float ratio = Math.max((float) (time - (System.nanoTime() - startTime)) / time, 0);
                    setVolume(volume * ratio);
                }
                itself().stop();
            }
        }.start();
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public float getVolume() {
        return volume;
    }

    public long getMediaId() {
        return mediaId;
    }
}
