package com.n26.statistics;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.statistics.entity.Statistics;
import com.n26.statistics.service.StatisticsService;
import com.n26.statistics.service.StatisticsServiceBean;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by Nazia
 */
@RunWith(SpringRunner.class)
public class StatisticsServiceTest {

    @Test
    public void testGetSummary() throws StatisticsServiceBean.StatAlreadyExpired {
    	StatisticsService statsService = new StatisticsServiceBean(60);

        Assert.assertEquals(statsService.getSummary().getCount(), 0);
        Assert.assertEquals(statsService.getSummary().getAvg(), 0, 0);

        double amount = 12.3;
        long timestamp = System.currentTimeMillis();
        Statistics stat = new Statistics(amount, timestamp);

        statsService.add(stat);
        statsService.add(stat);

        Assert.assertEquals(statsService.getSummary().getCount(), 2);
        Assert.assertEquals(statsService.getSummary().getSum(), amount * 2, 0);
        Assert.assertEquals(statsService.getSummary().getAvg(), amount, 0);
    }

    @Test
    public void testAddRejection() throws StatisticsServiceBean.StatAlreadyExpired {
        long limitOlderThan = 60 * 1000;
        StatisticsService statsService = new StatisticsServiceBean(limitOlderThan);

        double amount = 123.4;
        long timestamp = System.currentTimeMillis();

        Statistics stat = new Statistics(amount, timestamp);
        statsService.add(stat);

        Statistics alreadyExpiredStat = new Statistics(amount, timestamp - 62000);
        try {
            statsService.add(alreadyExpiredStat);
            fail("Statistics already expired cannot be deleted");
        } catch (StatisticsServiceBean.StatAlreadyExpired e) {}
    }

    @Test
    public void testClearingExpiredStats() throws StatisticsServiceBean.StatAlreadyExpired {
        long limitOlderThan = 60 * 1000;
        StatisticsService statsService = new StatisticsServiceBean(limitOlderThan);

        double amount = 12.3;
        long timestamp = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            statsService.add(new Statistics(amount, timestamp - (50 + i) * 1000));
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        statsService.clear();
        assertTrue(statsService.getSummary().getCount() - 5 < 2 );
    }

}
