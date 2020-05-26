package factories;

import contracts.IArrivalRateFactory;
import models.NewArrivalRate;

import java.util.ArrayList;

public class CorporateArrivalTimeFactory implements IArrivalRateFactory {

    protected double[] ranges;

    protected int period;

    public CorporateArrivalTimeFactory(double[] hourRanges, int period) {
        this.ranges = hourRanges;
        this.period = period-1;
    }

    @Override
    public double[] sampleInterArrivalRates() {
        ArrayList<Double> t = new ArrayList<>();
        ArrayList<Double> s = new ArrayList<>();

        t.add(0.0);
        s.add(0.0);


        double LM = 1.0/60;
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
        int h = this.timeToHour(time);
        String t;
        if(h>23)
            h -=24;
        return this.ranges[h]/60.0;
    }

    protected int timeToHour(double time) {
        double value = time % 3600;

        return (int) ((time - value) / 3600);
    }

    @Override
    public NewArrivalRate build() {
        return new NewArrivalRate(this);
    }
}
