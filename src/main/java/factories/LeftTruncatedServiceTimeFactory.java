package factories;


import Statistics.LeftTruncatedNormalDistribution;
import Statistics.NormalDistribution;
import models.ServiceTime;

public class LeftTruncatedServiceTimeFactory {

    protected final double mean;

    protected final double std;

    protected final int leftTruncValue;

    public LeftTruncatedServiceTimeFactory(double mean, double std, int leftTruncValue) {
        this.mean = mean;
        this.std = std;
        this.leftTruncValue = leftTruncValue;
    }

    public ServiceTime build(){
        NormalDistribution distribution = new LeftTruncatedNormalDistribution(this.mean, this.std, this.leftTruncValue);
        return new ServiceTime(distribution);
    }
}
