package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public interface FilterFactory extends UsagePrinter {

	FinancialTransactionSource filter(FinancialTransactionSource source,
			String[] filterArguments);

}
