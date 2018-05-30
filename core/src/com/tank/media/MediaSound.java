package com.tank.media;

/**
 * @author Gokul Swaminathan
 * @version 5.29.18
 */

import com.badlogic.gdx.audio.Sound;

public class MediaSound {

    private Sound sound;
    private float volume;

    public MediaSound(Sound sound, float volume) {
        this.sound = sound;
        this.volume = volume;
    }

    public void dispose() {
        sound.dispose();
    }

    public long loop() {
        return sound.loop();
    }

    public long loop(float vol) {
        return sound.loop(vol);
    }

    public void pause() {
        sound.pause();
    }

    public void pause(long id) {
        sound.pause(id);
    }

    public long play() {
        return sound.play(volume);
    }

    public void resume() {
        sound.resume();
    }

    public void resume(long id) {
        sound.resume(id);
    }

    public void setLooping(long id, boolean isLooping) {
        sound.setLooping(id, isLooping);
    }

    public void setVolume(long id, float vol) {

        volume = vol;
        sound.setVolume(id, vol);
    }

    public void stop() {
        sound.stop();
    }

    public void stop(long id) {
        sound.stop(id);
    }

    public void fade(final long time) {
        new Thread() {
            @Override
            public void run() {
                long id = play();
                long startTime = System.nanoTime();
                while (System.nanoTime() - startTime <= time) {
                    float ratio = Math.max((float) (time - (System.nanoTime() - startTime)) / time, 0);
                    setVolume(id, volume * ratio);
                }
                stop();
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
}
