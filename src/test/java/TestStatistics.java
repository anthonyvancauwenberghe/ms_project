import contracts.Distribution;
import org.junit.jupiter.api.Test;
import statistics.NormalDistribution;
import statistics.PoissonDistribution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStatistics {

    @Test
    void testPoissonDistribution() {
        double mean = 5.00;
        Distribution<Integer> distribution = new PoissonDistribution(mean);
        int sampleSize = 10000000;

        Integer[] values = distribution.sample(sampleSize);

        double avg=0;
        for(int i = 0; i<sampleSize; i++){
            avg += values[i];
        }
        avg /= sampleSize;

        BigDecimal bd = BigDecimal.valueOf(avg);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        assertEquals(mean, bd.doubleValue());
    }

    @Test
    void testNormalDistribution() {
        double mean = 5.00;
        Distribution<Double> distribution = new NormalDistribution(mean,1);
        int sampleSize = 10000000;

        Double[] values = distribution.sample(sampleSize);

        double avg=0;
        for(int i = 0; i<sampleSize; i++){
            avg += values[i];
        }
        avg /= sampleSize;

        BigDecimal bd = BigDecimal.valueOf(avg);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        assertEquals(mean, bd.doubleValue());
    }

}
