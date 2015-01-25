package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import android.graphics.Paint;

import com.coltsoftware.liquidsledgehammer.androidexample.FillTagDrawer;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public final class FinancialFillTagDrawer extends FillTagDrawer {

	@Override
	protected void tagToPaint(Object tag, Paint paint) {
		if (tag instanceof FinancialTreeNode) {
			FinancialTreeNode node = (FinancialTreeNode) tag;
			valueToColor(paint, node.getTotalValue());
			return;
		}

		if (tag instanceof SubTransaction) {
			SubTransaction node = (SubTransaction) tag;
			valueToColor(paint, node.getValue());
			return;
		}

		super.tagToPaint(tag, paint);
	}

	private void valueToColor(Paint paint, Money money) {
		int value = (int) Math.abs(money.getValue());
		float r = 0.5f + 0.5f * value / 10000f;
		float g = 0.5f + 0.5f * value / 1000f;
		float b = 0.5f + 0.5f * value / 100f;
		paint.setARGB(255, (int) (255 * r), (int) (255 * g), (int) (255 * b));
	}

}
