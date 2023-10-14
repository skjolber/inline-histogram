package com.github.skjolber.histogram.view;

public class LeftDividerHistogramItem implements HistogramItem {
	
	private static LeftDividerHistogramItem INSTANCE = new LeftDividerHistogramItem();
	
	public static LeftDividerHistogramItem getInstance() {
		return INSTANCE;
	}

	@Override
	public void append(StringBuilder builder) {
		builder.append('▏');
	}

}
