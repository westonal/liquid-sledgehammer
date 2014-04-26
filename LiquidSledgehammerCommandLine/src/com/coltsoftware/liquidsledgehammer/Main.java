package com.coltsoftware.liquidsledgehammer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public class Main {

	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Temp\\Transactions");
		ArrayList<FinancialTransactionSource> sources = PathSourceWalker.loadAllSourcesBelowPath(f.toPath());
		Processor processor = new Processor(new AliasPathResolver());
		FinancialTreeNode root = new FinancialTreeNode();
		for (FinancialTransactionSource source : sources)
			processor.populateTree(source, root);

		Output.output("");
		outputTree(root);
	}


	private static void outputTree(FinancialTreeNode root) {
		Output.output(String.format("Treenode %s = %s", root.getName(),
				root.getTotalValue()));
		for (SubTransaction transaction : root.getSubTransactions())
			Output.output(String.format("  %s = %s", transaction.getTransaction()
					.getDescription(), transaction.getValue()));

		for (FinancialTreeNode node : root)
			outputTree(node);
	}
}
