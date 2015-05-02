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

}
