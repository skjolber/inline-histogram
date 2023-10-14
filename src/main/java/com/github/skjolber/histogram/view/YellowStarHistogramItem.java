package com.github.skjolber.histogram.view;

public class YellowStarHistogramItem implements HistogramItem {

	private static YellowStarHistogramItem INSTANCE = new YellowStarHistogramItem();
	
	public static YellowStarHistogramItem getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void append(StringBuilder builder) {
		builder.append('⭐');
	}

}
