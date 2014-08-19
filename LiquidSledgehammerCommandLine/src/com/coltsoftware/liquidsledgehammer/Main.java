package com.coltsoftware.liquidsledgehammer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

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
import com.coltsoftware.rectangleareagraph.Rectangle;
import com.coltsoftware.rectangleareagraph.RectangleSplit;
import com.coltsoftware.rectangleareagraph.RectangleSplit.SplitResult;
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
		outputImage(root.findOrCreate("External"), "external");
	}

	private static void outputImage(FinancialTreeNode node, String fileName) {
		RectangleSplit<FinancialTreeNode> rectangleSplit = new RectangleSplit<FinancialTreeNode>();
		for (FinancialTreeNode subNode : node)
			rectangleSplit
					.addValue(
							Math.abs((int) subNode.getTotalValue().getValue()),
							subNode);
		int width = 1024;
		int height = 768;
		List<SplitResult<FinancialTreeNode>> split = rectangleSplit
				.split(new Rectangle(0, 0, width - 1, height - 1));
		RenderedImage image = render(split, width, height);
		saveImage(image, fileName);
	}

	private static RenderedImage render(
			List<SplitResult<FinancialTreeNode>> split, int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		Paint p = Color.BLACK;
		graphics.setPaint(p);
		FontMetrics fontMetrics = graphics.getFontMetrics();
		for (SplitResult<FinancialTreeNode> result : split) {
			Rectangle rectangle = result.getRectangle();
			graphics.drawRect(rectangle.getLeft(), rectangle.getTop(),
					rectangle.getWidth(), rectangle.getHeight());
			FinancialTreeNode tag = result.getTag();
			int x = rectangle.getLeft() + rectangle.getWidth() / 2;
			int y = rectangle.getTop() + rectangle.getHeight() / 2;
			drawTextCentered(graphics, fontMetrics, tag.getName(), x, y);
			drawTextCentered(graphics, fontMetrics, tag.getTotalValue()
					.toString(), x, y + fontMetrics.getHeight());
		}
		return bufferedImage;
	}

	private static void drawTextCentered(Graphics2D graphics,
			FontMetrics fontMetrics, String description, int x, int y) {
		int stringWidth = fontMetrics.stringWidth(description);
		graphics.drawChars(description.toCharArray(), 0, description.length(),
				x - stringWidth / 2, y);
	}

	private static void saveImage(RenderedImage bufferedImage, String fileName) {
		try {
			File outputfile = new File(fileName + ".png");
			ImageIO.write(bufferedImage, "png", outputfile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
