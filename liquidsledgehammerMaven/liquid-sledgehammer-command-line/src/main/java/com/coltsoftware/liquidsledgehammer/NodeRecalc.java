package com.coltsoftware.liquidsledgehammer;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public interface NodeRecalc {

	FinancialTreeNode doRecalc(FinancialTransactionSource source);
}
