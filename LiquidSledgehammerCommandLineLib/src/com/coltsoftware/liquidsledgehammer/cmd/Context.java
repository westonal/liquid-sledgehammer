package com.coltsoftware.liquidsledgehammer.cmd;

import java.util.ArrayList;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.sources.FinancialTransactionSource;

public final class Context {

	private final ArrayList<FinancialTransactionSource> sources;

	private Context(ArrayList<FinancialTransactionSource> sources) {
		this.sources = sources;
	}

	public static Context fromArgs(Arguments args) {

		ArrayList<FinancialTransactionSource> sources = null;

		if (args.hasFlag("jsonin")) {
			sources = new ArrayList<FinancialTransactionSource>();
		} else {
			throw new ContextException();
		}

		return new Context(sources);
	}

	public ArrayList<FinancialTransactionSource> getSources() {
		return sources;
	}
}
