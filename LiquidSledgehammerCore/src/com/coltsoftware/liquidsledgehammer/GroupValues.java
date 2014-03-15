package com.coltsoftware.liquidsledgehammer;

public final class GroupValues {

	private long unassigned;
	private long other;

	GroupValues(long unassigned, long other) {
		this.unassigned = unassigned;
		this.other = other;		
	}

	public long getUnassigned() {
		return unassigned;
	}

	public long get(String string) {
		return other;
	}

}
