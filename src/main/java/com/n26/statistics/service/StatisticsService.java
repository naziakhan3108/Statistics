package com.n26.statistics.service;

import com.n26.statistics.entity.Statistics;
import com.n26.statistics.entity.Summary;

/**
 * Created by Nazia
 */
public interface StatisticsService {
	void add(Statistics stat) throws StatisticsServiceBean.StatAlreadyExpired;

	Summary getSummary();

	void clear();
}
