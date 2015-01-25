package com.coltsoftware.rectangleareagraph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.rectangleareagraph.Rectangle;

public class RectanglePointInsideTests {

	private static boolean inside(int left, int top, int width, int height,
			int x, int y) {
		return new Rectangle(left, top, width, height).inside(x, y);
	}

	private static void assertInside(int left, int top, int width, int height,
			int x, int y) {
		assertTrue("Not inside as expected",
				inside(left, top, width, height, x, y));
	}

	private static void assertOutside(int left, int top, int width, int height,
			int x, int y) {
		assertFalse("Not outside as expected",
				inside(left, top, width, height, x, y));
	}

	@Test
	public void top_left() {
		assertInside(0, 0, 1, 1, 0, 0);
	}

	@Test
	public void top_left_out_by_one_left() {
		assertOutside(0, 0, 1, 1, -1, 0);
	}

	@Test
	public void top_left_out_by_one_right() {
		assertOutside(0, 0, 1, 1, 2, 0);
	}

	@Test
	public void out_by_one_right() {
		assertOutside(100, 100, 50, 50, 151, 100);
	}

	@Test
	public void inside_by_one_right() {
		assertInside(100, 100, 50, 50, 150, 100);
	}

	@Test
	public void out_by_one_left() {
		assertOutside(100, 100, 50, 50, 99, 100);
	}

	@Test
	public void out_by_one_top() {
		assertOutside(100, 100, 50, 50, 100, 99);
	}

	@Test
	public void inside_by_one_top() {
		assertInside(100, 100, 50, 50, 100, 100);
	}

	@Test
	public void out_by_one_bottom() {
		assertOutside(100, 100, 50, 50, 100, 151);
	}

	@Test
	public void inside_by_one_bottom() {
		assertInside(100, 100, 50, 50, 100, 150);
	}

	@Test
	public void all_points_test() {
		Rectangle rectangle = new Rectangle(10, 20, 30, 40);
		for (int x = rectangle.getLeft(); x <= rectangle.getRight(); x++)
			for (int y = rectangle.getTop(); y <= rectangle.getBottom(); y++)
				assertTrue(rectangle.inside(x, y));
	}

	@Test
	public void all_points_outside() {
		Rectangle rectangle = new Rectangle(10, 20, 30, 40);
		for (int y = 0; y <= rectangle.getBottom() + 10; y++) {
			for (int x = 0; x < rectangle.getLeft(); x++)
				assertFalse(rectangle.inside(x, y));
			for (int x = rectangle.getRight() + 1; x < rectangle.getRight() + 10; x++)
				assertFalse(rectangle.inside(x, y));
		}
		for (int y = 0; y < rectangle.getTop(); y++) {
			for (int x = 0; x < rectangle.getRight() + 10; x++)
				assertFalse(rectangle.inside(x, y));
		}
		for (int y = rectangle.getBottom() + 1; y < rectangle.getBottom() + 10; y++) {
			for (int x = 0; x < rectangle.getRight() + 10; x++)
				assertFalse(rectangle.inside(x, y));
		}
	}
}
