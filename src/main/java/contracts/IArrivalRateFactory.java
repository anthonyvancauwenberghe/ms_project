package contracts;


import models.NewArrivalRate;

public interface IArrivalRateFactory {
    public double[] sampleInterArrivalRates();

    public double getRate(double time);

    public NewArrivalRate build();

}
