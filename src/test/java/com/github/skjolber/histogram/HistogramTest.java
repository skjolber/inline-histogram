package com.github.skjolber.histogram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.github.skjolber.histogram.DefaultHistogram.Builder;
import com.github.skjolber.histogram.view.HistogramCharSequence;
import com.github.skjolber.histogram.view.HistogramCharSequenceFactory;
import com.github.skjolber.histogram.view.LeftDividerHistogramItem;
import com.github.skjolber.histogram.view.RightDividerHistogramItem;
import com.github.skjolber.histogram.view.ValueHistogramItem;

public class HistogramTest {
	
	@Test
	public void testHistogram() {
		
		HistogramCharSequence histogram = new HistogramCharSequence(12);
		histogram.add(new LeftDividerHistogramItem());
		for(int i = 0; i <= 8; i++) {
			histogram.add(new ValueHistogramItem(i));
		}
		histogram.add(new RightDividerHistogramItem());
		
		assertEquals("▏ ▁▂▃▄▅▆▇█▕", histogram.toCharSequence().toString());
	}
	
	@Test
	public void testHistogramFactory() {

		long[] limits = new long[] {
				0, 250, 500, 750, 1000, 
				1250, 1500, 1750, 2000, 
				2250, 2500,
		};		
		
		String[] labels = new String[] {
				"0,", "250ms", "500ms", "750ms", "1s", "1.25s", "1.5s", "1.75s", "2s", "2.25s", "2.5s"};
		
		
		DefaultHistogram histogram = new DefaultHistogram(limits, labels, new long[limits.length]);
		
		Random random = new Random();
		
		for(int i = 0; i < 100; i++) {
			int nextInt = random.nextInt(labels.length);
			histogram.incrementByIndex(nextInt);
		}
		
		HistogramCharSequence sequence = HistogramCharSequenceFactory.newInstance(histogram);
		assertFalse(sequence.toString().contains("⭐"));
		
		HistogramCharSequence sequence2 = HistogramCharSequenceFactory.newInstance(histogram, 5);
		assertTrue(sequence2.toString().contains("⭐"));
	}

	@Test
	public void testHistogramBuilder1() {

		long[] limits = new long[] {
				0, 250, 500, 750, 1000, 
				1250, 1500, 1750, 2000, 
				2250, 2500,
		};		
		
		String[] labels = new String[] {
				"0", "250ms", "500ms", "750ms", "1s", "1.25s", "1.5s", "1.75s", "2s", "2.25s", "2.5s"};
		
		Builder newBuilder = DefaultHistogram.newBuilder();
		for(int i = 0; i < limits.length; i++) {
			newBuilder.add(limits[i], labels[i]);
		}
		
		DefaultHistogram histogram = newBuilder.build();
		
		Random random = new Random();
		
		for(int i = 0; i < 100; i++) {
			int nextInt = random.nextInt(labels.length);
			histogram.incrementByIndex(nextInt);
		}
		
		HistogramCharSequence sequence = HistogramCharSequenceFactory.newInstance(histogram);
		System.out.println(sequence);
		
		
		HistogramCharSequence sequence2 = HistogramCharSequenceFactory.newInstance(histogram, 5);
		System.out.println(sequence2);
	}

	@Test
	public void testHistogramBuilder2() {
		DefaultHistogram histogram = DefaultHistogram.newBuilder()
			.add(0, "0s")
			.add(250)
			.add(500)
			.add(750)
			.add(1000, "1000s")
			.build();
		
		histogram.incrementByIndex(0);
		histogram.incrementByValue(444);
		histogram.incrementByIndex(2, 3);
		histogram.incrementByIndex(3, 6);
		histogram.incrementByIndex(4, 7);
		
		HistogramCharSequence sequence = HistogramCharSequenceFactory.newInstance(histogram);
		
		String result = new String("200 OK https://...  " + sequence);
		assertEquals("200 OK https://...  0s ▏▁ ▅▇█▕ 1000s", result);
		
		assertEquals(sequence.length(), 2 + 2 + 5 + 2 + 5);
	}

}
