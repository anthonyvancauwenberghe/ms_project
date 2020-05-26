package statistics;

import contracts.ITInterval;
import org.apache.commons.math3.distribution.TDistribution;

public class TInterval implements ITInterval {

    protected double[] data;

    protected double confidence;


    public TInterval(double[] data, double confidence) {
        this.data = data;
        this.confidence = confidence;

        if (this.data.length <= 1)
            throw new RuntimeException("Cannot analyze a sample set with less than 2 numbers");
    }

    @Override
    public int degreesOfFreedom() {
        return this.sampleSize() - 1;
    }

    @Override
    public double sampleStd() {
        double base = (1 / (this.sampleSize() - 1.0));
        double mean = this.sampleMean();
        double sum = 0;
        double result,value;

        for(int i = 0; i<this.data.length; i ++){
            value = data[i];
            result = Math.pow (value-mean,2);
            sum = sum + result;
        }

        return Math.sqrt(base*sum);
    }

    @Override
    public double sampleMean() {
        double count = 0.0;
        double total = 0.0;
        for (double value : this.data) {
            total += value;
            count++;
        }

        return total / count;
    }

    @Override
    public double criticalValue() {
        TDistribution distr = new TDistribution(this.sampleSize() - 1);
        return -distr.inverseCumulativeProbability((1 - this.confidenceLevel()) / 2);
    }

    @Override
    public double upperBound() {
        return this.sampleMean() + (this.criticalValue() * (this.sampleStd() / Math.sqrt(this.sampleSize())));
    }

    @Override
    public double lowerBound() {
        return this.sampleMean() - (this.criticalValue() * (this.sampleStd() / Math.sqrt(this.sampleSize())));
    }

    @Override
    public int sampleSize() {
        return this.data.length;
    }

    @Override
    public double confidenceLevel() {
        return this.confidence;
    }

}
