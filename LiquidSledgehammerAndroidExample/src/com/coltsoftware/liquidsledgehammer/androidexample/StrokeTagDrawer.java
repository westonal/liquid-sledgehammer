package com.coltsoftware.liquidsledgehammer.androidexample;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public final class StrokeTagDrawer implements TagDrawer {

	private final Paint paint = new Paint();

	@Override
	public void drawTag(Canvas canvas, Rect area, Object tag) {
		paint.setColor(0xff0000ff);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(area, paint);
	}

}
