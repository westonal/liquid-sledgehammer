package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

public final class NotDescriptionStrategy implements DescriptionStrategy {

	private final DescriptionStrategy strategy;

	private NotDescriptionStrategy(DescriptionStrategy strategy) {
		this.strategy = strategy;
	}

	public static DescriptionStrategy negate(DescriptionStrategy strategy) {
		return new NotDescriptionStrategy(strategy);
	}

	@Override
	public String getGroupName() {
		return strategy.getGroupName();
	}

	@Override
	public boolean unassigned(String description) {
		return !strategy.unassigned(description);
	}

}
