package com.coltsoftware.liquidsledgehammer.cmd.commands;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import com.coltsoftware.liquidsledgehammer.cmd.ContextTestBase;
import com.coltsoftware.liquidsledgehammer.cmd.Context;
import com.coltsoftware.liquidsledgehammer.cmd.SourceFactory;

public final class BalanceTests extends ContextTestBase {

	private Context context;
	private SourceFactory mock1;
	private SourceFactory mock2;

	@Before
	public void setUp() {
		mock1 = createMockSourceFactory(1);
		mock2 = createMockSourceFactory(2);
		Context.registerSourceFactory("s1", mock1);
		Context.registerSourceFactory("s2", mock2);
		Arguments args = Arguments.fromString("-s1 thePath -s2 path2");
		context = Context.fromArgs(args);
	}

	@Test
	public void can_create_with_context() {
		new BalanceCommand(context);
	}

}
