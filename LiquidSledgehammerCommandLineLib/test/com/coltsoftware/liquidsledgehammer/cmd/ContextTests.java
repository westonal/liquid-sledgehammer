package com.coltsoftware.liquidsledgehammer.cmd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;

public final class ContextTests {

	private Arguments args;

	@Before
	public void setUp() {
		args = Arguments.fromString("-jsonin .");
	}

	@Test
	public void can_create_with_json_input_source_specified() {
		Context context = Context.fromArgs(args);
		assertNotNull(context);
	}

	@Test(expected = ContextException.class)
	public void cant_create_without_json_input_source_specified() {
		Arguments invalidArgs = new Arguments(new String[] {});
		Context.fromArgs(invalidArgs);
	}

	@Test
	public void has_source_if_created() {
		Context context = Context.fromArgs(args);
		assertEquals(0, context.getSources().size());
	}

	@Test
	public void sources_are_same_each_call() {
		Context context = Context.fromArgs(args);
		assertSame(context.getSources(), context.getSources());
	}

}
