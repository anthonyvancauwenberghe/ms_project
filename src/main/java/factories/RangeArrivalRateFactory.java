package factories;

import interfaces.IArrivalRateFactory;
import models.ArrivalRate;

public class RangeArrivalRateFactory implements IArrivalRateFactory {
    protected final double[] ranges;

    public RangeArrivalRateFactory(double[] hourRanges) {
        this.ranges = hourRanges;
    }

    public ArrivalRate build() {
        double[] data = new double[24 * 60];
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                    data[h * 60 + m] = this.ranges[h];
            }
        }
        return new ArrivalRate(data);
    }

}
