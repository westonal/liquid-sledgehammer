package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.coltsoftware.liquidsledgehammer.androidexample.GraphDataSource;
import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.collections.FinancialTreeNode;
import com.coltsoftware.liquidsledgehammer.processing.Processor;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;
import com.coltsoftware.rectangleareagraph.RectangleSplit;
import com.google.gson.Gson;

public final class GraphDataSourceAdaptor implements GraphDataSource {

	private final Random rand = new Random(123);
	private final ArrayList<FinancialTransactionSource> sources;

	public GraphDataSourceAdaptor(
			ArrayList<FinancialTransactionSource> sources, File path)
			throws IOException {
		this.sources = sources;

		Processor processor = new Processor(
				FileToGroupAliasResolver.createAliasPathResolver(path),
				FileToGroupAliasResolver.createSubTransactionFactory(path));
		FinancialTreeNode root = new FinancialTreeNode();
		for (FinancialTransactionSource source : sources)
			processor.populateTree(source, root);

	}

	@Override
	public RectangleSplit<Object> getData(Object tag) {

		RectangleSplit<Object> rectangleSplit = new RectangleSplit<Object>();
		int objects = rand.nextInt(50) + 3;
		for (int i = 0; i < objects; i++)
			rectangleSplit.addValue(rand.nextInt(100) + 5, new Item(i,
					randomColor()));
		return rectangleSplit;
	}

	private int randomColor() {
		return 0xff000000 + rand.nextInt(0xffffff);
	}

	public class Item {

		private final String str;
		private final int color;

		public Item(int i, int color) {
			this.color = color;
			str = "" + i;
		}

		@Override
		public String toString() {
			return str;
		}

		public int getColor() {
			return color;
		}
	}

}
