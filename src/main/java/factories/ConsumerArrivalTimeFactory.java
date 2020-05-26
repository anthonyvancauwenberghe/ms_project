package factories;

import contracts.IArrivalRateFactory;
import models.ArrivalRate;
import models.NewArrivalRate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConsumerArrivalTimeFactory implements IArrivalRateFactory {
    protected final double lambda;

    protected final double period;

    protected final double minRateValue;

    protected final double minRateTime;

    public ConsumerArrivalTimeFactory(double avgArrivalRatePerMinute, double periodHours, double minimumArrivalRatePerMinute, double hourOfMinimumArrivalTime) {
        this.lambda = avgArrivalRatePerMinute / 60;
        this.period = periodHours * 60 * 60;
        this.minRateValue = minimumArrivalRatePerMinute / 60;
        this.minRateTime = hourOfMinimumArrivalTime * 60 * 60;
    }

    @Override
    public NewArrivalRate build() {
        return new NewArrivalRate(this);
    }

    @Override
    public double[] sampleInterArrivalRates() {
        ArrayList<Double> t = new ArrayList<>();
        ArrayList<Double> s = new ArrayList<>();

        t.add(0.0);
        s.add(0.0);


        double LM = this.getUpperBound();
        double D;
        double u, w;
        int n = 0;
        int m = 0;

        while (s.get(m) < this.period) {
            u = Math.random();
            w = -(Math.log(u)/ LM);
            s.add(m+1,s.get(m) + w);

            D = Math.random();
            if (D < (this.function(s.get(m+1)) / LM)) {
                t.add(n+1,s.get(m+1));
                n += 1;
            }
            m += 1;
        }

        if(t.get(n) >= this.period)
            t.remove(n);

        t.remove(0);

        return t.stream().mapToDouble(d -> d).toArray();
    }

    @Override
    public double getRate(double time) {
        return this.function(time);
    }

    protected double function(double time) {
        return ((lambda-minRateValue) * Math.sin(
                (2 * Math.PI / (period)) * (time - ((period / 4) + minRateTime))
        ) + lambda);
    }

    public double getUpperBound() {
        return this.lambda * (1) + this.lambda + this.minRateValue;
    }

}
