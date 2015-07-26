package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public interface UnassignedValueStrategy {

	String unassigned(FinancialTransaction transaction);

}
