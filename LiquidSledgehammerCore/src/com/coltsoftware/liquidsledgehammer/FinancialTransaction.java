package com.coltsoftware.liquidsledgehammer;

import java.util.Date;
import java.util.GregorianCalendar;

public final class FinancialTransaction {

	private final long value;
	private String description;
	private Date date;

	private FinancialTransaction(long value, String description, Date date) {
		this.value = value;
		this.description = description;
		this.date = date;
	}

	public long getValue() {
		return value;
	}

	public static class Builder {

		private long value;
		private String description = "";
		private Date date;

		public Builder value(long value) {
			this.value = value;
			return this;
		}

		public FinancialTransaction build() {			
			if(date==null) throw new FinancialTransactionConstructionException();
			
			return new FinancialTransaction(value, description, date);
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder date(int year, int month, int day) {
			date = new GregorianCalendar(year, month - 1, day).getTime();
			return this;
		}

	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}
}
