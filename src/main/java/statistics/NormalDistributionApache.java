package statistics;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.random.RandomGenerator;

//TODO implement our own poissondistribution
public class NormalDistributionApache extends org.apache.commons.math3.distribution.NormalDistribution {

    public void test(){
        this.density(50);
    }

}
