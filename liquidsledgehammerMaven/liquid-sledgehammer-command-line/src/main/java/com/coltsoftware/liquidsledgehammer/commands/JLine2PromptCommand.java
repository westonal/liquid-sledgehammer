package com.coltsoftware.liquidsledgehammer.commands;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import jline.console.ConsoleReader;

import java.io.*;
import java.util.HashMap;

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
			new JLinePrompter(state).prompt();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void printHelp(PrintStream out) {
		for (String key : commands.keySet())
			commands.get(key).printUsage(out);
		out.println("    exit");
	}

	public class JLinePrompter {
		private State state;
        private final ConsoleReader reader;

		public JLinePrompter(State state) throws IOException {
            this.state = state;
            reader = new ConsoleReader();
            reader.setPrompt("jline> ");
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
	}
}
