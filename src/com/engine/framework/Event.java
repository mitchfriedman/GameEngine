package com.engine.framework;

import java.util.List;

public class Event {
	
	public final String name;
	public final float triggerTime_s;
	public final List<String> attributes;
	
	public Event(String name, float triggerTime_s, List<String> attributes)
	{
		this.name = name;
		this.triggerTime_s = triggerTime_s;
		this.attributes = attributes;
	}
	
}
