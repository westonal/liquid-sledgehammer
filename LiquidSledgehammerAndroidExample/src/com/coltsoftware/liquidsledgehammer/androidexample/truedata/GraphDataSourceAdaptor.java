package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.coltsoftware.liquidsledgehammer.androidexample.GraphDataSource;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;
import com.coltsoftware.rectangleareagraph.RectangleSplit;

public final class GraphDataSourceAdaptor implements GraphDataSource {

	private final FinancialTreeNode root;
	private final Context context;

	public GraphDataSourceAdaptor(Context context,
			ArrayList<FinancialTransactionSource> sources, File path)
			throws IOException {
		this.context = context;
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

		if (tag instanceof SubTransaction) {
			SubTransaction subTransaction = (SubTransaction) tag;
			showDialogForSubTransaction(subTransaction);
		}

		return rectangleSplit;
	}

	private void showDialogForSubTransaction(SubTransaction subTransaction) {

		StringBuilder sb = new StringBuilder();
		FinancialTransaction transaction = subTransaction.getTransaction();
		sb.append(String.format("Date: %s\n", transaction.getDate()));
		sb.append(String.format("Transaction Value: %s\n",
				transaction.getValue()));
		sb.append(String.format("Description: %s\n",
				transaction.getDescription()));
		sb.append(String.format("Source: %s\n", transaction.getSource()
				.getName()));
		sb.append(String.format("Sub transaction Value: %s\n",
				subTransaction.getValue()));

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(sb.toString()).setPositiveButton(
				android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		builder.create().show();
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
