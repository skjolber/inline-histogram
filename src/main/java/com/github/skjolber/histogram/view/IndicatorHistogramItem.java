package com.github.skjolber.histogram.view;

public class IndicatorHistogramItem implements HistogramItem {

	private static IndicatorHistogramItem INSTANCE = new IndicatorHistogramItem();
	
	public static IndicatorHistogramItem getInstance() {
		return INSTANCE;
	}

	@Override
	public void append(StringBuilder builder) {
		builder.append('⫯');
	}
}
