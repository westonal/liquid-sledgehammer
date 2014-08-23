package com.coltsoftware.liquidsledgehammer.androidexample;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public final class FillTagDrawer implements TagDrawer {

	private final Paint paint = new Paint();
	private int fillColor = 0xff00ff00;

	@Override
	public void drawTag(Canvas canvas, Rect area, Object tag) {
		paint.setColor(fillColor);
		paint.setStyle(Style.FILL);
		canvas.drawRect(area, paint);
	}

	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}

}
