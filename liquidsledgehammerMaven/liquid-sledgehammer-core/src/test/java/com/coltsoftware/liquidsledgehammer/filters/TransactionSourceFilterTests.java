package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.filters.TransactionSourceFilter.Builder;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransactionSourceInformation;

public final class TransactionSourceFilterTests extends
		TransactionFilterTestBase {

	private Builder builder;

	@Before
	public void setup() {
		builder = new TransactionSourceFilter.Builder();
	}

	private static FinancialTransaction newFromSource(final String name) {
		return newTransactionBuilder()
				.source(new FinancialTransactionSourceInformation() {
					@Override
					public String getName() {
						return name;
					}
				}).date(2012, 1, 2).value(usd(10)).build();
	}

	@Test
	public void without_specifying_parameters_returns_null_filter() {
		TransactionFilter f = builder.build();
		assertSame(BooleanTransactionFilter.TRUE, f);
	}

	@Test
	public void allows_bank_it_is_looking_for() {
		TransactionFilter f = builder.sourceName("bank1").build();
		assertTrue(f.allow(newFromSource("bank1")));
	}

	@Test
	public void disallows_bank_it_is_not_looking_for() {
		TransactionFilter f = builder.sourceName("bank2").build();
		assertFalse(f.allow(newFromSource("bank1")));
	}

	@Test
	public void changing_builder_after_build_is_ok() {
		TransactionFilter f = builder.sourceName("bank2").build();
		builder.sourceName("bank1");
		assertFalse(f.allow(newFromSource("bank1")));
	}

	@Test
	public void case_sensivity_is_on_by_default() {
		TransactionFilter f = builder.sourceName("bank1").build();
		assertFalse(f.allow(newFromSource("Bank1")));
	}

	@Test
	public void case_sensivity_can_be_turned_off() {
		TransactionFilter f = builder.sourceName("bank1").caseInsensitive()
				.build();
		assertTrue(f.allow(newFromSource("Bank1")));
	}

	@Test
	public void case_insensitive_to_string() {
		TransactionFilter f = builder.sourceName("bank1").caseInsensitive()
				.build();
		assertEquals("source = \"bank1\"", f.toString());
	}

	@Test
	public void case_insensitive_to_string2() {
		TransactionFilter f = builder.sourceName("src2").caseInsensitive()
				.build();
		assertEquals("source = \"src2\"", f.toString());
	}

	@Test
	public void case_sensitive_to_string() {
		TransactionFilter f = builder.sourceName("bank1").build();
		assertEquals("source =(case) \"bank1\"", f.toString());
	}

	@Test
	public void case_sensitive_to_string2() {
		TransactionFilter f = builder.sourceName("src2").build();
		assertEquals("source =(case) \"src2\"", f.toString());
	}

}
