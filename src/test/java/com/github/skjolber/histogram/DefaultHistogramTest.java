package com.github.skjolber.histogram;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultHistogramTest {

	@Test
	public void testIncrementByValue() {
		long[] limits = new long[] {
				250, 500, 750, 1000, 
				1250, 1500, 1750, 2000, 
				2250, 2500,
		};		
		
		String[] labels = new String[limits.length];
		labels[0] = "0";
		labels[labels.length - 1] = "2.5s";
		
		DefaultHistogram histogram = new DefaultHistogram(limits, labels, new long[limits.length]);
		
		histogram.incrementByValue(125);
		long value = histogram.getValue(0);
		assertEquals(1, value);
	}
	
	@Test
	public void testIncrementByIndex() {
		long[] limits = new long[] {
				250, 500, 750, 1000, 
				1250, 1500, 1750, 2000, 
				2250, 2500,
		};		
		
		String[] labels = new String[limits.length];
		labels[0] = "0";
		labels[labels.length - 1] = "2.5s";
		
		DefaultHistogram histogram = new DefaultHistogram(limits, labels, new long[limits.length]);
		
		histogram.incrementByIndex(4);
		long value = histogram.getValue(4);
		assertEquals(1, value);
	}

}
