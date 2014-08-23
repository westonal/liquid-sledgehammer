package com.coltsoftware.liquidsledgehammer.androidexample;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public final class BasicToStringTagDrawer implements TagDrawer {

	private final Paint textPaint = new Paint();
	private final Rect textBounds = new Rect();

	@Override
	public void drawTag(Canvas canvas, Rect area, Object tag) {
		String text = tag.toString();
		textPaint.getTextBounds(text, 0, text.length(), textBounds);
		boolean rotateText = area.width() < textBounds.width();
		if (rotateText)
			canvas.rotate(-90, area.exactCenterX(),
					area.exactCenterY());
		canvas.drawText(text,
				area.exactCenterX() - textBounds.exactCenterX(),
				area.exactCenterY() - textBounds.exactCenterY(),
				textPaint);
	}

}
