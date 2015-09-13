package com.coltsoftware.liquidsledgehammer.model;

public enum NullFinancialTransactionSourceInformation implements
		FinancialTransactionSourceInformation {

	INSTANCE;

	@Override
	public String getName() {
		return "";
	}

}
