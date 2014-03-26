package com.coltsoftware.liquidsledgehammer.collections;

import java.util.HashMap;

public final class AliasPathResolver {

	private final HashMap<String, String> mapping = new HashMap<String, String>();

	public String resolve(String alias) {
		alias = sanitiseAlias(alias);
		String path = mapping.get(alias);
		if (path == null) {
			if (alias.equals(""))
				return "Error.Uncategorised";
			return "Error.UnknownAlias." + alias;
		}
		return path;
	}

	public void put(String alias, String path) {
		alias = sanitiseAlias(alias);
		mapping.put(alias, path);
	}

	private static String sanitiseAlias(String alias) {
		if (alias == null)
			alias = "";
		return alias;
	}

}
