package models;

import abstracts.Call;

import java.util.ArrayList;

public class CallGroup {
    ArrayList<Call> calls = new ArrayList<>();

    public void add(Call call) {
        calls.add(call);
    }

    public int size() {
        return calls.size();
    }

    public double totalServiceTime() {
        double total = 0;
        for (int i = 0; i < this.size(); i++) {
            total += calls.get(i).getCallDuration();
        }
        return total;
    }

    public double[] getServiceTimes() {
        double[] serviceTimes = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            serviceTimes[i] = calls.get(i).getCallDuration();
        }

        return serviceTimes;
    }
}
