package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

public final class DescriptionStrategyNamer implements NamedDescriptionStrategy {

	private final String groupName;
	private final DescriptionStrategy strategy;

	private DescriptionStrategyNamer(String groupName,
			DescriptionStrategy strategy) {
		this.groupName = groupName;
		this.strategy = strategy;
	}

	public static NamedDescriptionStrategy name(String groupName,
			DescriptionStrategy strategy) {
		return new DescriptionStrategyNamer(groupName, strategy);
	}

	@Override
	public boolean matches(String description) {
		return strategy.matches(description);
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

}
