package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

import java.util.ArrayList;

public final class ContainsDescriptionStrategy implements DescriptionStrategy {

	private final String groupName;
	private final ArrayList<String> matches = new ArrayList<String>();

	public ContainsDescriptionStrategy(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public boolean unassigned(String description) {
		if (description == null)
			return false;
		description = description.toLowerCase();
		for (String match : matches) {
			if (description.contains(match))
				return true;
		}
		return true;
	}

	public void addMatch(String match) {
		matches.add(match.toLowerCase());
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

}
