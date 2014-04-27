package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategy;

public final class DescriptionMatchingStrategy implements
		UnassignedValueStrategy {

	private final ArrayList<DescriptionStrategy> descStrats = new ArrayList<DescriptionStrategy>();

	@Override
	public String unassigned(FinancialTransaction transaction) {
		for (DescriptionStrategy descStrat : descStrats) {
			if (descStrat.matches("Desc"))
				return descStrat.getGroupName();
		}
		return "";
	}

	public void add(DescriptionStrategy descStrat) {
		descStrats.add(descStrat);
	}

}
