package com.github.skjolber.histogram.view;

import com.github.skjolber.histogram.Histogram;

public class HistogramCharSequenceFactory {
	
	
	private static final long LEVELS = 9;
	
	private static char[] SCHEME = new char[] {
			
			' ',
			'▁',
			'▂',
			'▃',
			'▄', 
			'▅',
			'▆',
			'▇',
			'█',
	};
	
	private static final ValueHistogramItem[] ITEMS;

	static {
		
		ValueHistogramItem[] items = new ValueHistogramItem[SCHEME.length];
		for(int i = 0; i < items.length; i++) {
			items[i] = new ValueHistogramItem(SCHEME[i]);
		}
		ITEMS = items;
	}
	
	private static final LabelHistogramItem SPACE = new LabelHistogramItem(" ");
	
	public static HistogramCharSequence newInstance(Histogram histogram) {
		return newInstance(histogram, -1);
	}

	public static HistogramCharSequence newInstance(Histogram histogram, int highlightIndex) {

		int labelSize = 0;
		
		String startLabel = histogram.getLabel(0);
		if(startLabel != null) {
			labelSize += startLabel.length() + 1;
		}
		String endLabel = histogram.getLabel(histogram.size() - 1);
		
		if(endLabel != null) {
			labelSize += endLabel.length() + 1;
		}
		
		HistogramCharSequence result = new HistogramCharSequence(histogram.size() + labelSize);
		if(startLabel != null) {
			result.add(startLabel);
			result.add(SPACE);
		}
		
		result.add(LeftDividerHistogramItem.getInstance());

		long maxValue = histogram.getMaxValue();
		
		// ' ', '▁', '▂', '▃',  '▄',  '▅',  '▆',  '▇',  '█'
		//
		// Normalize: 8 real levels, plus space. Each real level is 2 pixels higher than the previous. 
		// 
		// Lets normalize by 9 and set limits as follows:
		//
		//   0
		// ▁ 1
		// ▂ 2
		// ▃ 3
		// ▄ 4
		// ▅ 5
		// ▆ 6
		// ▇ 7
		// █ 8
		//
		
		if(maxValue > 0) {
			for(int i = 0; i < histogram.size(); i++) {
				int index = (int)Math.min(SCHEME.length - 1, (LEVELS * histogram.getValue(i)) / maxValue);
	
				if(highlightIndex == i) {
					result.add(YellowStarHistogramItem.getInstance());
				}
				
				result.add(ITEMS[index]);
			}
		} else {
			for(int i = 0; i < histogram.size(); i++) {
				if(highlightIndex == i) {
					result.add(YellowStarHistogramItem.getInstance());
				}
				
				result.add(ITEMS[0]);
			}
		}
		
		if(highlightIndex >= histogram.size()) {
			result.add(YellowStarHistogramItem.getInstance());
		}
		
		result.add(RightDividerHistogramItem.getInstance());
		
		if(endLabel != null) {
			result.add(SPACE);
			result.add(endLabel);
		}
		
		return result;
	}
}
