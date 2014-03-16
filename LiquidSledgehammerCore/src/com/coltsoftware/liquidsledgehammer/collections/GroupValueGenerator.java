package com.coltsoftware.liquidsledgehammer.collections;

import java.util.Currency;
import java.util.Locale;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class GroupValueGenerator {

	public GroupValues getGroupValues(FinancialTransaction transaction) {
		String groupPattern = transaction.getGroupPattern();
		GroupValues groupValues = new GroupValues(transaction.getValue());

		if (groupPattern == null)
			return groupValues;

		for (String group : groupPattern.split(","))
			processGroup(groupValues, group);

		return groupValues;
	}

	private void processGroup(GroupValues groupValues, String group) {
		String[] split = group.split("=");
		String groupName = split[0];
		if (split.length == 1) {
			groupValues.pushRemainingToGroup(groupName);
		} else {
			Money groupValue = new Money(Long.parseLong(split[1]),
					Currency.getInstance(Locale.getDefault()));
			groupValues.pushToGroup(groupName, groupValue);
		}
	}

}
