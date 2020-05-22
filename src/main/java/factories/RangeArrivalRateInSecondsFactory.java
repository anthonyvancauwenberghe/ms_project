package factories;

import contracts.IArrivalRateFactory;
import models.ArrivalRate;

public class RangeArrivalRateInSecondsFactory implements IArrivalRateFactory {
    protected final double[] ranges;

    public RangeArrivalRateInSecondsFactory(double[] hourRanges) {
        this.ranges = hourRanges;
    }

    public ArrivalRate build() {
        double[] data = new double[24 * 60 * 60];
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                for (int s = 0; s < 60; s++) {
                    data[h * 60 * 60 + m * 60 + s] = this.ranges[h]/60;
                }
            }
        }
        return new ArrivalRate(data);
    }

}
