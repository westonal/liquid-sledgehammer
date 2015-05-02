package com.coltsoftware.liquidsledgehammer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.collections.FinancialTransactionList;
import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransaction;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class PathSourceWalker {

	private PathSourceWalker() {
	}

	public static FinancialTransactionSource combineSources(
			ArrayList<FinancialTransactionSource> sources) {
		FinancialTransactionList result = new FinancialTransactionList();
		for (FinancialTransactionSource source : sources)
			for (FinancialTransaction transaction : source)
				result.add(transaction);
		return result;
	}
	
	public static ArrayList<FinancialTransactionSource> loadAllSourcesBelowPath(
			Path path) throws IOException {
		final ArrayList<FinancialTransactionSource> sources = new ArrayList<FinancialTransactionSource>();
		Files.walkFileTree(path, new FileVisitor<Path>() {

			private ArrayList<FinancialTransactionSource> localSources = new ArrayList<FinancialTransactionSource>();
			private FinancialTransactionSourceInformation currentSource;

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				sources.add(combineSources(localSources));
				localSources.clear();
				currentSource = null;
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {

				final String institutionName = dir.getFileName().toString();
				currentSource = new FinancialTransactionSourceInformation() {

					@Override
					public String getName() {
						return institutionName;
					}
				};
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				if (file.toString().endsWith(".json"))
					localSources.add(loadSource(currentSource, file));
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				return FileVisitResult.CONTINUE;
			}

		});
		return sources;
	}

	protected static FinancialTransactionSource loadSource(
			FinancialTransactionSourceInformation sourceInformation,
			final Path file) throws IOException {
		Output.output("Loading " + file);
		InputStream stream = new FileInputStream(file.toFile());
		try {
			return JsonStreamTransactionSource.fromStream(stream,
					sourceInformation);
		} finally {
			stream.close();
		}
	}
}
