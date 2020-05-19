package statistics;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.RandomGenerator;

//TODO implement our own poissondistribution
public class PoissonDistribution extends org.apache.commons.math3.distribution.PoissonDistribution {

    public PoissonDistribution(double p) throws NotStrictlyPositiveException {
        super(p);
    }

    public PoissonDistribution(double p, double epsilon, int maxIterations) throws NotStrictlyPositiveException {
        super(p, epsilon, maxIterations);
    }

    public PoissonDistribution(RandomGenerator rng, double p, double epsilon, int maxIterations) throws NotStrictlyPositiveException {
        super(rng, p, epsilon, maxIterations);
    }

    public PoissonDistribution(double p, double epsilon) throws NotStrictlyPositiveException {
        super(p, epsilon);
    }

    public PoissonDistribution(double p, int maxIterations) {
        super(p, maxIterations);
    }
}
