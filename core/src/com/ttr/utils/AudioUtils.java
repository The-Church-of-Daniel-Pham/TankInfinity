package com.ttr.utils;

import com.badlogic.gdx.audio.Sound;

public class AudioUtils {
	
	public static void fade0ut(final Sound sound, final float vol, final long time, final long id)
	{	
		new Thread() {
			@Override
			public void run() {
				long startTime = System.nanoTime();
				while(System.nanoTime() - startTime <= time)
				{
					float ratio = Math.max((float)(time - (System.nanoTime() - startTime)) / time, 0);
					sound.setVolume(id, vol * ratio);
				}
				sound.stop();
			}
		}.start();	
	}
}
