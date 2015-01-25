package com.coltsoftware.rectangleareagraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RectangleSplit<TagType> {

	private final SplitResultList<TagType> arrayList = new SplitResultList<TagType>();

	public List<SplitResult<TagType>> split(Rectangle rectangle) {
		Collections.sort(arrayList.arrayList);
		processSubList(arrayList.subList(0, arrayList.size()), rectangle, false);
		return arrayList.asList();
	}

	private void processSubList(SplitResultList<TagType> arrayList,
			Rectangle rectangle, boolean dirToggle) {
		int total = arrayList.getTotal();

		int oneThird = total / 3;

		int t2 = 0;
		int size = arrayList.size();
		int firstThirdCount = 0;
		for (int i3 = 0; i3 < size; i3++) {
			t2 += arrayList.get(i3).value;
			firstThirdCount++;
			if (t2 >= oneThird) {
				break;
			}
		}

		if (firstThirdCount < size && firstThirdCount > 1) {
			SplitResultList<TagType> firstThird = arrayList.subList(0,
					firstThirdCount);
			arrayList.fold(firstThirdCount);
			processSubList(arrayList, rectangle, dirToggle);
			processSubList(firstThird, arrayList.get(0).rectangle, !dirToggle);
			return;
		}

		if (dirToggle) {
			int height = rectangle.getHeight();

			for (int i = 0; i < size; i++) {
				SplitResult<TagType> result = arrayList.get(i);
				int height2 = i == (size - 1) ? height : result.value
						* rectangle.getHeight() / total;
				int left = rectangle.getLeft();
				int top = rectangle.getTop() + rectangle.getHeight() - height;
				int width = rectangle.getWidth();
				result.rectangle = new Rectangle(left, top, width, height2);
				height -= height2;
			}
		} else {
			int width = rectangle.getWidth();

			for (int i = 0; i < size; i++) {
				SplitResult<TagType> result = arrayList.get(i);
				int width2 = i == (size - 1) ? width : result.value
						* rectangle.getWidth() / total;
				int left = rectangle.getLeft() + rectangle.getWidth() - width;
				int top = rectangle.getTop();
				int height = rectangle.getHeight();
				result.rectangle = new Rectangle(left, top, width2, height);
				width -= width2;
			}
		}
	}

	public void addValue(int value, TagType tag) {
		arrayList.add(new SplitResult<TagType>(value, tag));
	}

	public static class SplitResultList<TagType> {
		private final ArrayList<SplitResult<TagType>> arrayList = new ArrayList<SplitResult<TagType>>();

		public int size() {
			return arrayList.size();
		}

		public void fold(int count) {
			int subTotal = 0;
			for (int i = 0; i < count; i++)
				subTotal += arrayList.remove(0).value;
			SplitResult<TagType> combined = new SplitResult<TagType>(subTotal,
					null);
			arrayList.add(0, combined);
		}

		public SplitResult<TagType> get(int index) {
			return arrayList.get(index);
		}

		public void add(SplitResult<TagType> splitResult) {
			arrayList.add(splitResult);
		}

		public List<SplitResult<TagType>> asList() {
			return arrayList;
		}

		public int getTotal() {
			int total = 0;
			int size = size();
			for (int i = 0; i < size; i++)
				total += arrayList.get(i).value;
			return total;
		}

		public SplitResultList<TagType> subList(int startAt, int count) {
			SplitResultList<TagType> splitResultList = new SplitResultList<TagType>();
			for (int i = startAt; i < startAt + count; i++)
				splitResultList.add(get(i));
			return splitResultList;
		}

	}

	public static class SplitResult<TagType> implements
			Comparable<SplitResult<TagType>> {

		private final int value;
		private final TagType tag;
		private Rectangle rectangle;

		public SplitResult(int value, TagType tag) {
			this.value = value;
			this.tag = tag;
		}

		public int getValue() {
			return value;
		}

		public TagType getTag() {
			return tag;
		}

		public Rectangle getRectangle() {
			return rectangle;
		}

		@Override
		public int compareTo(SplitResult<TagType> other) {
			return value - other.value;
		}

	}

}
