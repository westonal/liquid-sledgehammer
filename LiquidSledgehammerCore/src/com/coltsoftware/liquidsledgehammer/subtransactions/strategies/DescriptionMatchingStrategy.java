package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;

public final class DescriptionMatchingStrategy implements
		UnassignedValueStrategy {

	private final ArrayList<DescriptionStrategy> descStrats = new ArrayList<DescriptionStrategy>();

	@Override
	public String unassigned(FinancialTransaction transaction) {
		for (DescriptionStrategy descStrat : descStrats) {
			String res = descStrat.unassigned("Desc");
			if (res != null)
				return res;
		}
		return "";
	}

	public void add(DescriptionStrategy descStrat) {
		descStrats.add(descStrat);
	}

}
