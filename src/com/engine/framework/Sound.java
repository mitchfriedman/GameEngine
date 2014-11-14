package com.engine.framework;


public interface Sound {

    public void dispose(int sound);
	public int play(float volume, int playTimes);
	public void autoPause();
	public void Release();
}
