package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

public final class NotDescriptionStrategy implements DescriptionStrategy {

	private final DescriptionStrategy strategy;

	private NotDescriptionStrategy(DescriptionStrategy strategy) {
		this.strategy = strategy;
	}

	public static DescriptionStrategy negate(DescriptionStrategy strategy) {
		return new NotDescriptionStrategy(strategy);
	}

	@Override
	public boolean matches(String description) {
		return !strategy.matches(description);
	}

}
