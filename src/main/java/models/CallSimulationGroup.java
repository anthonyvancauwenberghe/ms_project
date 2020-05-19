package models;

import enums.CallType;

import java.util.ArrayList;
import java.util.List;

public class CallSimulationGroup {
    List<CallGroup> calls = new ArrayList<>();

    public void add(int h, int m, CallGroup callGroup) {
        calls.add(60 * h + m, callGroup);
    }

    public CallGroup get(int h, int m) {
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

}
