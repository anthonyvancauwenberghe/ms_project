package models;

import Statistics.NormalDistribution;

public class ServiceTime {

    protected NormalDistribution distribution;

    public ServiceTime(NormalDistribution distribution) {
        this.distribution = distribution;
    }

    public double[] getProbabilities(int size) {
        double[] data = new double[size];

        for (int i = 0; i < data.length; i++) {
            data[i] = this.distribution.density(i);
        }
        return data;
    }
}
