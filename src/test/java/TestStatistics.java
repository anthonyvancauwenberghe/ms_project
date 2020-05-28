import configs.ArrivalRatesConfig;
import configs.SimulationConfig;
import contracts.Distribution;
import factories.ConsumerArrivalTimeFactory;
import factories.CorporateArrivalTimeFactory;
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

        double avg = 0;
        for (int i = 0; i < sampleSize; i++) {
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
        Distribution<Double> distribution = new NormalDistribution(mean, 1);
        int sampleSize = 10000000;

        Double[] values = distribution.sample(sampleSize);

        double avg = 0;
        for (int i = 0; i < sampleSize; i++) {
            avg += values[i];
        }
        avg /= sampleSize;

        BigDecimal bd = BigDecimal.valueOf(avg);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        assertEquals(mean, bd.doubleValue());
    }

    @Test
    void testCorporateInterArrivalTimeGeneration() {
        double avgTotalArrivalsIn24h = 0;

        for (double rate : ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE) {
            avgTotalArrivalsIn24h += 60.0 * rate;
        }

        double totalArrivalsFromSampling = 0;
        int iterations = 100000;

        for (int i = 0; i < iterations; i++) {
            totalArrivalsFromSampling += (new CorporateArrivalTimeFactory(
                    ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE,
                    SimulationConfig.SIMULATION_RUNTIME
            )).sampleArrivalRates().length;
        }

        double avg = totalArrivalsFromSampling / iterations;


        assertEquals(avgTotalArrivalsIn24h, (int) Math.round(avg));

    }

    @Test
    void testConsumerInterArrivalTimeGeneration() {
        int avgTotalArrivalsIn24h = ((int) ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE) * (60 * 24);

        double totalArrivalsFromSampling = 0;
        int iterations = 20000;

        for (int i = 0; i < iterations; i++) {
            totalArrivalsFromSampling += (new ConsumerArrivalTimeFactory(
                    ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                    ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
                    ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                    ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
            )).sampleArrivalRates().length;
        }

        double avg = totalArrivalsFromSampling / iterations;

        assertEquals(avgTotalArrivalsIn24h, (int) Math.round(avg));
    }

    @Test
    public void testConsumerSinusoidFunction() {
        ConsumerArrivalTimeFactory factory = new ConsumerArrivalTimeFactory(
                ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_MINUTE_VALUE,
                ArrivalRatesConfig.CONSUMER_ARRIVAL_LOWEST_HOUR
        );


        double sumRates = 0;
        for (int i = 0; i < ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD * 60 * 60; i++) {
            sumRates += factory.getRate(i);
        }
        double avg = (sumRates / (ArrivalRatesConfig.CONSUMER_ARRIVAL_RATE_PERIOD * 60 * 60)) * 60;

        assertEquals(ArrivalRatesConfig.CONSUMER_AVG_MINUTE_ARRIVAL_RATE, (int) Math.round(avg));

    }
}
