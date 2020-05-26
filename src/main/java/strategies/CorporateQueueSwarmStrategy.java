package strategies;

import abstracts.AbstractStrategy;
import configs.ArrivalRatesConfig;
import configs.SimulationConfig;
import contracts.IQueue;
import models.Product;
import statistics.PoissonDistribution;

public class CorporateQueueSwarmStrategy extends AbstractStrategy {

    @Override
    public IQueue execute(double time, Product product) {
        if (product.type().isCorporate())
            return this.corporateQueue;

        if ((this.productInConsumerQueue() >= 5) && (this.availableCorporateAgents() >= 2)){
            int incomingArrivals = this.getArrivalsWithinConsumerServiceTime(time);
            if (incomingArrivals <= this.availableCorporateAgents())
                return this.corporateQueue;
        }

        return this.consumerQueue;
    }

    public int getArrivalsWithinConsumerServiceTime(double time) {
        double serviceTime = this.sampleConsumerServiceTime();
        double arrivalRate = serviceTime * (this.getCorporateArrivalRateFromTime(time) / 60);

        return this.sampleCorporateArrivalRate(arrivalRate);
    }

    public int sampleCorporateArrivalRate(double rate) {
        PoissonDistribution distribution = new PoissonDistribution(rate);
        return distribution.sample();
    }

    public double sampleConsumerServiceTime() {
        return SimulationConfig.CONSUMER_SERVICE_DISTRIBUTION.sample();
    }

    protected double getCorporateArrivalRateFromTime(double time) {
        double value = time % 3600;

        int result = (int) (time - value) / 3600;

        return ArrivalRatesConfig.CORPORATE_AVG_ARRIVAL_RATE_RANGE[result];
    }


}
