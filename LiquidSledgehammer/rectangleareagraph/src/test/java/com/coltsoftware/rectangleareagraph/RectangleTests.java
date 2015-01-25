package com.coltsoftware.rectangleareagraph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coltsoftware.rectangleareagraph.Rectangle;

public class RectangleTests {

	@Test
	public void can_create_and_read_left() {
		Rectangle rectangle = new Rectangle(0, 0, 1, 1);
		assertEquals(rectangle.getLeft(), 0);
	}

	@Test
	public void can_create_and_read_left_2() {
		Rectangle rectangle = new Rectangle(10, 0, 1, 1);
		assertEquals(rectangle.getLeft(), 10);
	}

	@Test
	public void can_create_and_read_top() {
		Rectangle rectangle = new Rectangle(0, 0, 1, 1);
		assertEquals(rectangle.getTop(), 0);
	}

	@Test
	public void can_create_and_read_top_2() {
		Rectangle rectangle = new Rectangle(0, 10, 1, 1);
		assertEquals(rectangle.getTop(), 10);
	}

	@Test
	public void can_create_and_read_width() {
		Rectangle rectangle = new Rectangle(0, 0, 1, 1);
		assertEquals(rectangle.getWidth(), 1);
	}

	@Test
	public void can_create_and_read_Width_2() {
		Rectangle rectangle = new Rectangle(0, 0, 100, 1);
		assertEquals(rectangle.getWidth(), 100);
	}

	@Test
	public void can_create_and_read_height() {
		Rectangle rectangle = new Rectangle(0, 0, 1, 1);
		assertEquals(rectangle.getHeight(), 1);
	}

	@Test
	public void can_create_and_read_Height_2() {
		Rectangle rectangle = new Rectangle(0, 0, 1, 100);
		assertEquals(rectangle.getHeight(), 100);
	}

	@Test
	public void equality() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		Rectangle rb = new Rectangle(1, 2, 34, 56);
		assertEquals(ra, rb);
		assertEquals(ra.hashCode(), rb.hashCode());
	}

	@Test
	public void in_equality_on_other_object_type() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		assertNotEquals(ra, new Object());
	}

	@Test
	public void in_equality_on_null() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		assertNotEquals(ra, null);
	}

	@Test
	public void in_equality_on_left() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		Rectangle rb = new Rectangle(7, 2, 34, 56);
		inEqual(ra, rb);
	}

	@Test
	public void in_equality_on_top() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		Rectangle rb = new Rectangle(1, 7, 34, 56);
		inEqual(ra, rb);
	}

	@Test
	public void in_equality_on_width() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		Rectangle rb = new Rectangle(1, 2, 35, 56);
		inEqual(ra, rb);
	}

	@Test
	public void in_equality_on_height() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		Rectangle rb = new Rectangle(1, 2, 34, 57);
		inEqual(ra, rb);
	}

	private void inEqual(Rectangle ra, Rectangle rb) {
		assertNotEquals(ra, rb);
		assertNotEquals(ra.hashCode(), rb.hashCode());
	}

	private void assertNotEquals(Object a, Object b) {
		assertFalse(a.equals(b));
	}

	@Test
	public void to_string() {
		Rectangle ra = new Rectangle(1, 2, 34, 56);
		assertEquals("[(1, 2) 34 x 56]", ra.toString());
	}

	@Test
	public void to_string_2() {
		Rectangle ra = new Rectangle(78, 102, 200, 345);
		assertEquals("[(78, 102) 200 x 345]", ra.toString());
	}

	@Test
	public void area() {
		Rectangle rectangle = new Rectangle(7, 8, 3, 4);
		assertEquals(12, rectangle.getArea());
	}
	
	@Test
	public void area2() {
		Rectangle rectangle = new Rectangle(0, 0, 6, 8);
		assertEquals(48, rectangle.getArea());
	}

	@Test
	public void can_create_and_read_right() {
		Rectangle rectangle = new Rectangle(10, 0, 1, 1);
		assertEquals(rectangle.getRight(), 11);
	}
	
	@Test
	public void can_create_and_read_right_2() {
		Rectangle rectangle = new Rectangle(10, 0, 40, 1);
		assertEquals(rectangle.getRight(), 50);
	}
	
	@Test
	public void can_create_and_read_bottom() {
		Rectangle rectangle = new Rectangle(0, 10, 1, 1);
		assertEquals(rectangle.getBottom(), 11);
	}
	
	@Test
	public void can_create_and_read_bottom_2() {
		Rectangle rectangle = new Rectangle(0, 10, 1, 40);
		assertEquals(rectangle.getBottom(), 50);
	}
}
