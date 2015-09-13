package com.coltsoftware.liquidsledgehammer;

import java.io.PrintStream;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import jline.console.completer.Completer;

public interface Command extends UsagePrinter {
    void execute(State state, Arguments arguments, PrintStream out);

    Completer getCompleter(State state);
}
