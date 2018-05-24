package com.tank.media;

/**
 * @author Gokul Swaminathan
 * @version 5.24.18
 */

import com.badlogic.gdx.audio.Music;

public class MediaAudio {
    private Music music;

    public MediaAudio(Music music) {
        this.music = music;
    }

    public void dispose() {
        music.dispose();
    }

    public float getVolume() {
        return music.getVolume();
    }

    public boolean isLooping() {
        return music.isLooping();
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
        music.setVolume(vol);
    }

    public void stop() {
        music.stop();
    }
}
