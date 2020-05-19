package statistics;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;

public class LeftTruncatedNormalDistribution extends NormalDistribution {

    protected int leftTruncatedValue;

    public LeftTruncatedNormalDistribution(int leftTruncatedValue) {
        super();
        this.leftTruncatedValue = leftTruncatedValue;
    }

    public LeftTruncatedNormalDistribution(double mean, double sd, int leftTruncatedValue) throws NotStrictlyPositiveException {
        super(mean, sd);
        this.leftTruncatedValue = leftTruncatedValue;
    }

    @Override
    public double sample() {
        double sample = super.sample();
        return sample < this.leftTruncatedValue ? this.sample() : sample;
    }

    @Override
    public double density(double x) {
        return x < this.leftTruncatedValue ? 0 : super.density(x);
    }
}
