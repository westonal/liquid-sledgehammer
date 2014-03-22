package com.coltsoftware.liquidsledgehammer.model;

import java.util.Currency;
import java.util.Locale;

import com.coltsoftware.liquidsledgehammer.BaseTest;

public abstract class MoneyTestBase extends BaseTest {

	protected final Currency gbp = Currency.getInstance(Locale.UK);
	protected final Currency usd = Currency.getInstance(Locale.US);
	protected final Currency yen = Currency.getInstance(Locale.JAPAN);
	protected final Currency local = Currency.getInstance(Locale.getDefault());

	protected Money pounds(long pence) {
		return new Money(pence, gbp);
	}

}
