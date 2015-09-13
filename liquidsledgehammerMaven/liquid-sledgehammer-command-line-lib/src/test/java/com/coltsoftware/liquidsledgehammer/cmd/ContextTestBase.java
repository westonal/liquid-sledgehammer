package com.coltsoftware.liquidsledgehammer.cmd;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public abstract class ContextTestBase {

	protected static SourceFactory createMockSourceFactory(int sourcesCount,
			String usage) {
		SourceFactory mock = mock(SourceFactory.class);
		when(mock.getSources(any(String[].class), any(Arguments.class)))
				.thenReturn(sources(sourcesCount));
		when(mock.getUsageLine()).thenReturn(usage);
		return mock;
	}

	private static ArrayList<FinancialTransactionSource> sources(
			int sourcesCount) {
		ArrayList<FinancialTransactionSource> list = new ArrayList<FinancialTransactionSource>();
		for (int i = 0; i < sourcesCount; i++)
			list.add(null);
		return list;
	}

}