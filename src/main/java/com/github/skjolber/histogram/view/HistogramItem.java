package com.github.skjolber.histogram.view;

public interface HistogramItem {

	void append(StringBuilder builder);
	
	default int length() {
		return 1;
	}
}
