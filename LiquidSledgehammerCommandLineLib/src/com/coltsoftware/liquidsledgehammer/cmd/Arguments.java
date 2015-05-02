package com.coltsoftware.liquidsledgehammer.cmd;

public final class Arguments {

	private static final String FLAG_PREFIX = "-";

	private final String[] args;

	public Arguments(String[] args) {
		this.args = args;
	}

	public boolean hasFlag(String flag) {
		validateFlagArgument(flag);
		String flagWithDash = FLAG_PREFIX + flag;
		for (String arg : args)
			if (flagWithDash.equals(arg))
				return true;
		return false;
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
