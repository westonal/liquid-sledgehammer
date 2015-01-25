package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.util.Log;

import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class PathSourceWalker {

	private static final String TAG = "PathSourceWalker";

	private PathSourceWalker() {
	}

	public static ArrayList<FinancialTransactionSource> loadAllSourcesBelowPath(
			File path) throws IOException {
		final ArrayList<FinancialTransactionSource> sources = new ArrayList<FinancialTransactionSource>();
		addSubFilesToSource(sources, path);
		return sources;
	}

	private static void addSubFilesToSource(
			ArrayList<FinancialTransactionSource> sources, File path)
			throws IOException {
		Log.d(TAG, "Walking " + path);
		for (File child : path.listFiles()) {
			if (child.isDirectory())
				addSubFilesToSource(sources, child);
			else if (child.toString().endsWith(".json"))
				sources.add(loadSource(child));
		}
	}

	protected static FinancialTransactionSource loadSource(final File file)
			throws IOException {
		Log.d(TAG, "Loading " + file);
		InputStream stream = new FileInputStream(file);
		try {
			return JsonStreamTransactionSource.fromStream(stream,
					new FinancialTransactionSourceInformation() {

						@Override
						public String getName() {
							return file.getParentFile().getName();
						}
					});
		} finally {
			stream.close();
		}
	}
}
