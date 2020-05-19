package Statistics;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.RandomGenerator;

//TODO implement normaldistribution ourselves
public class NormalDistribution extends org.apache.commons.math3.distribution.NormalDistribution {

    public NormalDistribution() {
    }

    public NormalDistribution(double mean, double sd) throws NotStrictlyPositiveException {
        super(mean, sd);
    }

    public NormalDistribution(double mean, double sd, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(mean, sd, inverseCumAccuracy);
    }

    public NormalDistribution(RandomGenerator rng, double mean, double sd) throws NotStrictlyPositiveException {
        super(rng, mean, sd);
    }

    public NormalDistribution(RandomGenerator rng, double mean, double sd, double inverseCumAccuracy) throws NotStrictlyPositiveException {
        super(rng, mean, sd, inverseCumAccuracy);
    }
}
