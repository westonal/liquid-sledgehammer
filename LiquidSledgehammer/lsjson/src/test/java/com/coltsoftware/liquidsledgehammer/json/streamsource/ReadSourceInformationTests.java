package com.coltsoftware.liquidsledgehammer.json.streamsource;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.model.NullFinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class ReadSourceInformationTests extends StreamSourceTestBase {

	@Override
	protected String getAssetName() {
		return "statement.json";
	}

	private FinancialTransactionSource source;

	@Before
	@Override
	public void setup() {
		super.setup();
		source = JsonStreamTransactionSource.fromStream(stream,
				new FinancialTransactionSourceInformation() {

					@Override
					public String getName() {
						return "A bank";
					}
				});
	}

	@Test
	public void loaded_correct_number() {
		assertEquals(2, count(source));
	}

	@Test
	public void can_read_source_name() {
		Iterator<FinancialTransaction> iterator = source.iterator();
		FinancialTransaction transaction = iterator.next();
		assertEquals("A bank", transaction.getSource().getName());
		transaction = iterator.next();
		assertEquals("A bank", transaction.getSource().getName());
	}
}
