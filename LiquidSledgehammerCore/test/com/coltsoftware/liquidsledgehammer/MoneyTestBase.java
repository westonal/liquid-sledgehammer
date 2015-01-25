package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.model.Money;
import com.coltsoftware.liquidsledgehammer.model.MoneyHelper;

import java.util.Currency;

public abstract class MoneyTestBase extends BaseTest {

    protected final Currency gbp = MoneyHelper.gbp;
    protected final Currency usd = MoneyHelper.usd;
    protected final Currency yen = MoneyHelper.yen;
    protected final Currency euro = MoneyHelper.euro;
    protected final Currency local = MoneyHelper.local;

    protected Money gbp(long pence) {
        return MoneyHelper.gbp(pence);
    }

    protected Money usd(long cents) {
        return MoneyHelper.usd(cents);
    }

    protected Money yen(long yenValue) {
        return MoneyHelper.yen(yenValue);
    }

    protected Money euro(long cents) {
        return MoneyHelper.euro(cents);
    }

    protected Money local(long minorUnit) {
        return MoneyHelper.local(minorUnit);
    }

}
