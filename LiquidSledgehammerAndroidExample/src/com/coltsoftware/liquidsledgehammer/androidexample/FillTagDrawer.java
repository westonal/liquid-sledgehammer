package com.coltsoftware.liquidsledgehammer.androidexample;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public class FillTagDrawer implements TagDrawer {

	private final Paint paint = new Paint();
	private int fillColor = 0xff00ff00;

	@Override
	public void drawTag(Canvas canvas, Rect area, Object tag) {
		paint.setStyle(Style.FILL);
		tagToPaint(tag, paint);
		canvas.drawRect(area, paint);
	}

	protected void tagToPaint(Object tag, Paint paint) {
		if (tag instanceof FakeDataSource.Item) {
			paint.setColor(((FakeDataSource.Item) tag).getColor());
		} else {
			paint.setColor(fillColor);
		}
	}

	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}

}
