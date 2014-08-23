package com.coltsoftware;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

public class Main {
	public static void main(String[] args) throws IOException {

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	private static void createAndShowGUI() throws IOException {

		// JPanel contentPane = new JPanel();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 1024);
		// contentPane.setBounds(0, 0, 100, 100);
		// contentPane.setLayout(null);
		// frame.getContentPane().add(contentPane);

		Container pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		frame.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		// c.anchor = GridBagConstraints.PAGE_START;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(new JButton("Back"), c);
		c.gridx = 0;
		c.gridy = 1;
		// c.anchor = GridBagConstraints.PAGE_END;
		frame.add(pane, c);

		pane.add(new JButton("Back"), c);
		pane.add(new JButton("Back"), c);

		addComponents(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	private static String inPath = "C:\\Users\\Alan\\Documents\\SVN Finances\\JsonInput\\Transactions\\";

	private static void addComponents(Container contentPane) throws IOException {
		File f = new File(inPath);
		ArrayList<FinancialTransactionSource> sources = PathSourceWalker
				.loadAllSourcesBelowPath(f.toPath());
		Processor processor = new Processor(createAliasPathResolver(f),
				createSubTransactionFactory(f));
		FinancialTreeNode root = new FinancialTreeNode();
		for (FinancialTransactionSource source : sources)
			processor.populateTree(source, root);

		outputNodes(root.findOrCreate("External"), contentPane,
				contentPane.getBounds());
	}

	private static void outputNodes(final FinancialTreeNode node,
			final Container jFrame, final java.awt.Rectangle rectangle) {
		GridBagConstraints c = new GridBagConstraints();
		jFrame.removeAll();
		RectangleSplit<FinancialTreeNode> rectangleSplit = new RectangleSplit<FinancialTreeNode>();
		for (FinancialTreeNode subNode : node)
			rectangleSplit
					.addValue(
							Math.abs((int) subNode.getTotalValue().getValue()),
							subNode);
		int width = 100;
		int height = 100;
		List<SplitResult<FinancialTreeNode>> split = rectangleSplit
				.split(new Rectangle(0, 0, width - 1, height - 1));
		//
		// JButton backButton = new JButton("Back");
		// backButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// outputNodes(node.getParent(), jFrame, rectangle);
		// }
		// });
		// contentPaneOuter.add(backButton);
		// jFrame.add

		for (final SplitResult<FinancialTreeNode> result : split) {
			String buttonText = String.format("%s %s", result.getTag()
					.getName(), result.getTag().getTotalValue());
			JButton button = new JButton(buttonText);
			Rectangle rectangle2 = result.getRectangle();
			// button.setBounds(rectangle2.getLeft(), rectangle2.getTop(),
			// rectangle2.getWidth(), rectangle2.getHeight());
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					outputNodes(node.findOrCreate(result.getTag().getName()),
							jFrame, rectangle);
				}
			});
			c.weightx = rectangle2.getWidth();
			c.weighty = rectangle2.getHeight();
			c.gridx = rectangle2.getLeft();
			c.gridy = rectangle2.getTop();
			c.gridwidth = rectangle2.getWidth();
			c.gridheight = rectangle2.getHeight();
			jFrame.add(button, c);
		}
		// jFrame.removeAll();
		// jFrame.add(contentPane);
		// contentPane.revalidate();
		// jFrame.validate();
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
