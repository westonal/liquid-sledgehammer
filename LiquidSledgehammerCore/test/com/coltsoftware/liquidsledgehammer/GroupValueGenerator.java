package com.coltsoftware.liquidsledgehammer;

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
			long groupValue = Long.parseLong(split[1]);
			groupValues.pushToGroup(groupName, groupValue);
		}
	}

}
