package models;

import contracts.IArrivalRateFactory;
import factories.InterArrivalTimesFactory;

public class NewArrivalRate {

    protected IArrivalRateFactory factory;

    protected double[] arrivalRates;

    protected double[] interArrivalTimes;

    public NewArrivalRate(IArrivalRateFactory factory) {
        this.factory = factory;
        this.arrivalRates = factory.sampleInterArrivalRates();
        this.interArrivalTimes = new InterArrivalTimesFactory(this.arrivalRates).build();
    }

    public double getArrivalRate(double time) {
        return this.factory.getRate(time);
    }

    public double getArrivalRate(int h, int m, int s) {
        return this.getArrivalRate(3600 * h + 60 * m + s);
    }

    public double[] sampleInterArrivalTimes() {
        return this.interArrivalTimes;
    }

    public double[] sampleArrivalTimes() {
        return this.arrivalRates;
    }
}
