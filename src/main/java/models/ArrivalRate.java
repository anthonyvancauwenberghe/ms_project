package models;

import statistics.PoissonDistribution;

public class ArrivalRate {
    protected double[] data;

    public ArrivalRate(double[] data) {
        this.data = data;
    }

    public double getArrivalRate(int hour, int minute, int second) {
        if (this.data.length < 24 * 60 * 60)
            throw new RuntimeException("The arrival rate resolution is not defined in seconds");

        return this.data[hour * 3600 + minute * 60 + second];
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
