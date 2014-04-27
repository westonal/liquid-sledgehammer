package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

public interface DescriptionStrategy {

	String getGroupName();
	
	boolean unassigned(String description);

}
