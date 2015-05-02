package com.coltsoftware.liquidsledgehammer.cmd.commands;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.Context;

public final class BalanceTests {

	private Context context;

	@Before
	public void setUp() {
		context = Context.fromArgs(new Arguments(
				new String[] { "-jsonin", "." }));
	}

	@Test
	public void can_create_with_context() {
		new BalanceCommand(context);
	}

}
