package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public interface Command extends UsagePrinter {
	void execute(FinancialTransactionSource source, Arguments arguments,
			PrintStream out);
}
