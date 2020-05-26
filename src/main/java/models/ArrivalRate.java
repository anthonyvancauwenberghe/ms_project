package models;

import factories.InterArrivalTimesFactory;
import org.apache.commons.lang3.ArrayUtils;
import statistics.PoissonDistribution;

import java.util.Arrays;
import java.util.Collections;

public class ArrivalRate {
    protected double[] data;

    protected double max;

    public ArrivalRate(double[] data) {
        this.data = data;
        this.max = Collections.max(Arrays.asList(ArrayUtils.toObject(data)));
    }

    /**
     * Gives the arrival rate for a certain time of the day (granularity per minute)
     *
     * @param hour   int
     * @param minute int
     * @return double
     */
    public double getArrivalRate(int hour, int minute) {
        return this.data[hour * 60 + minute];
    }

    public int getArrivalRateSample(int hour, int minute) {
        double rate = this.getArrivalRate(hour, minute);
        PoissonDistribution distribution = new PoissonDistribution(rate);
        return distribution.sample();
    }

    public Integer[] getArrivalRateSample(int hour, int minute, int size) {
        double rate = this.getArrivalRate(hour, minute);
        PoissonDistribution distribution = new PoissonDistribution(rate);
        return distribution.sample(size);
    }

    public double[] getRates() {
        return this.data;
    }

    public double getMax() {
        return this.max;
    }
}
