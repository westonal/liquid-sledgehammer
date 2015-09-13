package com.coltsoftware.liquidsledgehammer.commands.jline;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.commands.jline.completers.CommandCompleter;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public final class JLine2PromptCommand implements Command {

	private final HashMap<String, Command> commands;

    public JLine2PromptCommand(HashMap<String, Command> commands) {
		this.commands = commands;
    }

	@Override
	public void printUsage(PrintStream out) {
		out.println("    jlineprompt");
	}

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		try {
			new Prompter(state, commands).prompt();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public Completer getCompleter(State state) {
        return null;
    }
}
