package com.coltsoftware.liquidsledgehammer.cmd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.coltsoftware.liquidsledgehammer.cmd.Arguments;
import static org.mockito.Mockito.*;

public final class ContextTests extends ContextTestBase {

	private Arguments args;
	private SourceFactory mock1;
	private SourceFactory mock2;

	@Before
	public void setUp() {
		mock1 = createMockSourceFactory(1);
		mock2 = createMockSourceFactory(2);
		Context.registerSourceFactory("jsonin", mock1);
		Context.registerSourceFactory("dbin", mock2);
		args = Arguments.fromString("-jsonin thePath -dbin path2");
	}

	@Test
	public void can_create_with_json_input_source_specified() {
		Context context = Context.fromArgs(args);
		assertNotNull(context);
	}

	@Test(expected = ContextException.class)
	public void cant_create_without_json_input_source_specified() {
		Arguments invalidArgs = Arguments
				.fromString("-jsonin2 thePath -dbin2 path2");
		Context.fromArgs(invalidArgs);
	}

	@Test
	public void has_sources_from_single_factory() {
		Context context = Context.fromArgs(Arguments.fromString("-jsonin a"));
		assertEquals(1, context.getSources().size());
	}

	@Test
	public void has_sources_from_two_factories() {
		Context context = Context.fromArgs(Arguments
				.fromString("-jsonin a -dbin b"));
		assertEquals(3, context.getSources().size());
	}

	@Test
	public void sources_are_same_each_call() {
		Context context = Context.fromArgs(args);
		assertSame(context.getSources(), context.getSources());
	}

	@Test
	public void mocks_were_given_path() {
		Context.fromArgs(args);
		verify(mock1).getSources("thePath", args);
		verify(mock2).getSources("path2", args);
	}

}
