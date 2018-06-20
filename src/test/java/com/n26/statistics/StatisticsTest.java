package com.n26.statistics;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.statistics.entity.Statistics;

/**
 * Created by Nazia
 */
@RunWith(SpringRunner.class)
public class StatisticsTest {

    @Test
    public void testStatConstructor() {
        double amount = 123456.789;
        long timestamp = 1505236870;
        Statistics stat = new Statistics(amount, timestamp);
        Assert.assertEquals(amount, stat.getAmount(), 0);
        Assert.assertEquals(timestamp, stat.getTimestamp());
    }

    @Test
    public void testStatToString() {
        double amount = 123456.789;
        long timestamp = 1505236870;
        Statistics stat = new Statistics(amount, timestamp);
        Assert.assertEquals(
                "Statistics{amount=123456.789, timestamp=1505236870}",
                stat.toString());
    }


    @Test
    public void testStatComparision() {

        double amount = 123456.789;
        long timestamp = 1505236870;
        Statistics stat1 = new Statistics(amount, timestamp);
        Statistics stat2 = new Statistics(amount, timestamp - 1);
        Statistics stat3 = new Statistics(amount, timestamp + 1);

        Assert.assertTrue(stat1.compareTo(stat1) == 0);
        Assert.assertTrue(stat1.compareTo(stat2) == 1);
        Assert.assertTrue(stat1.compareTo(stat3) == -1);
    }
}
