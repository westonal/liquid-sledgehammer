package com.coltsoftware.liquidsledgehammer.androidexample;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.coltsoftware.rectangleareagraph.Rectangle;
import com.coltsoftware.rectangleareagraph.RectangleSplit;
import com.coltsoftware.rectangleareagraph.RectangleSplit.SplitResult;

public class RectDisplay extends View {

	private static final String TAG = "RectDisplay";
	private RectangleSplit<String> rectangleSplit;
	private List<SplitResult<String>> split;
	private Paint paint = new Paint();

	public RectDisplay(Context context) {
		super(context);
		init();
	}

	public RectDisplay(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RectDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		rectangleSplit = new RectangleSplit<String>();
		rectangleSplit.addValue(10, "A");
		rectangleSplit.addValue(20, "B");
		rectangleSplit.addValue(40, "C");
		rectangleSplit.addValue(70, "D");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xff00ff00);
		if (split == null)
			split = rectangleSplit.split(new Rectangle(0, 0, getWidth() - 1,
					getHeight() - 1));
		paint.setColor(0xff0000ff);
		paint.setStyle(Style.STROKE);
		for (SplitResult<String> rect : split)
			canvas.drawRect(toGraphicsRect(rect.getRectangle()), paint);
	}

	private Rect toGraphicsRect(Rectangle rectangle) {
		return new Rect(rectangle.getLeft(), rectangle.getTop(),
				rectangle.getWidth() + rectangle.getLeft(),
				rectangle.getHeight() + rectangle.getTop());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		split = null;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			SplitResult<String> findItemAt = findItemAt(x, y);
			if (findItemAt != null)
				Log.d(TAG,
						String.format("Clicked %s (%s)",
								findItemAt.getRectangle(), findItemAt.getTag()));
			return true;
		}
		return super.onTouchEvent(event);
	}

	private SplitResult<String> findItemAt(int x, int y) {
		if (split == null)
			return null;
		for (SplitResult<String> rect : split)
			if (rect.getRectangle().inside(x, y))
				return rect;
		return null;
	}

}
