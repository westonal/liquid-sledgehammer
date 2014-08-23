package com.coltsoftware.liquidsledgehammer.androidexample;

import com.coltsoftware.rectangleareagraph.RectangleSplit;

public interface GraphDataSource {

	RectangleSplit<Object> getData(Object tag);

}
