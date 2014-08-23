package com.coltsoftware.liquidsledgehammer.androidexample;

import java.util.List;
import java.util.Random;
import java.util.Stack;

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
import android.view.animation.AccelerateDecelerateInterpolator;

import com.coltsoftware.rectangleareagraph.Rectangle;
import com.coltsoftware.rectangleareagraph.RectangleSplit;
import com.coltsoftware.rectangleareagraph.RectangleSplit.SplitResult;

public class RectDisplay extends View {

	private static final String TAG = "RectDisplay";

	private final Stack<Split> backStack = new Stack<RectDisplay.Split>();

	private TagDrawer tagDrawer = new BasicToStringTagDrawer();
	private TagDrawer strokeTagDrawer = new StrokeTagDrawer();
	private FillTagDrawer fillTagDrawer = new FillTagDrawer();

	private class Split {
		private SplitResult<Object> parent;

		public Split(SplitResult<Object> parent, List<SplitResult<Object>> split) {
			this.parent = parent;
			this.split = split;
		}

		private List<SplitResult<Object>> split;
		private int fillColor = 0xff00ff00;

		public void draw(Canvas canvas) {
			for (SplitResult<Object> rect : split) {
				Rectangle rectangle = rect.getRectangle();
				Rect graphicsRect = toGraphicsRect(rectangle);

				canvas.save();
				fillTagDrawer.setFillColor(fillColor);
				fillTagDrawer.drawTag(canvas, graphicsRect, rect.getTag());
				canvas.restore();

				canvas.save();
				tagDrawer.drawTag(canvas, graphicsRect, rect.getTag());
				canvas.restore();

				canvas.save();
				strokeTagDrawer.drawTag(canvas, graphicsRect, rect.getTag());
				canvas.restore();
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

	public void setDataSource(GraphDataSource dataSource) {
		if (this.dataSource == dataSource)
			return;
		this.dataSource = dataSource;
		split = null;
		split2 = null;
		if (va != null)
			va.cancel();
		invalidate();
	}

	private void init() {
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xff00ff00);
		if (split == null) {
			split = calculateSplit(null);
			if (split == null)
				return;
		}
		split.draw(canvas);
		if (split2 != null) {
			paint.setStyle(Style.FILL);
			paint.setARGB((int) (255 * blend), 0, 0, 0);
			canvas.drawPaint(paint);
			float invBlend = 1f - blend;
			Rectangle source = split2.parent.getRectangle();
			float sx = source.getWidth() / (float) getWidth();
			float sy = source.getHeight() / (float) getHeight();
			matrix.setScale(sx * invBlend + blend, sy * invBlend + blend);
			matrix.postTranslate(source.getLeft() * invBlend, source.getTop()
					* invBlend);
			canvas.concat(matrix);
			split2.draw(canvas);
		}
	}

	private Split calculateSplit(SplitResult<Object> parent) {
		Object tag = parent != null ? parent.getTag() : null;
		RectangleSplit<Object> rectangleSplit = dataSource.getData(tag);
		Split split = new Split(parent, rectangleSplit.split(new Rectangle(0,
				0, getWidth() - 1, getHeight() - 1)));
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
				split2 = calculateSplit(findItemAt);
				backStack.push(split);
				animateToNewItem(false);
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

	private boolean isAnimating() {
		return va != null && va.isRunning();
	}

	private void animateToNewItem(final boolean reverse) {
		if (va != null)
			va.cancel();
		va = reverse ? ValueAnimator.ofFloat(1f, 0f) : ValueAnimator.ofFloat(
				0f, 1f);
		va.setInterpolator(new AccelerateDecelerateInterpolator());
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
				if (!reverse) {
					split = split2;
				}
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

	public boolean back() {
		if (backStack.isEmpty())
			return false;
		if (isAnimating())
			return true;
		Split poppedSplit = backStack.pop();
		split2 = split;
		split = poppedSplit;
		animateToNewItem(true);
		return true;
	}

}
