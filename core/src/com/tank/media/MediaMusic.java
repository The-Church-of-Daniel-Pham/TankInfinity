package com.tank.media;

import com.badlogic.gdx.audio.Music;

/**
 * @author Daniel P., Samuel H., Edmond F., Gokul S.
 * @description Wrapper class for the music interface.
 */
public class MediaMusic {

    private Music music;	//music instance variable
    private float volume;	//volume instance variable

    private static float globalSoundVolume = 1.0f;	//volume of the music object
    
    public MediaMusic(Music music, float volume) {
        this.music = music;
        this.volume = volume;
    }

    /**
     * Disposes the music object
     */
    public void dispose() {
        music.dispose();
    }
    
    /**
     * @return volume of the music object
     */
    public float getVolume() {
        return volume;
    }

    /**
     * @return is the music looping?
     */
    public boolean isLooping() {
        return music.isLooping();
    }

    /**
     * play the music
     */
    public void play() {
        music.play();
    }

    /**
     * @return is the music being played?
     */
    public boolean isPlaying() {
        return music.isPlaying();
    }

    public void setLooping(boolean isLooping) {
        music.setLooping(isLooping);
    }

    public void setCompletionListener(Music.OnCompletionListener listener) {
        music.setOnCompletionListener(listener);
    }

    public void setPosition(float pos) {
        music.setPosition(pos);
    }

    public void setVolume(float vol) {
        volume = vol;
        music.setVolume(volume);
    }

    public MediaMusic itself()
    {
        return this;
    }

    public void stop() {
        music.stop();
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

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
    
    public static float getGlobalVolume() {
    	return globalSoundVolume;
    }
    
    public static void setGlobalVolume(float vol) {
    	globalSoundVolume = vol;
    	//updateLoopingSounds();
    }
}
