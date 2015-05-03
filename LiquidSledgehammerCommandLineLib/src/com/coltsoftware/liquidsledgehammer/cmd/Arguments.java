package com.coltsoftware.liquidsledgehammer.cmd;

import java.util.ArrayList;

public final class Arguments {

	private static final String FLAG_PREFIX = "-";

	private final String[] args;

	public static Arguments fromString(String argumentString) {
		return new Arguments(argumentString.split("\\s"));
	}

	public Arguments(String[] args) {
		this.args = args;
	}

	public boolean hasFlag(String... flags) {
		validateFlagArguments(flags);
		for (String flag : flags) {
			String formattedFlag = formatFlag(flag);
			for (String arg : args)
				if (formattedFlag.equals(arg))
					return true;
		}
		return false;
	}

	public String flagValue(String... flags) {
		validateFlagArguments(flags);
		for (String flag : flags) {
			String formattedFlag = formatFlag(flag);
			for (int i = 0; i < args.length - 1; i++)
				if (formattedFlag.equals(args[i]))
					return args[i + 1];
		}
		return null;
	}

	public String[] flagValues(String... flags) {
		validateFlagArguments(flags);
		int count = 0;
		String[] tempResult = new String[args.length];
		for (String flag : flags) {
			String formattedFlag = formatFlag(flag);
			boolean foundArg = false;
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if (isFlag(arg)) {
					foundArg = formattedFlag.equals(arg);
					continue;
				}
				if (foundArg) {
					tempResult[i] = arg;
					count++;
				}
			}
		}
		if (count == 0)
			return null;
		return copyNonNullStringsToNewStringArray(count, tempResult);
	}

	private static boolean isFlag(String argument) {
		return argument.startsWith(FLAG_PREFIX);
	}

	protected static String formatFlag(String flag) {
		return FLAG_PREFIX + flag;
	}

	protected static void validateFlagArguments(String... flags) {
		if (flags == null)
			throw new IllegalArgumentException();
		for (String flag : flags)
			validateFlagArgument(flag);
	}

	protected static void validateFlagArgument(String flag) {
		if (flag == null)
			throw new IllegalArgumentException();
		if (flag.equals(""))
			throw new IllegalArgumentException();
		if (!flag.equals(flag.trim()))
			throw new IllegalArgumentException();
		if (flag.startsWith(FLAG_PREFIX))
			throw new IllegalArgumentException();
	}

	protected static String[] copyNonNullStringsToNewStringArray(
			int nonNullCount, String[] inputWithNulls) {
		int idx = 0;
		String[] result = new String[nonNullCount];
		for (String arg : inputWithNulls)
			if (arg != null)
				result[idx++] = arg;
		return result;
	}

	public String first() {
		return args[0];
	}

	public String second() {
		return args.length > 1 ? args[1] : "";
	}
}
