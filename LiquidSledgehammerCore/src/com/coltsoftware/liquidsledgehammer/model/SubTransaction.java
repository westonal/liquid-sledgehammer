package com.coltsoftware.liquidsledgehammer.model;

public final class SubTransaction {

	private final FinancialTransaction transaction;
	private final String group;
	private final Money value;

	public SubTransaction(FinancialTransaction transaction, String group,
			Money value) {
		this.transaction = transaction;
		this.group = group;
		this.value = value;
	}

	public Money getValue() {
		return value;
	}

	public String getGroup() {
		return group;
	}

}
