package com.coltsoftware.liquidsledgehammer.subtransactions;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.collections.GroupPatternParser;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.NullUnassignedStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.UnassignedValueStrategy;

public class SubTransactionFactory {

	private UnassignedValueStrategy strategy = NullUnassignedStrategy.INSTANCE;

	public Iterable<SubTransaction> getSubTransactions(
			FinancialTransaction transaction) {
		ArrayList<SubTransaction> arrayList = new ArrayList<SubTransaction>();
		GroupValues values = new GroupPatternParser()
				.getGroupValues(transaction);
		if (!values.getUnassigned().isZero())
			values.pushRemainingToGroup(strategy.unassigned(transaction));
		for (String group : values)
			arrayList.add(new SubTransaction(transaction, group, values
					.get(group)));
		return arrayList;
	}

	public void setUnassignedValueStrategy(UnassignedValueStrategy strategy) {
		this.strategy = strategy;
	}

}
