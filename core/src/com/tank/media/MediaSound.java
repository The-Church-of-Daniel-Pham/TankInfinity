package com.tank.media;

import com.badlogic.gdx.audio.Sound;

public class MediaSound {

    private Sound sound;

    public MediaSound(Sound sound) {
        this.sound = sound;
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

    public void play() {
        sound.play();
    }

    public void play(float vol) {
        sound.play(vol);
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
        sound.setVolume(id, vol);
    }

    public void stop() {
        sound.stop();
    }

    public void stop(long id) {
        sound.stop(id);
    }

}
