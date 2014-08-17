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

import com.coltsoftware.liquidsledgehammer.json.JsonStreamTransactionSource;
import com.coltsoftware.liquidsledgehammer.model.FinancialTransactionSourceInformation;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class PathSourceWalker {

	private PathSourceWalker() {
	}

	public static ArrayList<FinancialTransactionSource> loadAllSourcesBelowPath(
			Path path) throws IOException {
		final ArrayList<FinancialTransactionSource> sources = new ArrayList<FinancialTransactionSource>();
		Files.walkFileTree(path, new FileVisitor<Path>() {

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				if (file.toString().endsWith(".json"))
					sources.add(loadSource(file));
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

	protected static FinancialTransactionSource loadSource(final Path file)
			throws IOException {
		Output.output("Loading " + file);
		InputStream stream = new FileInputStream(file.toFile());
		try {
			return JsonStreamTransactionSource.fromStream(stream,
					new FinancialTransactionSourceInformation() {

						@Override
						public String getName() {
							return file.getParent().getFileName().toString();
						}
					});
		} finally {
			stream.close();
		}
	}
}
