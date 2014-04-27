package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.NamedDescriptionStrategy;

public final class DescriptionMatchingStrategy implements
		UnassignedValueStrategy {

	private final ArrayList<NamedDescriptionStrategy> descStrats = new ArrayList<NamedDescriptionStrategy>();

	@Override
	public String unassigned(FinancialTransaction transaction) {
		for (NamedDescriptionStrategy descStrat : descStrats) {
			if (descStrat.matches(transaction.getDescription()))
				return descStrat.getGroupName();
		}
		return "";
	}

	public void add(NamedDescriptionStrategy descStrat) {
		descStrats.add(descStrat);
	}

}
