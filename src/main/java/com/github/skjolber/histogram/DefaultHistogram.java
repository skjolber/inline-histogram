package com.github.skjolber.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultHistogram implements Histogram {

	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static class Builder {

		private static class Item implements Comparable<Item> {
		
			public Item(long value, long limit, String label) {
				super();
				this.value = value;
				this.limit = limit;
				this.label = label;
			}
			
			long value;
			long limit;
			String label;
			
			@Override
			public int compareTo(Item o) {
				return Long.compare(limit, o.limit);
			}
		}
		
		private List<Item> items = new ArrayList<>();
		
		public Builder add(String label, long limit, long value) {
			items.add(new Item(value, limit, label));
			return this;
		}
		
		public Builder add(long limit, String label) {
			items.add(new Item(0, limit, label));
			return this;
		}
		
		public Builder add(long limit) {
			items.add(new Item(0, limit, null));
			return this;
		}
		
		public DefaultHistogram build() {
			long[] limits = new long[items.size()];
			String[] labels = new String[items.size()];
			long[] initialValues = new long[items.size()];
			
			Collections.sort(items);
			
			for(int i = 0; i < items.size(); i++) {
				Item item = items.get(i);
				limits[i] = item.limit;
				labels[i] = item.label;
				initialValues[i] = item.value;
			}
			return new DefaultHistogram(limits, labels, initialValues);
		}
	}
	
	private final AtomicLong[] values;
	private final long[] limits;
	private final String[] labels;

	public DefaultHistogram(long[] limits, String[] labels, long[] initialValues) {
		this.limits = limits;
		this.labels = labels;
		this.values = new AtomicLong[labels.length];
		for(int i = 0; i < labels.length; i++) {
			values[i] = new AtomicLong(initialValues[i]);
		}
	}
	
	public long getValue(int index) {
		return values[index].get();
	}
	
	public long[] getLimits() {
		return limits;
	}
	public String getLabel(int index) {
		return labels[index];
	}

	public int size() {
		return values.length;
	}

	public long getMaxValue() {
		long maxValue = 0;
		for (AtomicLong atomicInteger : values) {
			
			long value = atomicInteger.get();
			if(maxValue < value) {
				maxValue = value;
			}
			
		}
		return maxValue;
	}
	
	public void incrementByIndex(int index) {
		values[index].incrementAndGet();
	}
	
	public void incrementByIndex(int index, long delta) {
		values[index].addAndGet(delta);
	}

	public int incrementByValue(long value) {
		int result = Arrays.binarySearch(limits, value);
		if(result < 0) {
			// result is negative insertion point
			int index = - result - 1;
			if(index >= values.length) {
				// constrain to last bucket
				index = values.length - 1;
			}
			values[index].incrementAndGet();
			
			return index;
		} else {
			// result is index
			values[result].incrementAndGet();
		}
		return result;
	}
	
	public void reset(long base) {
		for(int i = 0; i < values.length; i++) {
			values[i].set(base);
		}
	}

}
