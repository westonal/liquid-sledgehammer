package com.coltsoftware.liquidsledgehammer;

public class GroupValueGenerator {

	public GroupValues getGroupValues(FinancialTransaction transaction) {
		long value = transaction.getValue();
		String groupPattern = transaction.getGroupPattern();
		if (groupPattern == null)
			return new GroupValues(value, 0);

		String[] split = groupPattern.split("=");
		if (split.length == 1) {
			return new GroupValues(0, value);
		} else {
			long parseInt = Long.parseLong(split[1]);
			value -= parseInt;
			return new GroupValues(value, parseInt);
		}
	}

}
