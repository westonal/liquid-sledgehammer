package com.coltsoftware.liquidsledgehammer.model;

public final class SubTransaction {

	private final FinancialTransaction transaction;
	private String groupPattern;

	public SubTransaction(FinancialTransaction transaction, String group) {
		this.transaction = transaction;
		this.groupPattern=group;
	}

	public Money getValue() {
		return transaction.getValue();
	}

	public String getGroup() {
		return groupPattern;
	}

}
