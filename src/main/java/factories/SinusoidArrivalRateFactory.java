package factories;

import interfaces.IArrivalRateFactory;
import models.ArrivalRate;

public class SinusoidArrivalRateFactory implements IArrivalRateFactory {
    protected final double lambda;

    protected final double period;

    protected final double minRateValue;

    protected final double minRateTime;

    public SinusoidArrivalRateFactory(double avgArrivalRatePerMinute, double periodHours, double minimumArrivalRatePerMinute, double hourOfMinimumArrivalTime) {
        this.lambda = avgArrivalRatePerMinute;
        this.period = periodHours;
        this.minRateValue = minimumArrivalRatePerMinute;
        this.minRateTime = hourOfMinimumArrivalTime;
    }

    public ArrivalRate build() {
            double[] data = new double[24 * 60];
            double lambda = this.lambda;
            double period = this.period * 60;
            double minRateTime = this.minRateTime * 60;
            double minRateValue = this.minRateValue;

            for (int h = 0; h < 24; h++) {
                for (int m = 0; m < 60; m++) {
                    for (int s = 0; s < 60; s++) {
                        int time = h * 60 + m;
                        data[time] = (lambda) * Math.sin(
                                (2 * Math.PI / (period)) * (time - ((period / 4) + minRateTime))
                        )
                                + lambda + minRateValue;
                    }
                }
            }
            return new ArrivalRate(data);
    }
}
