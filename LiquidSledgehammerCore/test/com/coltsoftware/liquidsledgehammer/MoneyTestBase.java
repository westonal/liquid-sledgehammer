package com.coltsoftware.liquidsledgehammer;

import java.util.Currency;
import java.util.Locale;

import com.coltsoftware.liquidsledgehammer.model.Money;

public abstract class MoneyTestBase extends BaseTest {

	protected final Currency gbp = Currency.getInstance(Locale.UK);
	protected final Currency usd = Currency.getInstance(Locale.US);
	protected final Currency yen = Currency.getInstance(Locale.JAPAN);
	protected final Currency euro = Currency.getInstance(Locale.GERMANY);
	protected final Currency local = Currency.getInstance(Locale.getDefault());

	protected Money gbp(long pence) {
		return new Money(pence, gbp);
	}

	protected Money usd(long cents) {
		return new Money(cents, usd);
	}

	protected Money yen(long yenValue) {
		return new Money(yenValue, yen);
	}

	protected Money euro(long cents) {
		return new Money(cents, euro);
	}

	protected Money local(long minorUnit) {
		return new Money(minorUnit, local);
	}

}
