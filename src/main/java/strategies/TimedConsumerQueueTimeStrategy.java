package strategies;

import abstracts.AbstractStrategy;
import contracts.IQueue;
import models.Product;

public class TimedConsumerQueueTimeStrategy extends AbstractStrategy {

    protected int minAvailableCorporateAgents;

    protected double maxConsumerQueueTime;

    public TimedConsumerQueueTimeStrategy() {
        this.minAvailableCorporateAgents = 2;
        this.maxConsumerQueueTime = 5*60;
    }

    public TimedConsumerQueueTimeStrategy(int minAvailableCorporateAgents, double maxConsumerQueueTime) {
        this.minAvailableCorporateAgents = minAvailableCorporateAgents;
        this.maxConsumerQueueTime = maxConsumerQueueTime;
    }

    @Override
    public IQueue execute(double tme, Product product) {

        if (product.type().isCorporate())
            return corporateQueue;

        if (!this.consumerAgentsBusy())
            return consumerQueue;

        if (tme > 11 * 3600 && tme < 17 * 3600) {
            return this.consumerQueue;
        }

        if ((tme > 21 * 3600 && tme < 24 * 3600) || (tme > 0 && tme < 6 * 3600)) {
            if ((this.getMaxConsumerQueueTime(tme) > this.maxConsumerQueueTime) && (this.availableCorporateAgents() >= this.minAvailableCorporateAgents))
                return this.corporateQueue;
        }

        return consumerQueue;
    }
}
