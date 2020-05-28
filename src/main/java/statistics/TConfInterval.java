package statistics;

import contracts.ITInterval;
import utils.FileUtil;

public class TConfInterval implements ITInterval {

    protected double[] data;

    protected double confidence;

    protected final static double[][] tTable = (new FileUtil<double[][]>("src/main/resources/tinterval.txt")).readObjectFromFile();


    public TConfInterval(double[] data, double confidence) {
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
        double result, value;

        for (int i = 0; i < this.data.length; i++) {
            value = data[i];
            result = Math.pow(value - mean, 2);
            sum = sum + result;
        }

        return Math.sqrt(base * sum);
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
        // LOOKING UP CRITICAL VALUES FROM T TABLE
        // confidenceLevels = {0.99, 0.95, 0.9, 0.8, 0.5};
        int sampleSize = this.sampleSize();

        if(this.sampleSize()>10000)
            sampleSize=10000;

        int key;
        if (this.confidenceLevel() == 0.99)
            key = 0;
        else if (this.confidenceLevel() == 0.95)
            key = 1;
        else if (this.confidenceLevel() == 0.9)
            key = 2;
        else if (this.confidenceLevel() == 0.8)
            key = 3;
        else if (this.confidenceLevel() == 0.5)
            key = 4;
        else
            throw new RuntimeException("confidence level value not supported. Only 0.99,0.95,0.9,0.8,0.5");

        return tTable[key][sampleSize-1];

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
