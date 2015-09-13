package com.coltsoftware.liquidsledgehammer.groups;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.coltsoftware.liquidsledgehammer.collections.AliasPathResolver;
import com.coltsoftware.liquidsledgehammer.subtransactions.SubTransactionFactory;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.DescriptionMatchingStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.DescriptionStrategyNamer;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.IncludeExcludeDescriptionStrategy;
import com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description.NamedDescriptionStrategy;
import com.google.gson.Gson;

public final class JsonGroupsFactory {

	private JsonGroupsFactory() {
	}

	public static AliasPathResolver createAliasPathResolver(File groupsPath)
			throws IOException {
		AliasPathResolver aliasPathResolver = new AliasPathResolver();

		for (GroupJson group : readGroupsJson(groupsPath))
			aliasPathResolver.put(group.uniqueName, group.path);

		return aliasPathResolver;
	}

	protected static GroupJson[] readGroupsJson(File groupsPath)
			throws IOException {
		FileReader reader = new FileReader(groupsPath);
		try {
			return new Gson().fromJson(reader, GroupJson[].class);
		} finally {
			reader.close();
		}
	}

	public static SubTransactionFactory createSubTransactionFactory(
			File groupsPath) throws IOException {
		DescriptionMatchingStrategy strategy = new DescriptionMatchingStrategy();
		for (GroupJson group : readGroupsJson(groupsPath)) {
			DescriptionStrategy descStrat = createStratForGroup(group);
			if (descStrat == null)
				continue;
			NamedDescriptionStrategy named = DescriptionStrategyNamer.name(
					group.uniqueName, descStrat);
			strategy.add(named);
		}

		SubTransactionFactory subTransactionFactory = new SubTransactionFactory();

		subTransactionFactory.setUnassignedValueStrategy(strategy);
		return subTransactionFactory;
	}

	private static DescriptionStrategy createStratForGroup(GroupJson group) {
		if (group.matchStrings == null && group.excludeStrings == null
				|| group.matchStrings.length == 0
				&& group.excludeStrings.length == 0)
			return null;

		IncludeExcludeDescriptionStrategy strat = new IncludeExcludeDescriptionStrategy();

		if (group.matchStrings != null) {
			for (String s : group.matchStrings)
				strat.addInclude(s);
		}
		if (group.excludeStrings != null) {
			for (String s : group.excludeStrings)
				strat.addExclude(s);
		}

		return strat;
	}

	public static class GroupJson {
		public String path;
		public String uniqueName;
		public String[] matchStrings;
		public String[] excludeStrings;
	}
}
