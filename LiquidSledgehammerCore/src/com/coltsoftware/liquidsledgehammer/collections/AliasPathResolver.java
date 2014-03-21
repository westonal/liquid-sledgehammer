package com.coltsoftware.liquidsledgehammer.collections;

import java.util.HashMap;

public final class AliasPathResolver {

	private final HashMap<String, String> mapping = new HashMap<String, String>();

	public String resolve(String alias) {
		String path = mapping.get(alias);
		return path == null ? "Error.UnknownAlias." + alias : path;
	}

	public void put(String alias, String path) {
		mapping.put(alias, path);
	}

}
