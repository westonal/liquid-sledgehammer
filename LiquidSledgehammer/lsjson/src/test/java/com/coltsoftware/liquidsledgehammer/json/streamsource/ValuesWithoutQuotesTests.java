package com.coltsoftware.liquidsledgehammer.json.streamsource;

import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static com.coltsoftware.liquidsledgehammer.IterableCounter.count;
import static com.coltsoftware.liquidsledgehammer.model.MoneyHelper.local;
import static org.junit.Assert.assertEquals;

public final class ValuesWithoutQuotesTests extends StreamSourceTestBase {

    @Override
    protected String getAssetName() {
        return "valuesWithoutQuotes.json";
    }

    private FinancialTransactionSource source;

    @Before
    @Override
    public void setup() {
        super.setup();
        source = JsonStreamTransactionSource.fromStream(stream,
                NullFinancialTransactionSourceInformation.INSTANCE);
    }

    @Test
    public void loaded_correct_number() {
        assertEquals(2, count(source));
    }

    @Test
    public void can_read_values() {
        Iterator<FinancialTransaction> iterator = source.iterator();
        FinancialTransaction transaction = iterator.next();
        assertEquals(local(123), transaction.getValue());
    }

    @Test
    public void can_read_negative_value() {
        Iterator<FinancialTransaction> iterator = source.iterator();
        iterator.next();
        FinancialTransaction transaction = iterator.next();
        assertEquals(local(-4567), transaction.getValue());
    }
}
