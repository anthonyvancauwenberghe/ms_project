package statistics;

import interfaces.Distribution;

import java.util.Random;

//TODO implement normaldistribution ourselves
public class NormalDistribution implements Distribution {

    protected double mean;
    protected double std;

    protected Random rng = new Random();

    public NormalDistribution() {
        this.mean = 0;
        this.std = 1;
    }

    public NormalDistribution(double mean, double std) {
        this.mean = mean;
        this.std = std;
    }

    public double sample() {
        return this.mean + this.rng.nextGaussian() * this.std;
    }

    public double[] sample(int size) {
        double[] samples = new double[size];
        for (int i = 0; i < size; i++) {
            samples[i] = this.sample();
        }
        return samples;
    }

}