package com.coltsoftware.liquidsledgehammer.cmd;

public final class Arguments {

	private static final String FLAG_PREFIX = "-";

	private final String[] args;

	public Arguments(String[] args) {
		this.args = args;
	}

	public boolean hasFlag(String flag) {
		validateFlagArgument(flag);
		String formattedFlag = formatFlag(flag);
		for (String arg : args)
			if (formattedFlag.equals(arg))
				return true;
		return false;
	}

	public String flagValue(String flag) {
		validateFlagArgument(flag);
		String formattedFlag = formatFlag(flag);
		for (int i = 0; i < args.length; i++)
			if (formattedFlag.equals(args[i]))
				return args[i + 1];
		return null;
	}

	protected static String formatFlag(String flag) {
		return FLAG_PREFIX + flag;
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

}
