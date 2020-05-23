package factories;

import models.ArrivalRate;
import statistics.PoissonDistribution;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class InterArrivalTimesFactory {
    protected final ArrivalRate rate;

    public InterArrivalTimesFactory(ArrivalRate arrivalRate) {
        this.rate = arrivalRate;
    }

    public double[] build() {
        List<Double> times = new LinkedList<>();

        double counter = 0;

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        for (double rate : this.rate.getRates()) {
            PoissonDistribution distribution = new PoissonDistribution(rate);
            int arrivals = distribution.sample();

            for (int i = 0; i < arrivals; i++) {
                Random rng = new Random();
                times.add(counter + rng.nextDouble());
            }

            counter++;
        }

        Collections.sort(times);

        double[] array = new double[times.size()];

        for (int i = 0; i < times.size(); i++) {
            if(i==0)
                array[i]= times.get(i);
            else
            array[i] = times.get(i)-times.get(i-1);
        }

        return array;
    }



}
