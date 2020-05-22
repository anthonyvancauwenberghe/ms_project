package factories;

import contracts.Distribution;
import statistics.LeftTruncatedNormalDistribution;
import statistics.NormalDistribution;
import contracts.IServiceTimeFactory;

public class ServiceTimeFactory implements IServiceTimeFactory {

    protected final double mean;

    protected final double std;

    protected final int leftTruncValue;

    protected final NormalDistribution distribution;

    public ServiceTimeFactory(double mean, double std, int leftTruncValue) {
        this.mean = mean;
        this.std = std;
        this.leftTruncValue = leftTruncValue;

        this.distribution = new LeftTruncatedNormalDistribution(this.mean, this.std, this.leftTruncValue);
    }

    public Distribution getDistribution(){
        return this.distribution;
    }

    public double build() {
        return this.distribution.sample();
    }

    public double[] build(int size) {

        return this.distribution.sample(size);
    }

    @Override
    public double[] probabilities(int limit) {
        double[] probabilities = new double[limit];

        for(int i=0; i<limit; i++){
            probabilities[i]=this.distribution.density(i);
        }
        return probabilities;
    }
}
