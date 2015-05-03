package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.filters.TransactionFilter;

public interface FilterFactory extends UsagePrinter {

	TransactionFilter constructFilter(String[] filterArguments);
}
