package com.coltsoftware.liquidsledgehammer.collections;

import java.util.HashMap;
import java.util.Map;

import com.coltsoftware.liquidsledgehammer.model.Money;

public final class GroupValues {

	private Money unassigned;
	private final Map<String, Money> other = new HashMap<String, Money>();

	GroupValues(Money totalValue) {
		this.unassigned = totalValue;
	}

	public Money getUnassigned() {
		return unassigned;
	}

	public Money get(String groupName) {
		Money value = other.get(groupName);
		return value == null ? Money.Zero : value;
	}

	void pushRemainingToGroup(String groupName) {
		pushToGroup(groupName, unassigned);
	}

	void pushToGroup(String groupName, Money value) {
		other.put(groupName, value);
		unassigned = unassigned.subtract(value);
	}

}
