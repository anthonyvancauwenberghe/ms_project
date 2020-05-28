package contracts;


import models.ArrivalRate;

public interface IArrivalRateFactory {
    public double[] sampleArrivalRates();

    public double getRate(double time);

    public ArrivalRate build();

}
