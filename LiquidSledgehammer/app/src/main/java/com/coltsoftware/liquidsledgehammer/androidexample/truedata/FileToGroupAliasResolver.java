package com.coltsoftware.liquidsledgehammer.androidexample.truedata;

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

public final class FileToGroupAliasResolver {

	private final File groupsFile;

	public FileToGroupAliasResolver(File groupsFile) {
		this.groupsFile = groupsFile;
	}

	public AliasPathResolver createAliasPathResolver() throws IOException {
		AliasPathResolver aliasPathResolver = new AliasPathResolver();

		FileReader reader = new FileReader(groupsFile);
		try {
			GroupJson[] groups = new Gson().fromJson(reader, GroupJson[].class);
			for (GroupJson group : groups)
				aliasPathResolver.put(group.uniqueName, group.path);
		} finally {
			reader.close();
		}

		return aliasPathResolver;
	}

	public SubTransactionFactory createSubTransactionFactory()
			throws IOException {
		DescriptionMatchingStrategy strategy = new DescriptionMatchingStrategy();

		FileReader reader = new FileReader(groupsFile);
		try {
			GroupJson[] groups = new Gson().fromJson(reader, GroupJson[].class);
			for (GroupJson group : groups) {
				DescriptionStrategy descStrat = createStratForGroup(group);
				if (descStrat == null)
					continue;
				NamedDescriptionStrategy named = DescriptionStrategyNamer.name(
						group.uniqueName, descStrat);
				strategy.add(named);
			}
		} finally {
			reader.close();
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
