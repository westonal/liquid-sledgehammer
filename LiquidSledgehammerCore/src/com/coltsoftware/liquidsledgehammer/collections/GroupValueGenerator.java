package com.coltsoftware.liquidsledgehammer.collections;

import java.util.Currency;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class GroupValueGenerator {

	public GroupValues getGroupValues(FinancialTransaction transaction) {
		String groupPattern = transaction.getGroupPattern();
		Money value = transaction.getValue();
		GroupValues groupValues = new GroupValues(value);

		if (groupPattern == null)
			return groupValues;

		Currency currency = value.getCurrency();
		for (String group : groupPattern.split(","))
			processGroup(groupValues, group, currency);

		return groupValues;
	}

	private void processGroup(GroupValues groupValues, String group,
			Currency currency) {
		String[] split = group.split("=");
		String groupName = split[0];
		if (split.length == 1) {
			groupValues.pushRemainingToGroup(groupName);
		} else {
			Money groupValue = Money.fromString(split[1], currency);
			groupValues.pushToGroup(groupName, groupValue);
		}
	}

}
