package com.github.skjolber.histogram.view;

public class ValueHistogramItem implements HistogramItem {

	private static char[] INDICATORS = new char[] {
			' ', '▁', '▂', '▃',  '▄',  '▅',  '▆',  '▇',  '█'
	};

	private static ValueHistogramItem[] VALUES;
	
	static {
		ValueHistogramItem[] values = new ValueHistogramItem[INDICATORS.length];
		
		for(int i = 0; i < values.length; i++) {
			values[i] = new ValueHistogramItem(i);
		}
		
		ValueHistogramItem.VALUES = values;
	}
	
	public static ValueHistogramItem get(int value) {
		return VALUES[value];
	}
	
	private final char value;
	
	public ValueHistogramItem(char value) {
		this.value = value;
	}
	
	public ValueHistogramItem(int value) {
		this.value = INDICATORS[value];
	}
	
	@Override
	public void append(StringBuilder builder) {
		builder.append(value);
	}


}
