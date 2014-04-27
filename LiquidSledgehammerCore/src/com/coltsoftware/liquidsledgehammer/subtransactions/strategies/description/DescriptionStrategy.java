package com.coltsoftware.liquidsledgehammer.subtransactions.strategies.description;

public interface DescriptionStrategy {

	String getGroupName();
	
	boolean matches(String description);

}
