package com.coltsoftware.liquidsledgehammer;

import java.util.HashMap;
import java.util.Map;

public final class GroupValues {

	private long unassigned;
	private final Map<String, Long> other = new HashMap<String, Long>();

	GroupValues(long totalValue) {
		this.unassigned = totalValue;
	}

	public long getUnassigned() {
		return unassigned;
	}

	public long get(String groupName) {
		Long value = other.get(groupName);
		return value == null ? 0 : value;
	}

	void pushRemainingToGroup(String groupName) {
		pushToGroup(groupName, unassigned);
	}

	void pushToGroup(String groupName, long value) {
		other.put(groupName, value);
		unassigned -= value;
	}

}
