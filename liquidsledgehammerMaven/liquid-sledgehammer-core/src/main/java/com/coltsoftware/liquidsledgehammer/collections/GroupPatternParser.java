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
		for (String group : groupPattern.split("\\s*,\\s*"))
			processGroup(rawValues, groupValues, group, currency, value);

		if (!Money.allSameSign(rawValues.values()))
			return new GroupValues(value);

		return groupValues;
	}

	private void processGroup(HashMap<String, Money> result,
			GroupValues groupValues, String group, Currency currency,
			Money fullValue) {
		String[] split = group.split("=");
		String groupName = split[0];
		if (split.length == 1) {
			result.put(groupName, Money.Zero);
			groupValues.pushRemainingToGroup(groupName);
		} else {
			String groupValueText = split[1];
			Money groupValue = groupValueTextToMoney(groupValueText, fullValue,
					currency);
			result.put(groupName, groupValue);
			groupValues.pushToGroup(groupName, groupValue);
		}
	}

	private Money groupValueTextToMoney(String groupValueText, Money fullValue,
			Currency currency) {
		Money groupValue;
		if (groupValueText.endsWith("%")) {
			double percentage = Double.parseDouble(groupValueText.substring(0,
					groupValueText.length() - 1));
			groupValue = new Money(
					(long) (fullValue.getValue() * percentage / 100), currency);
		} else {
			groupValue = Money.fromString(groupValueText, currency);
		}
		return groupValue;
	}

}
