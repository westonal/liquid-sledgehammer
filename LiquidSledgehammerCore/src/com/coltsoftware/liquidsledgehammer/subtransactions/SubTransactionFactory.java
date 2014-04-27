package com.coltsoftware.liquidsledgehammer.subtransactions;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.collections.GroupPatternParser;
import com.coltsoftware.liquidsledgehammer.collections.GroupValues;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;

public class SubTransactionFactory {

	public Iterable<SubTransaction> getSubTransactions(FinancialTransaction transaction) {
		ArrayList<SubTransaction> arrayList = new ArrayList<SubTransaction>();
		GroupValues values = new GroupPatternParser().getGroupValues(transaction);
		for (String group : values)
			arrayList.add(new SubTransaction(transaction, group, values.get(group)));
		return arrayList;
	}

}
