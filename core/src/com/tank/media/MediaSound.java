package com.tank.media;

import java.util.ArrayList;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Daniel P., Samuel H., Edmond F., Gokul S.
 * @description Wrapper class for the sound interface.
 */
public class MediaSound {

    private Sound sound;
    private float volume;
    private long mediaId;
    
    private static ArrayList<MediaSound> loopingSounds = new ArrayList<MediaSound>();
    private static boolean muted = false;
    private static float globalSoundVolume = 1.0f;

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
    		mediaId = sound.loop(volume * globalSoundVolume);
    		if (!loopingSounds.contains(this)) loopingSounds.add(this);
    	}
        return mediaId;
    }

    public long loop(float vol) {
    	if (!muted) {
	        volume = vol;
	        mediaId = sound.loop(vol * globalSoundVolume);
	        if (!loopingSounds.contains(this)) loopingSounds.add(this);
    	}
        return mediaId;
    }

    public void pause() {
        sound.pause();
    }

    public long play() {
    	if (!muted) {
    		mediaId = sound.play(volume * globalSoundVolume);
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
        sound.setVolume(mediaId, vol * globalSoundVolume);
    }

    public void stop() {
        sound.stop();
        if (loopingSounds.contains(this)) loopingSounds.remove(this);
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
    
    public static void updateLoopingSounds() {
    	if (!muted) {
	    	int soundsCount = loopingSounds.size();
	    	for (int count = 0; count < soundsCount; count++) {
	    		MediaSound sound = loopingSounds.get(count);
	    		sound.setVolume(sound.getVolume());
	    	}
    	}
    	else {
    		while (loopingSounds.size() > 0) {
    			MediaSound sound = loopingSounds.get(0);
    			sound.stop();
    		}
    	}
    }
    
    public static float getGlobalVolume() {
    	return globalSoundVolume;
    }
    
    public static void setGlobalVolume(float vol) {
    	globalSoundVolume = vol;
    	updateLoopingSounds();
    }
}
