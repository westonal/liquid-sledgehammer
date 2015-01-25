package com.coltsoftware.liquidsledgehammer.androidexample;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface TagDrawer {

	void drawTag(Canvas canvas, Rect area, Object tag);
}
