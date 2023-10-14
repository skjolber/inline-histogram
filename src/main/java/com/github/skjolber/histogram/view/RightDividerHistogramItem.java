package com.github.skjolber.histogram.view;

public class RightDividerHistogramItem implements HistogramItem {

	private static RightDividerHistogramItem INSTANCE = new RightDividerHistogramItem();
	
	public static RightDividerHistogramItem getInstance() {
		return INSTANCE;
	}

	@Override
	public void append(StringBuilder builder) {
		builder.append('▕');
	}

}
