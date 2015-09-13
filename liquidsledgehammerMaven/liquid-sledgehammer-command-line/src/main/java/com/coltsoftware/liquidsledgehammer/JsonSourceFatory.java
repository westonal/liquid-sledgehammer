package com.coltsoftware.liquidsledgehammer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.SourceFactory;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class JsonSourceFatory implements SourceFactory {

	@Override
	public ArrayList<FinancialTransactionSource> getSources(String[] paths,
			Arguments otherArguments) {
		try {
			ArrayList<FinancialTransactionSource> result = new ArrayList<FinancialTransactionSource>();
			for (String pathString : paths) {
				Path path = new File(pathString).toPath();
				result.addAll(PathSourceWalker.loadAllSourcesBelowPath(path));
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUsageLine() {
		return "<path1> [<path2> ...]";
	}

}
