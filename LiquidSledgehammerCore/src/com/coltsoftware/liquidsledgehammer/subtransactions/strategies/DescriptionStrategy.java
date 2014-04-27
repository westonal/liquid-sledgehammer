package com.coltsoftware.liquidsledgehammer.subtransactions.strategies;

public interface DescriptionStrategy {

	String getGroupName();
	
	boolean unassigned(String description);

}
