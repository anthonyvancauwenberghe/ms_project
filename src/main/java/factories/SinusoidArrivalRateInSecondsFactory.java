package factories;

import contracts.IArrivalRateFactory;
import models.ArrivalRate;

public class SinusoidArrivalRateInSecondsFactory implements IArrivalRateFactory {
    protected final double lambda;

    protected final double period;

    protected final double minRateValue;

    protected final double minRateTime;

    public SinusoidArrivalRateInSecondsFactory(double avgArrivalRatePerMinute, double periodHours, double minimumArrivalRatePerMinute, double hourOfMinimumArrivalTime) {
        this.lambda = avgArrivalRatePerMinute;
        this.period = periodHours;
        this.minRateValue = minimumArrivalRatePerMinute;
        this.minRateTime = hourOfMinimumArrivalTime;
    }

    public ArrivalRate build() {
        double[] data = new double[24 * 60 * 60];
        double lambda = this.lambda / 60;
        double period = this.period * 60 * 60;
        double minRateTime = this.minRateTime * 60 * 60;
        double minRateValue = this.minRateValue / 60;

            for (int h = 0; h < 24; h++) {
                for (int m = 0; m < 60; m++) {
                    for (int s = 0; s < 60; s++) {
                        int time = h * 60*60 + m * 60 + s;
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
