package com.coltsoftware.liquidsledgehammer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public class Main {

	public static void main(String[] args) throws IOException {
		File f = new File(args[0]);
		InputStream stream = new FileInputStream(f);
		try {
			FinancialTransactionSource source = JsonStreamTransactionSource
					.fromStream(stream);
			for (FinancialTransaction t : source)
				output(String.format("%s, %s", t.getDescription(), t.getValue()
						.toString()));

			Processor processor = new Processor(new AliasPathResolver());
			FinancialTreeNode root = new FinancialTreeNode();
			processor.populateTree(source, root);

			output("");
			outputTree(root);
		} finally {
			stream.close();
		}
	}

	private static void outputTree(FinancialTreeNode root) {
		output(String.format("Treenode %s = %s", root.getName(),
				root.getTotalValue()));
		for (SubTransaction transaction : root.getSubTransactions())
			output(String.format("  %s = %s", transaction.getTransaction()
					.getDescription(), transaction.getValue()));

		for (FinancialTreeNode node : root)
			outputTree(node);
	}

	private static void output(String message) {
		System.out.println(message);
	}

}
