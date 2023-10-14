package com.github.skjolber.histogram.view;

import java.util.List;

public class HistogramCharSequence implements CharSequence {

	private StringBuilder builder;
	
	public HistogramCharSequence(List<HistogramItem> items) {
		super();
		this.builder = new StringBuilder(items.size());
		for (HistogramItem histogramItem : items) {
			histogramItem.append(builder);
		}
	}
	
	public HistogramCharSequence(CharSequence charSequence) {
		super();
		this.builder = new StringBuilder(charSequence.length());
		this.builder.append(charSequence);
	}
	
	public HistogramCharSequence(StringBuilder builder) {
		super();
		this.builder = builder;
	}

	public HistogramCharSequence(int length) {
		this(new StringBuilder(length));
	}
	
	public CharSequence toCharSequence() {
		return builder;
	}

	public boolean add(HistogramItem e) {
		e.append(builder);
		return true;
	}
	
	public boolean add(CharSequence e) {
		builder.append(e);
		return true;
	}

	@Override
	public int length() {
		return builder.length();
	}

	@Override
	public char charAt(int index) {
		return builder.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return builder.subSequence(start, end);
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
	
}
