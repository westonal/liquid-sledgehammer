package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;

public interface Command extends UsagePrinter {
	void execute(State state, Arguments arguments, PrintStream out);
}
