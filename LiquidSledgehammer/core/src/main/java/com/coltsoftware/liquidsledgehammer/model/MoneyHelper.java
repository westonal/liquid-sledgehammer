package com.coltsoftware.liquidsledgehammer.model;

import java.util.Currency;
import java.util.Locale;

public final class MoneyHelper {

    private MoneyHelper() {
    }

    public static final Currency gbp = Currency.getInstance(Locale.UK);
    public static final Currency usd = Currency.getInstance(Locale.US);
    public static final Currency yen = Currency.getInstance(Locale.JAPAN);
    public static final Currency euro = Currency.getInstance(Locale.GERMANY);
    public static final Currency local = Currency.getInstance(Locale.getDefault());

    public static Money gbp(long pence) {
        return new Money(pence, gbp);
    }

    public static Money usd(long cents) {
        return new Money(cents, usd);
    }

    public static Money yen(long yenValue) {
        return new Money(yenValue, yen);
    }

    public static Money euro(long cents) {
        return new Money(cents, euro);
    }

    public static Money local(long minorUnit) {
        return new Money(minorUnit, local);
    }
}
