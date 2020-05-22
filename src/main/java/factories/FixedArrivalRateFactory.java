package factories;

import contracts.IArrivalRateFactory;
import models.ArrivalRate;

public class FixedArrivalRateFactory implements IArrivalRateFactory {
    protected final double lambda;

    public FixedArrivalRateFactory(double lambda) {
        this.lambda = lambda;
    }

    public ArrivalRate build() {
        double[] data = new double[24 * 60];
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                    data[h * 60 + m ] = this.lambda;
            }
        }
        return new ArrivalRate(data);
    }

}
