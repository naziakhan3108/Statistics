package com.n26.statistics.entity;

/**
 * Created by Nazia
 */
public class Statistics implements Comparable<Statistics> {
	private double amount;
	private long timestamp;

	public Statistics() {
	}

	public Statistics(double amount, long timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "Statistics{" + "amount=" + amount + ", timestamp=" + timestamp
				+ '}';
	}

	public int compareTo(Statistics o) {
		if (o.getTimestamp() == getTimestamp())
			return 0;
		else if (o.getTimestamp() > getTimestamp())
			return -1;
		else
			return 1;
	}

}
