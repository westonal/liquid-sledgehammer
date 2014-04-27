package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public enum NullUnassignedStrategy implements UnassignedValueStrategy {

	INSTANCE;

	@Override
	public String unassigned(FinancialTransaction transaction) {
		return "";
	}

}
