package com.github.skjolber.histogram.view;

public class LabelHistogramItem implements HistogramItem {

	private final String value;
	
	public LabelHistogramItem(String value) {
		this.value = value;
	}
	
	@Override
	public void append(StringBuilder builder) {
		builder.append(value);
	}

}
