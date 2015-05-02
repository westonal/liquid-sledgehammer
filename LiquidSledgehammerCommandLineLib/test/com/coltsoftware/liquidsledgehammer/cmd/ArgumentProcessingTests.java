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

	public static class ArgumentsFromStringTests {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = Arguments
					.fromString("-input firstpath  -output secondpath -d ");
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

		@Test
		public void returns_true_if_has_flag() {
			assertTrue(arguments.hasFlag("d"));
		}
	}

	public static class FlagValueSearchForMultiplePossibleArguments {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = Arguments.fromString("-a 1 -b 2 -c 3");
		}

		@Test
		public void finds_a() {
			assertEquals("1", arguments.flagValue("a", "b", "c"));
		}

		@Test
		public void finds_b() {
			assertEquals("2", arguments.flagValue("f", "b", "c"));
		}

		@Test
		public void finds_c() {
			assertEquals("3", arguments.flagValue("f", "g", "c"));
		}

		@Test
		public void finds_null() {
			assertNull(arguments.flagValue("f", "g", "h"));
		}

		@Test(expected = IllegalArgumentException.class)
		public void checks_all_arguments_first() {
			arguments.flagValue("a", "-b");
		}
	}

	public static class HasFlagSearchForMultiplePossibleArguments {

		private Arguments arguments;

		@Before
		public void setUp() {
			arguments = Arguments.fromString("-a 1 -b 2 -c 3");
		}

		@Test
		public void finds_in_pos_one() {
			assertTrue(arguments.hasFlag("a", "b", "c"));
		}

		@Test
		public void finds_in_pos_two() {
			assertTrue(arguments.hasFlag("f", "b", "c"));
		}

		@Test
		public void finds_in_pos_three() {
			assertTrue(arguments.hasFlag("f", "g", "c"));
		}

		@Test
		public void false_if_in_no_position() {
			assertFalse(arguments.hasFlag("f", "g", "h"));
		}

		@Test(expected = IllegalArgumentException.class)
		public void checks_all_arguments_first() {
			arguments.hasFlag("a", "-b");
		}
	}

}
