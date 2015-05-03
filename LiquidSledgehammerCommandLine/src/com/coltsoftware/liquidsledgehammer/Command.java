package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public interface Command extends UsagePrinter {
	void execute(FinancialTreeNode root, FinancialTransactionSource source,
			Arguments arguments, PrintStream out);
}
