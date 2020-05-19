package results;

import java.util.ArrayList;
import java.util.List;

public class IterationSimulationResult {
    List<MinutelyCallResult> calls = new ArrayList<>();

    public void add(int h, int m, MinutelyCallResult callGroupResult) {
        calls.add(60 * h + m, callGroupResult);
    }

    public MinutelyCallResult get(int h, int m) {
        return calls.get(60 * h + m);
    }

    public double[] getAverageServiceTimePerHour() {
        double[] hours = new double[24];

        for (int h = 0; h < 24; h++) {
            double total = 0;
            for (int m = 0; m < 60; m++) {
                total += this.get(h, m).totalServiceTime();
            }
            hours[h] = total;
        }
        return hours;
    }

    public double[] getAverageServiceTimePerMinute() {
        double[] times = new double[24 * 60];

        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                times[h * 60 + m] = this.get(h, m).totalServiceTime();
            }
        }
        return times;
    }

    public double[] getAverageCallsPerHour() {
        double[] hours = new double[24];

        for (int h = 0; h < 24; h++) {
            double total = 0;
            for (int m = 0; m < 60; m++) {
                total += this.get(h, m).size();
            }
            hours[h] = total;
        }
        return hours;
    }

    public double[] getAverageCallsPerMinute() {
        double[] calls = new double[24 * 60];

        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                calls[h * 60 + m] = this.get(h, m).size();
            }
        }
        return calls;
    }

}
