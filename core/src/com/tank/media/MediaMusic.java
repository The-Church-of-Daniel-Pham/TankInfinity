package com.tank.media;

/**
 * @author Gokul Swaminathan
 * @version 5.29.18
 */

import com.badlogic.gdx.audio.Music;

public class MediaMusic {

    private Music music;
    private float volume;

    public MediaMusic(Music music, float volume) {
        this.music = music;
        this.volume = volume;
    }

    public void dispose() {
        music.dispose();
    }

    public float getVolume() {
        return volume;
    }

    public boolean isLooping() {
        return music.isLooping();
    }

    public void play() {
        music.play();
    }

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
                stop();
            }
        }.start();
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
