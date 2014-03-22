package com.coltsoftware.liquidsledgehammer.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.coltsoftware.liquidsledgehammer.model.Money;

public final class GroupValues implements Iterable<String> {

	private Money unassigned;
	private final ArrayList<String> keysInOrder = new ArrayList<String>();
	private final HashMap<String, Money> other = new HashMap<String, Money>();

	GroupValues(Money totalValue) {
		this.unassigned = totalValue;
	}

	public Money getUnassigned() {
		return unassigned;
	}

	public Money get(String groupName) {
		if ("".equals(groupName))
			return unassigned;
		Money value = other.get(groupName);
		return value == null ? Money.Zero : value;
	}

	void pushRemainingToGroup(String groupName) {
		pushToGroup(groupName, unassigned);
	}

	void pushToGroup(String groupName, Money value) {
		if (!value.sameSignAs(unassigned))
			value = value.negate();
		other.put(groupName, value);
		keysInOrder.add(groupName);
		unassigned = unassigned.subtract(value);
	}

	@Override
	public Iterator<String> iterator() {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(keysInOrder);
		if (!unassigned.isZero())
			result.add("");
		return result.iterator();
	}

}
