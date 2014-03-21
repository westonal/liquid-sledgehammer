package com.coltsoftware.liquidsledgehammer.model;

public final class SubTransaction {

	private final FinancialTransaction transaction;

	public SubTransaction(FinancialTransaction transaction) {
		this.transaction = transaction;
	}
	
	public Money getValue() {
		return transaction.getValue();
	}

}
