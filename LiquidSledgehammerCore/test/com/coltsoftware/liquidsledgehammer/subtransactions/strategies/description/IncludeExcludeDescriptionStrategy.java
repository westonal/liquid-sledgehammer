package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

public final class IncludeExcludeDescriptionStrategy implements
		DescriptionStrategy {

	private final ContainsDescriptionStrategy contains;
	private final ContainsDescriptionStrategy excludesList;
	private final DescriptionStrategy excludes;

	public IncludeExcludeDescriptionStrategy() {
		contains = new ContainsDescriptionStrategy();
		excludesList = new ContainsDescriptionStrategy();
		excludes = NotDescriptionStrategy.negate(excludesList);
	}

	@Override
	public boolean matches(String description) {
		return contains.matches(description) && excludes.matches(description);
	}

	public void addInclude(String include) {
		contains.addMatch(include);
	}

	public void addExclude(String exclude) {
		excludesList.addMatch(exclude);
	}

}
