package com.coltsoftware.liquidsledgehammer.cmd;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public interface SourceFactory {

	ArrayList<FinancialTransactionSource> getSources(String[] sourceArguments,
			Arguments otherArguments);

	String getUsageLine();

}
