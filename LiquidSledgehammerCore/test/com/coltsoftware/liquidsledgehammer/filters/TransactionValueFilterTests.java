package com.coltsoftware.liquidsledgehammer.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.filters.TransactionValueFilter.Builder;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class TransactionValueFilterTests extends
		TransactionFilterTestBase {

	private Builder builder;

	@Before
	public void setup() {
		builder = new TransactionValueFilter.Builder();
	}

	private static FinancialTransaction newForValue(Money money) {
		return newTransactionBuilder().date(2012, 1, 2).value(money).build();
	}

	@Test
	public void without_specifying_parameters_returns_null_filter() {
		TransactionFilter f = builder.build();
		assertSame(BooleanTransactionFilter.TRUE, f);
	}

	@Test
	public void minimum_value_allows_exact_match() {
		TransactionFilter f = builder.minimumValue(usd(10)).build();
		assertTrue(f.allow(newForValue(usd(10))));
	}

	@Test
	public void minimum_value_disallows_different_currency() {
		TransactionFilter f = builder.minimumValue(usd(10)).build();
		assertFalse(f.allow(newForValue(gbp(10))));
	}

	@Test
	public void minimum_value_disallows_value_below() {
		TransactionFilter f = builder.minimumValue(usd(21)).build();
		assertFalse(f.allow(newForValue(usd(20))));
	}

	@Test
	public void minimum_value_allows_value_above() {
		TransactionFilter f = builder.minimumValue(usd(21)).build();
		assertTrue(f.allow(newForValue(usd(22))));
	}

	@Test
	public void maximum_value_allows_exact_match() {
		TransactionFilter f = builder.maximumValue(usd(10)).build();
		assertTrue(f.allow(newForValue(usd(10))));
	}

	@Test
	public void maximum_value_disallows_different_currency() {
		TransactionFilter f = builder.maximumValue(usd(10)).build();
		assertFalse(f.allow(newForValue(gbp(10))));
	}

	@Test
	public void maximum_value_allows_value_below() {
		TransactionFilter f = builder.maximumValue(usd(21)).build();
		assertTrue(f.allow(newForValue(usd(20))));
	}

	@Test
	public void maximum_value_disallows_value_above() {
		TransactionFilter f = builder.maximumValue(usd(21)).build();
		assertFalse(f.allow(newForValue(usd(22))));
	}

	@Test
	public void min_and_max_value_allows_exact_min() {
		TransactionFilter f = builder.minimumValue(usd(19))
				.maximumValue(usd(21)).build();
		assertTrue(f.allow(newForValue(usd(19))));
	}

	@Test
	public void min_and_max_value_allows_exact_max() {
		TransactionFilter f = builder.minimumValue(usd(19))
				.maximumValue(usd(21)).build();
		assertTrue(f.allow(newForValue(usd(21))));
	}

	@Test
	public void min_and_max_value_allows_inbetween() {
		TransactionFilter f = builder.minimumValue(usd(19))
				.maximumValue(usd(21)).build();
		assertTrue(f.allow(newForValue(usd(20))));
	}

	@Test
	public void min_and_max_value_disallows_below_min() {
		TransactionFilter f = builder.minimumValue(usd(19))
				.maximumValue(usd(21)).build();
		assertFalse(f.allow(newForValue(usd(18))));
	}

	@Test
	public void min_and_max_value_disallows_above_max() {
		TransactionFilter f = builder.minimumValue(usd(19))
				.maximumValue(usd(21)).build();
		assertFalse(f.allow(newForValue(usd(22))));
	}

}
