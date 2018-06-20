package com.n26.statistics.service;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.n26.statistics.entity.Statistics;
import com.n26.statistics.entity.Summary;

/**
 * Created by Nazia
 */
@Service
public class StatisticsServiceBean implements StatisticsService {

	/**
	 * This will keep stat entries ordered by timestamp.
	 */
	private final PriorityQueue<Statistics> stats;

	/**
	 * summaries will be updated as soon as stats collection are updated
	 */
	private volatile Summary summary;

	/**
	 * Statistic expire limit in milisecs
	 */
	private long expireMilis = 60 * 1000;

	/**
	 * used to update stats collection and summary in thread safe manner
	 */
	private Lock lock = new ReentrantLock();

	public StatisticsServiceBean(@Value("${limitOlderThan}") long limitOlderThan) {
		expireMilis = limitOlderThan;
		summary = new Summary(0, 0, 0);
		stats = new PriorityQueue<Statistics>();
	}

	@Override
	public void add(Statistics stat) throws StatAlreadyExpired {
		if (isExpired(stat))
			throw new StatAlreadyExpired();

		lock.lock();
		try {
			stats.add(stat);
			updateSummary(stat, StatisticsOperation.INSERT);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Summary getSummary() {
		return summary;
	}

	/**
	 * updates summary at once. To update all the stat summary variables in
	 * proper manner, they are wrapped in a Summary instance.
	 *
	 * @param stat
	 *            Statistics instance
	 * @param operation
	 *            StatOperation value
	 */
	private void updateSummary(Statistics stat, StatisticsOperation operation) {

		if (operation.equals(StatisticsOperation.INSERT)) {
			summary = new Summary(summary.getCount() + 1, summary.getSum()
					+ stat.getAmount(), (summary.getSum() + stat.getAmount())
					/ (summary.getCount() + 1),
					(summary.getMin() != null ? Math.min(summary.getMin(),
							stat.getAmount()) : stat.getAmount()),
					(summary.getMax() != null ? Math.max(summary.getMax(),
							stat.getAmount()) : stat.getAmount()));
		} else if (operation.equals(StatisticsOperation.REMOVE)) {
			summary = new Summary(summary.getCount() - 1, summary.getSum()
					- stat.getAmount(), (summary.getCount() == 1 ? 0
					: (summary.getSum() - stat.getAmount())
							/ (summary.getCount() - 1)), summary.getMin(),
					summary.getMax());
		}
	}

	/**
	 * Clearing entries from statistics collection
	 */
	@Override
	public void clear() {

		Statistics head = stats.peek();
		while (head != null && isExpired(head)) {
			lock.lock();
			// Lock is used to update both collection and the summary in thread
			// safe way

			try {
				// Rechecking if the entries are already removed
				head = stats.peek();
				if (isExpired(head))
					updateSummary(stats.poll(), StatisticsOperation.REMOVE);
			} finally {

				lock.unlock();
			}
			head = stats.peek();
		}
	}

	/**
	 * checks whether given Statistics is already expired or not.
	 *
	 * @param Statistics
	 * @return true if expired
	 */
	private boolean isExpired(Statistics stat) {
		return stat.getTimestamp() < (System.currentTimeMillis() - expireMilis);
	}

	private enum StatisticsOperation {
		INSERT, REMOVE;
	}

	public class StatAlreadyExpired extends Exception {
	}

}
