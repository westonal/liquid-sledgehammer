package com.coltsoftware.liquidsledgehammer.androidexample;

import java.util.Random;

import com.coltsoftware.rectangleareagraph.RectangleSplit;

public class FakeDataSource implements GraphDataSource {

	private final Random rand = new Random();

	@Override
	public RectangleSplit<Object> getData(Object tag) {
		RectangleSplit<Object> rectangleSplit = new RectangleSplit<Object>();
		int objects = rand.nextInt(5) + 3;
		for (int i = 0; i < objects; i++)
			rectangleSplit.addValue(rand.nextInt(100) + 5, "" + i);
		return rectangleSplit;
	}

}
