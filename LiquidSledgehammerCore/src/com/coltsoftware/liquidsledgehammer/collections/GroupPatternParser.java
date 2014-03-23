package com.coltsoftware.liquidsledgehammer.collections;

import java.util.Currency;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.Money;

public final class GroupPatternParser {

	public GroupValues getGroupValues(FinancialTransaction transaction) {
		String groupPattern = transaction.getGroupPattern();
		Money value = transaction.getValue();
		GroupValues groupValues = new GroupValues(value);

		if (groupPattern == null)
			return groupValues;

		HashMap<String, Money> rawValues = new HashMap<String, Money>();

		Currency currency = value.getCurrency();
		for (String group : groupPattern.split(","))
			processGroup(rawValues, groupValues, group, currency);

		if (!Money.allSameSign(rawValues.values()))
			return new GroupValues(value);

		return groupValues;
	}

	private void processGroup(HashMap<String, Money> result,
			GroupValues groupValues, String group, Currency currency) {
		String[] split = group.split("=");
		String groupName = split[0];
		if (split.length == 1) {
			result.put(groupName, Money.Zero);
			groupValues.pushRemainingToGroup(groupName);
		} else {
			Money groupValue = Money.fromString(split[1], currency);
			result.put(groupName, groupValue);
			groupValues.pushToGroup(groupName, groupValue);
		}
	}

}
