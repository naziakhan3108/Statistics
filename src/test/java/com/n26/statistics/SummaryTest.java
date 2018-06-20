package com.n26.statistics;

import com.n26.statistics.entity.Summary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nazia
 */
@RunWith(SpringRunner.class)
public class SummaryTest {

    @Test
    public void testSummaryConstructor() {
        long count = 10;
        double sum = 1000;
        double avg = 100.0;

        Summary stat = new Summary(count, sum, avg, 50.0, 200.0);
        Assert.assertEquals(avg, stat.getAvg(), 0);
        Assert.assertEquals(count, stat.getCount(), 0);
        Assert.assertEquals(sum, stat.getSum(), 0);
    }

    @Test
    public void testSummaryToString() {
        long count = 10;
        double sum = 1000;
        double avg = 100.0;

        Summary stat = new Summary(count, sum, avg, 50.0, 200.0);
        Assert.assertEquals(
                "Summary{count=10, sum=1000.0, avg=100.0, min=50.0, max=200.0}",
                stat.toString());
    }


}
