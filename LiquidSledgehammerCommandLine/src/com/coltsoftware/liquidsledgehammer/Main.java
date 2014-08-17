package com.coltsoftware.liquidsledgehammer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.LocalDate;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.SubTransaction;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;
import com.coltsoftware.liquidsledgehammer.subtransactions.SubTransactionFactory;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.DescriptionMatchingStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategyNamer;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.IncludeExcludeDescriptionStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.NamedDescriptionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Temp\\Transactions");
		ArrayList<FinancialTransactionSource> sources = PathSourceWalker
				.loadAllSourcesBelowPath(f.toPath());
		Processor processor = new Processor(createAliasPathResolver(f),
				createSubTransactionFactory(f));
		FinancialTreeNode root = new FinancialTreeNode();
		for (FinancialTransactionSource source : sources)
			processor.populateTree(source, root);

		Output.output("");
		// outputTree(root);
		outputJson(root, "output");
		outputJson(root.findOrCreate("External"), "external");
		outputJson(root.findOrCreate("Error"), "error");
	}

	private static SubTransactionFactory createSubTransactionFactory(File path)
			throws IOException {
		DescriptionMatchingStrategy strategy = new DescriptionMatchingStrategy();

		path = new File(path, "..\\groups.json");

		FileReader reader = new FileReader(path);
		try {
			GroupJson[] groups = new Gson().fromJson(reader, GroupJson[].class);
			for (GroupJson group : groups) {
				DescriptionStrategy descStrat = createStratForGroup(group);
				if (descStrat == null)
					continue;
				NamedDescriptionStrategy named = DescriptionStrategyNamer.name(
						group.uniqueName, descStrat);
				strategy.add(named);
			}
		} finally {
			reader.close();
		}

		SubTransactionFactory subTransactionFactory = new SubTransactionFactory();

		subTransactionFactory.setUnassignedValueStrategy(strategy);
		return subTransactionFactory;
	}

	private static DescriptionStrategy createStratForGroup(GroupJson group) {
		if (group.matchStrings == null && group.excludeStrings == null
				|| group.matchStrings.length == 0
				&& group.excludeStrings.length == 0)
			return null;

		IncludeExcludeDescriptionStrategy strat = new IncludeExcludeDescriptionStrategy();

		if (group.matchStrings != null) {
			for (String s : group.matchStrings)
				strat.addInclude(s);
		}
		if (group.excludeStrings != null) {
			for (String s : group.excludeStrings)
				strat.addExclude(s);
		}

		return strat;
	}

	private static AliasPathResolver createAliasPathResolver(File path)
			throws IOException {
		AliasPathResolver aliasPathResolver = new AliasPathResolver();

		path = new File(path, "..\\groups.json");

		FileReader reader = new FileReader(path);
		try {
			GroupJson[] groups = new Gson().fromJson(reader, GroupJson[].class);
			for (GroupJson group : groups)
				aliasPathResolver.put(group.uniqueName, group.path);
		} finally {
			reader.close();
		}

		return aliasPathResolver;
	}

	private static void outputTree(FinancialTreeNode root) {
		Output.output(String.format("Treenode %s = %s", root.getName(),
				root.getTotalValue()));
		for (SubTransaction transaction : root.getSubTransactions())
			Output.output(String.format("  %s = %s", transaction
					.getTransaction().getDescription(), transaction.getValue()));

		for (FinancialTreeNode node : root)
			outputTree(node);
	}

	private static void outputJson(FinancialTreeNode root, String name)
			throws IOException {
		TreeNode treeNode = createTreeNode(root);
		String json = new GsonBuilder().setPrettyPrinting().create()
				.toJson(treeNode);
		// Output.output(json);
		File path = new File(new File(outPath), name + ".json");
		FileWriter writer = new FileWriter(path.getPath());
		try {
			writer.write(json);
		} finally {
			writer.close();
		}
	}

	private static TreeNode createTreeNode(FinancialTreeNode root) {
		TreeNode treeNode = new TreeNode();
		treeNode.name = root.getName();
		treeNode.value = root.getTotalValue().toString();
		treeNode.transactions = createTransactions(root.getSubTransactions());
		treeNode.children = createTreeNodeChildren(root);
		return treeNode;
	}

	private static SubTransactionNode[] createTransactions(
			Iterable<SubTransaction> subTransactions) {
		ArrayList<SubTransactionNode> result = new ArrayList<SubTransactionNode>();
		for (SubTransaction node : subTransactions)
			result.add(createSubTransactionNode(node));
		return result.toArray(new SubTransactionNode[result.size()]);
	}

	private static SubTransactionNode createSubTransactionNode(
			SubTransaction node) {
		SubTransactionNode result = new SubTransactionNode();
		FinancialTransaction transaction = node.getTransaction();
		result.date = transaction.getDate().toString("yyyy-MM-dd");
		result.source = transaction.getSource().getName();
		result.description = transaction.getDescription();
		result.value = node.getValue().toString();
		return result;
	}

	private static TreeNode[] createTreeNodeChildren(FinancialTreeNode root) {
		ArrayList<TreeNode> result = new ArrayList<TreeNode>();
		for (FinancialTreeNode node : root)
			result.add(createTreeNode(node));
		return result.toArray(new TreeNode[result.size()]);
	}

	@SuppressWarnings("unused")
	public static class TreeNode {
		private String name;
		private String value;
		private SubTransactionNode[] transactions;
		private TreeNode[] children;
	}

	@SuppressWarnings("unused")
	public static class SubTransactionNode {
		private String date;
		private String source;
		private String description;
		private String value;
	}

	public static class GroupJson {
		public String path;
		public String uniqueName;
		public String[] matchStrings;
		public String[] excludeStrings;
	}
}
