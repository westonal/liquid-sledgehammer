package com.coltsoftware.liquidsledgehammer.androidexample;

import java.util.Random;

import com.coltsoftware.rectangleareagraph.RectangleSplit;

public class FakeDataSource implements GraphDataSource {

	private final Random rand = new Random(123);

	@Override
	public RectangleSplit<Object> getData(Object tag) {
		RectangleSplit<Object> rectangleSplit = new RectangleSplit<Object>();
		int objects = rand.nextInt(50) + 3;
		for (int i = 0; i < objects; i++)
			rectangleSplit.addValue(rand.nextInt(100) + 5, new Item(i,
					randomColor()));
		return rectangleSplit;
	}

	private int randomColor() {
		return 0xff000000 + rand.nextInt(0xffffff);
	}

	public class Item {

		private final String str;
		private final int color;

		public Item(int i, int color) {
			this.color = color;
			str = "" + i;
		}

		@Override
		public String toString() {
			return str;
		}

		public int getColor() {
			return color;
		}
	}

}
