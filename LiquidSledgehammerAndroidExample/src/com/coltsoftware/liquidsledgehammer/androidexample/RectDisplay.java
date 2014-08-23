package com.coltsoftware.liquidsledgehammer.androidexample;

import java.util.List;
import java.util.Random;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.coltsoftware.rectangleareagraph.Rectangle;
import com.coltsoftware.rectangleareagraph.RectangleSplit;
import com.coltsoftware.rectangleareagraph.RectangleSplit.SplitResult;

public class RectDisplay extends View {

	private static final String TAG = "RectDisplay";

	private class Split {
		public Split(List<SplitResult<Object>> split) {
			this.split = split;
		}

		private List<SplitResult<Object>> split;
		private int fillColor = 0xff00ff00;

		public void draw(Canvas canvas) {
			for (SplitResult<Object> rect : split) {
				paint.setColor(fillColor);
				paint.setStyle(Style.FILL);
				Rect graphicsRect = toGraphicsRect(rect.getRectangle());
				canvas.drawRect(graphicsRect, paint);
				paint.setColor(0xff0000ff);
				paint.setStyle(Style.STROKE);
				canvas.drawRect(graphicsRect, paint);
			}
		}

		public SplitResult<Object> findItemAt(int x, int y) {
			for (SplitResult<Object> rect : split)
				if (rect.getRectangle().inside(x, y))
					return rect;
			return null;
		}

		public void setFillColor(int fillColor) {
			this.fillColor = fillColor;
		}
	}

	private Split split;
	private SplitResult<Object> animating;
	private Split split2;
	private final Paint paint = new Paint();
	protected float blend;
	private ValueAnimator va;
	private final Matrix matrix = new Matrix();

	private GraphDataSource dataSource = new FakeDataSource();

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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xff00ff00);
		if (split == null) {
			split = calculateSplit();
			if (split == null)
				return;
		}
		split.draw(canvas);
		if (split2 != null && animating != null) {
			float invBlend = 1f - blend;
			Rectangle source = animating.getRectangle();
			float sx = source.getWidth() / (float) getWidth();
			float sy = source.getHeight() / (float) getHeight();
			matrix.setScale(sx * invBlend + blend, sy * invBlend + blend);
			matrix.postTranslate(source.getLeft() * invBlend, source.getTop()
					* invBlend);
			canvas.concat(matrix);
			split2.draw(canvas);
		}
	}

	private Split calculateSplit() {
		Object tag = null;
		RectangleSplit<Object> rectangleSplit = dataSource.getData(tag);
		Split split = new Split(rectangleSplit.split(new Rectangle(0, 0,
				getWidth() - 1, getHeight() - 1)));
		split.setFillColor(0xff000000 + new Random().nextInt(0xffffff));
		return split;
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
			return !isAnimating();
		}
		if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			if (isAnimating())
				return false;
			int x = (int) event.getX();
			int y = (int) event.getY();
			SplitResult<Object> findItemAt = findItemAt(x, y);
			if (findItemAt != null) {
				Log.d(TAG,
						String.format("Clicked %s (%s)",
								findItemAt.getRectangle(), findItemAt.getTag()));
				animating = findItemAt;
				split2 = calculateSplit();
				animateToNewItem();
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

	private boolean isAnimating() {
		return va != null && va.isRunning();
	}

	private void animateToNewItem() {
		if (va != null)
			va.cancel();
		va = ValueAnimator.ofFloat(0f, 1f);
		va.setInterpolator(new AccelerateInterpolator());
		va.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = ((Float) animation.getAnimatedValue())
						.floatValue();
				blend = value;
				invalidate();
			}
		});
		va.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				split = split2;
				split2 = null;
				invalidate();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		va.start();
	}

	private SplitResult<Object> findItemAt(int x, int y) {
		if (split == null)
			return null;
		return split.findItemAt(x, y);
	}

}
