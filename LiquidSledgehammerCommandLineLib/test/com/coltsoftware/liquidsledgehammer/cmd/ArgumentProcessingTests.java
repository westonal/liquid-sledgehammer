package com.coltsoftware.liquidsledgehammer.cmd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class ArgumentProcessingTests {

	public static class ZeroArguments {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] {});
		}

		@Test
		public void returns_false_for_flag() {
			assertFalse(arguments.hasFlag("m"));
		}
	}

	public static class InvalidHasFlagQueries {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] { "-m" });
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_null_flag() {
			arguments.hasFlag(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_flag_if_specify_dash() {
			arguments.hasFlag("-m");
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_empty_flag() {
			arguments.hasFlag("");
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_pre_whitespace() {
			arguments.hasFlag(" m");
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_post_whitespace() {
			arguments.hasFlag("m ");
		}
	}

	public static class InvalidFlagValueQueries {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] { "-m", "v", "-n" });
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_null_flag() {
			arguments.flagValue(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_flag_if_specify_dash() {
			arguments.flagValue("-m");
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_empty_flag() {
			arguments.flagValue("");
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_pre_whitespace() {
			arguments.flagValue(" m");
		}

		@Test(expected = IllegalArgumentException.class)
		public void throws_for_post_whitespace() {
			arguments.flagValue("m ");
		}

		@Test
		public void returns_null_if_flag_has_no_value() {
			assertNull(arguments.flagValue("n"));
		}
	}

	public static class OneFlagArgument {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] { "-m" });
		}

		@Test
		public void returns_true_for_flag() {
			assertTrue(arguments.hasFlag("m"));
		}

		@Test
		public void returns_false_for_other_flag() {
			assertFalse(arguments.hasFlag("n"));
		}
	}

	public static class TwoFlagArguments {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] { "-m", "-f2" });
		}

		@Test
		public void returns_true_for_flag_m() {
			assertTrue(arguments.hasFlag("m"));
		}

		@Test
		public void returns_true_for_flag_f2() {
			assertTrue(arguments.hasFlag("f2"));
		}

		@Test
		public void returns_false_for_other_flag() {
			assertFalse(arguments.hasFlag("f3"));
		}
	}

	public static class OneValuedArgument {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] { "-path", "somepath" });
		}

		@Test
		public void returns_path() {
			assertEquals("somepath", arguments.flagValue("path"));
		}

		@Test
		public void returns_null_if_flag_not_found() {
			assertNull(arguments.flagValue("p"));
		}
	}

	public static class TwoValuedArgument {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = new Arguments(new String[] { "-input", "firstpath",
					"-output", "secondpath" });
		}

		@Test
		public void returns_firstpath() {
			assertEquals("firstpath", arguments.flagValue("input"));
		}

		@Test
		public void returns_secondpath() {
			assertEquals("secondpath", arguments.flagValue("output"));
		}

		@Test
		public void returns_null_if_flag_not_found() {
			assertNull(arguments.flagValue("p"));
		}
	}

}
