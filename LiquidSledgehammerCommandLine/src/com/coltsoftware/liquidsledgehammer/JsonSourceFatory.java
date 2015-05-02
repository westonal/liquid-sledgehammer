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
	public ArrayList<FinancialTransactionSource> getSources(String pathString,
			Arguments otherArguments) {
		try {
			Path path = new File(pathString).toPath();
			return PathSourceWalker.loadAllSourcesBelowPath(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUsageLine() {
		return "<path>";
	}

}
