package com.coltsoftware.liquidsledgehammer.cmd;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class Context {

	private final ArrayList<FinancialTransactionSource> sources;

	private Context(ArrayList<FinancialTransactionSource> sources) {
		this.sources = sources;
	}

	public static Context fromArgs(Arguments args) {

		ArrayList<FinancialTransactionSource> sources = new ArrayList<FinancialTransactionSource>();

		for (String sourceFactoryName : sourceFactories.keySet()) {
			if (args.hasFlag(sourceFactoryName)) {
				SourceFactory factory = sourceFactories.get(sourceFactoryName);
				sources.addAll(factory.getSources(
						args.flagValue(sourceFactoryName), args));
			}
		}
		if (sources.isEmpty())
			throw new ContextException();

		return new Context(sources);
	}

	public ArrayList<FinancialTransactionSource> getSources() {
		return sources;
	}

	public static HashMap<String, SourceFactory> sourceFactories = new HashMap<String, SourceFactory>();

	public static void registerSourceFactory(String name,
			SourceFactory sourceFactory) {
		sourceFactories.put(name, sourceFactory);
	}

	public static void printSources(PrintStream out) {
		for (String sourceFactoryName : sourceFactories.keySet()) {
			out.println(String.format("    -%s %s", sourceFactoryName,
					sourceFactories.get(sourceFactoryName).getUsageLine()));
		}
	}
}
