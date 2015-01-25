package com.coltsoftware.rectangleareagraph;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.rectangleareagraph.Rectangle;
import com.coltsoftware.rectangleareagraph.RectangleSplit;
import com.coltsoftware.rectangleareagraph.RectangleSplit.SplitResult;

public class GenericRectangleSplitTests {

	private RectangleSplit<SampleClass> splitter;

	private static class SampleClass {

		private String string;

		public SampleClass(String string) {
			this.string = string;

		}

		public String getString() {
			return string;
		}
	}

	@Before
	public void setup() {
		splitter = new RectangleSplit<SampleClass>();
	}

	@Test
	public void can_split_nothing() {
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 100, 100));
		assertEquals(0, results.size());
	}

	@Test
	public void can_split_one_item() {
		splitter.addValue(100, newTag("Red"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 100, 100));
		assertEquals(1, results.size());
		SplitResult<SampleClass> result = results.get(0);
		assertEquals(100, result.getValue());
		assertEquals("Red", result.getTag().getString());
		assertEquals(new Rectangle(0, 0, 100, 100), result.getRectangle());
	}

	@Test
	public void can_split_two_items_and_read_values() {
		splitter.addValue(100, newTag("Red"));
		splitter.addValue(235, newTag("Green"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 100, 100));
		assertEquals(2, results.size());
		{
			SplitResult<SampleClass> result = results.get(0);
			assertEquals(100, result.getValue());
			assertEquals("Red", result.getTag().getString());
		}
		{
			SplitResult<SampleClass> result = results.get(1);
			assertEquals(235, result.getValue());
			assertEquals("Green", result.getTag().getString());
		}
	}

	@Test
	public void can_split_two_even_items() {
		splitter.addValue(100, newTag("Red"));
		splitter.addValue(100, newTag("Green"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 100, 100));
		assertEquals(2, results.size());
		assertEquals(new Rectangle(0, 0, 50, 100), results.get(0)
				.getRectangle());
		assertEquals(new Rectangle(50, 0, 50, 100), results.get(1)
				.getRectangle());
	}

	@Test
	public void can_split_three_even_items() {
		splitter.addValue(7, newTag("Red"));
		splitter.addValue(7, newTag("Green"));
		splitter.addValue(7, newTag("Blue"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 100, 100));
		assertEquals(3, results.size());
		assertEquals(new Rectangle(0, 0, 33, 100), results.get(0)
				.getRectangle());
		assertEquals(new Rectangle(33, 0, 33, 100), results.get(1)
				.getRectangle());
		assertEquals(new Rectangle(66, 0, 34, 100), results.get(2)
				.getRectangle());
	}

	@Test
	public void can_split_two_items_unevenly() {
		splitter.addValue(1, newTag("Green"));
		splitter.addValue(3, newTag("Red"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 100, 100));
		assertEquals(2, results.size());
		assertEquals(new Rectangle(0, 0, 25, 100), results.get(0)
				.getRectangle());
		assertEquals(new Rectangle(25, 0, 75, 100), results.get(1)
				.getRectangle());
	}

	@Test
	public void can_split_thee_items_first_two_required_to_make_a_third_of_total() {
		splitter.addValue(2, newTag("A"));
		splitter.addValue(2, newTag("B"));
		splitter.addValue(6, newTag("C"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 10, 10));
		assertEquals(3, results.size());
		assertEquals(20, results.get(0).getRectangle().getArea());
		assertEquals(20, results.get(1).getRectangle().getArea());
		assertEquals(60, results.get(2).getRectangle().getArea());
		assertEquals(new Rectangle(0, 0, 4, 5), results.get(0).getRectangle());
		assertEquals(new Rectangle(0, 5, 4, 5), results.get(1).getRectangle());
		assertEquals(new Rectangle(4, 0, 6, 10), results.get(2).getRectangle());
	}

	@Test
	public void sorts_automatically() {
		splitter.addValue(6, newTag("C"));
		splitter.addValue(2, newTag("A"));
		splitter.addValue(2, newTag("B"));
		List<SplitResult<SampleClass>> results = splitter.split(new Rectangle(
				0, 0, 10, 10));
		assertEquals(3, results.size());
		assertEquals(20, results.get(0).getRectangle().getArea());
		assertEquals(20, results.get(1).getRectangle().getArea());
		assertEquals(60, results.get(2).getRectangle().getArea());
		assertEquals("A", results.get(0).getTag().getString());
		assertEquals("B", results.get(1).getTag().getString());
		assertEquals(new Rectangle(0, 0, 4, 5), results.get(0).getRectangle());
		assertEquals(new Rectangle(0, 5, 4, 5), results.get(1).getRectangle());
		assertEquals(new Rectangle(4, 0, 6, 10), results.get(2).getRectangle());
	}

	private SampleClass newTag(String string) {
		return new SampleClass(string);
	}

}
