package Statistics;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.RandomGenerator;

public class LeftTruncatedNormalDistribution extends NormalDistribution {

    protected int leftTruncatedValue;

    public LeftTruncatedNormalDistribution(int leftTruncatedValue) {
        this.leftTruncatedValue = leftTruncatedValue;
    }

    public LeftTruncatedNormalDistribution(double mean, double sd, int leftTruncatedValue) throws NotStrictlyPositiveException {
        super(mean, sd);
        this.leftTruncatedValue = leftTruncatedValue;
    }

    public LeftTruncatedNormalDistribution(double mean, double sd, double inverseCumAccuracy, int leftTruncatedValue) throws NotStrictlyPositiveException {
        super(mean, sd, inverseCumAccuracy);
        this.leftTruncatedValue = leftTruncatedValue;
    }

    public LeftTruncatedNormalDistribution(RandomGenerator rng, double mean, double sd, int leftTruncatedValue) throws NotStrictlyPositiveException {
        super(rng, mean, sd);
        this.leftTruncatedValue = leftTruncatedValue;
    }

    public LeftTruncatedNormalDistribution(RandomGenerator rng, double mean, double sd, double inverseCumAccuracy, int leftTruncatedValue) throws NotStrictlyPositiveException {
        super(rng, mean, sd, inverseCumAccuracy);
        this.leftTruncatedValue = leftTruncatedValue;
    }

    @Override
    public double density(double x) {
        return x < this.leftTruncatedValue ? 0 : super.density(x);
    }
}
