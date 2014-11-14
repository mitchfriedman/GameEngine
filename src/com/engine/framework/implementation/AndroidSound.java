package com.engine.framework.implementation;

import android.media.SoundPool;

import com.engine.framework.Sound;

public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }
    public void Release() {
    	soundPool.release();
    }
    @Override
    public int play(float volume, int playTimes) {
        return soundPool.play(soundId, volume, volume, 0, playTimes, 1);
    }
    public void autoPause() {
    	soundPool.autoPause();
    }
    @Override
    public void dispose(int soundID) {
    	soundPool.pause(soundID);
    	//soundPool.release();
    	//soundPool.stop(soundID);
    	//soundPool.unload(soundID);
    }
}
