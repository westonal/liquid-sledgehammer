package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.androidexample.GraphDataSource;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;
import com.coltsoftware.rectangleareagraph.RectangleSplit;

public final class GraphDataSourceAdaptor implements GraphDataSource {

	private final FinancialTreeNode root;

	public GraphDataSourceAdaptor(
			ArrayList<FinancialTransactionSource> sources, File path)
			throws IOException {
		FileToGroupAliasResolver aliasResolver = new FileToGroupAliasResolver(
				new File(path, "groups.json"));

		Processor processor = new Processor(
				aliasResolver.createAliasPathResolver(),
				aliasResolver.createSubTransactionFactory());
		root = new FinancialTreeNode();
		for (FinancialTransactionSource source : sources)
			processor.populateTree(source, root);

	}

	@Override
	public RectangleSplit<Object> getData(Object tag) {
		if (tag == null) {
			tag = root;
		}

		RectangleSplit<Object> rectangleSplit = new RectangleSplit<Object>();

		if (tag instanceof FinancialTreeNode) {
			FinancialTreeNode node = (FinancialTreeNode) tag;
			addFinancialNodeData(rectangleSplit, node);
		}

		return rectangleSplit;
	}

	private void addFinancialNodeData(RectangleSplit<Object> rectangleSplit,
			FinancialTreeNode node) {
		for (FinancialTreeNode child : node)
			rectangleSplit.addValue(
					(int) Math.abs(child.getTotalValue().getValue()), child);

		for (SubTransaction subTransaction : node.getSubTransactions())
			rectangleSplit.addValue(
					(int) Math.abs(subTransaction.getValue().getValue()),
					subTransaction);
	}

}
