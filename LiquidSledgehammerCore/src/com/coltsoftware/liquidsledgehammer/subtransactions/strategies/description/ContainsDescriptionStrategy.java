package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

import java.util.ArrayList;

public final class ContainsDescriptionStrategy implements DescriptionStrategy {

	private final ArrayList<String> matches = new ArrayList<String>();

	@Override
	public boolean matches(String description) {
		if (description == null)
			return false;
		description = description.toLowerCase();
		for (String match : matches) {
			if (description.contains(match))
				return true;
		}
		return false;
	}

	public void addMatch(String match) {
		if (match == null || match.trim().equals(""))
			throw new InvalidDescriptionException();
		matches.add(match.toLowerCase());
	}

}
