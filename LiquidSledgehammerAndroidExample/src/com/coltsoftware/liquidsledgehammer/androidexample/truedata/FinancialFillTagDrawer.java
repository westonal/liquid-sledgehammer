package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import android.graphics.Paint;

import com.coltsoftware.liquidsledgehammer.androidexample.FillTagDrawer;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;

public final class FinancialFillTagDrawer extends FillTagDrawer {

	@Override
	protected void tagToPaint(Object tag, Paint paint) {
		if (tag instanceof FinancialTreeNode) {
			FinancialTreeNode node = (FinancialTreeNode) tag;
			int value = (int) node.getTotalValue().getValue();
			float r = value / 10000f;
			float g = value / 1000f;
			float b = value / 100f;

			paint.setARGB(255, (int) (255 * r), (int) (255 * g),
					(int) (255 * b));
		} else
			super.tagToPaint(tag, paint);
	}

}
