package models;

import statistics.PoissonDistribution;

public class ArrivalRate {
    protected double[] data;

    public ArrivalRate(double[] data) {
        this.data = data;
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

    public int[] getArrivalRateSample(int hour, int minute, int size) {
        double rate = this.getArrivalRate(hour, minute);
        PoissonDistribution distribution = new PoissonDistribution(rate);
        return distribution.sample(size);
    }

    public double[] getRates() {
        return this.data;
    }
}
