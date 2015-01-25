package com.coltsoftware.rectangleareagraph;

public final class Rectangle {

	private final int left;
	private final int top;
	private final int width;
	private final int height;
	private final int right;
	private final int bottom;

	public Rectangle(int left, int top, int width, int height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		right = width + left;
		bottom = height + top;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Rectangle other = null;
		if (obj.getClass() == getClass())
			other = (Rectangle) obj;

		return equals(other);
	}

	public boolean equals(Rectangle other) {
		if (other == null)
			return false;
		return other.left == left && other.top == top && other.width == width
				&& other.height == height;
	}

	@Override
	public int hashCode() {
		int hash = left;
		hash *= 31;
		hash += top;
		hash *= 31;
		hash += width;
		hash *= 31;
		hash += height;
		return hash;
	}

	@Override
	public String toString() {
		return String.format("[(%d, %d) %d x %d]", left, top, width, height);
	}

	public int getArea() {
		return width * height;
	}

	public boolean inside(int x, int y) {
		return x >= left && x <= right && y >= top && y <= bottom;
	}

}
