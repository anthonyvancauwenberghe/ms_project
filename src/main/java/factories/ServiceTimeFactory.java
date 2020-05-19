package factories;

import statistics.LeftTruncatedNormalDistribution;
import statistics.NormalDistribution;
import interfaces.IServiceTimeFactory;

public class ServiceTimeFactory implements IServiceTimeFactory {

    protected final double mean;

    protected final double std;

    protected final int leftTruncValue;

    public ServiceTimeFactory(double mean, double std, int leftTruncValue) {
        this.mean = mean;
        this.std = std;
        this.leftTruncValue = leftTruncValue;
    }

    public double build(){
        NormalDistribution distribution = new LeftTruncatedNormalDistribution(this.mean, this.std, this.leftTruncValue);
        return distribution.sample();
    }

    public double[] build(int size){
        NormalDistribution distribution = new LeftTruncatedNormalDistribution(this.mean, this.std, this.leftTruncValue);
        return distribution.sample(size);
    }
}
