package com.coltsoftware.liquidsledgehammer.commands.jline;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.commands.jline.completers.CommandCompleter;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

public final class Prompter {
    private State state;
    private final ConsoleReader reader;
    private final HashMap<String, Command> commands;

    public Prompter(State state, HashMap<String, Command> commands) throws IOException {
        this.state = state;
        this.commands = commands;
        reader = new ConsoleReader();
        reader.setPrompt("jline> ");

        addCompleters(reader);
    }

    private void addCompleters(ConsoleReader reader) {
        reader.addCompleter(new CommandCompleter(commands));
    }

    protected void prompt() throws IOException {
        String line;
        PrintWriter out = new PrintWriter(reader.getOutput());

        while ((line = reader.readLine()) != null) {
            out.println("Echo: " + line);
            out.flush();
            runCommand(Arguments.fromString(line));
            out.flush();
            if ("exit".equalsIgnoreCase(line)) break;
        }
    }

    private void runCommand(Arguments arguments) {
        Command command = commands.get(arguments.first());
        if (command == null) {
            printHelp(System.out);
            return;
        }
        command.execute(state, arguments, System.out);
    }

    private void printHelp(PrintStream out) {
        for (String key : commands.keySet())
            commands.get(key).printUsage(out);
        out.println("    exit");
    }
}
