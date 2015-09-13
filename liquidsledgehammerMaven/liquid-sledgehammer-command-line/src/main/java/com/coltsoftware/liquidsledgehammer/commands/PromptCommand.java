package com.coltsoftware.liquidsledgehammer.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;

import com.coltsoftware.liquidsledgehammer.Command;
import com.coltsoftware.liquidsledgehammer.State;
import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import jline.console.completer.Completer;

public final class PromptCommand implements Command {

	private final HashMap<String, Command> commands;

	public PromptCommand(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	@Override
	public void printUsage(PrintStream out) {
		out.println("    prompt");
	}

	@Override
	public void execute(State state, Arguments arguments, PrintStream out) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			new Prompter(br, state, out).prompt();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Completer getCompleter(State state){
		return null;
	}

	private void printHelp(PrintStream out) {
		for (String key : commands.keySet())
			commands.get(key).printUsage(out);
		out.println("    exit");
	}

	public class Prompter {

		private BufferedReader br;
		private PrintStream out;
		private State state;

		public Prompter(BufferedReader br, State state, PrintStream out) {
			this.br = br;
			this.state = state;
			this.out = out;
		}

		protected void prompt() throws IOException {
			while (true) {
				System.out.print("> ");
				String s = br.readLine();
				if ("exit".equalsIgnoreCase(s))
					break;
				runCommand(Arguments.fromString(s));
			}
		}

		private void runCommand(Arguments arguments) {
			Command command = commands.get(arguments.first());
			if (command == null) {
				printHelp(System.out);
				return;
			}
			command.execute(state, arguments, out);
		}

	}
}
