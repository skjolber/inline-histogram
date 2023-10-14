package com.github.skjolber.histogram;

public interface Histogram {

	long getValue(int index);
	
	String getLabel(int index);

	public int size();

	long getMaxValue();
	
}
