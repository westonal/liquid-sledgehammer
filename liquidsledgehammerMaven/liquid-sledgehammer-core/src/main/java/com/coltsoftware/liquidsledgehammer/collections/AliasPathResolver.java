package com.coltsoftware.liquidsledgehammer.collections;

import java.util.HashMap;

public final class AliasPathResolver {

	private final HashMap<String, String> mapping = new HashMap<String, String>();

	public String resolve(String alias) {
		String sanatizedAlias = sanitiseAlias(alias);
		String path = mapping.get(sanatizedAlias);
		return path == null ? pathToMissingAlias(sanatizedAlias) : path;
	}

	public void put(String alias, String path) {
		String sanatizedAlias = sanitiseAlias(alias);
		mapping.put(sanatizedAlias, path);
	}

	private static String pathToMissingAlias(String sanatizedAlias) {
		if (sanatizedAlias.equals(""))
			return "Error.Uncategorised";
		return "Error.UnknownAlias." + sanatizedAlias;
	}

	private static String sanitiseAlias(String alias) {
		return alias == null ? "" : alias;
	}

}
