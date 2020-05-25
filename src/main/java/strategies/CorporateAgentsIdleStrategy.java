package strategies;

import abstracts.AbstractStrategy;
import contracts.IQueue;
import models.Product;

public class CorporateAgentsIdleStrategy extends AbstractStrategy {

    protected int minAvailableCorporateAgents;

    protected int minConsumerQueueCount;

    public CorporateAgentsIdleStrategy() {
        this.minAvailableCorporateAgents = 6;
        this.minConsumerQueueCount = 5;
    }

    public CorporateAgentsIdleStrategy(int minAvailableCorporateAgents, int minConsumerQueueCount) {
        this.minAvailableCorporateAgents = minAvailableCorporateAgents;
        this.minConsumerQueueCount = minConsumerQueueCount;
    }

    @Override
    public IQueue execute(double tme, Product product) {

        if (product.type().isCorporate())
            return corporateQueue;

        if (!this.consumerAgentsBusy())
            return consumerQueue;

        if (tme > 11 * 3600 && tme < 17 * 3600) {
            if ((this.productInConsumerQueue() > this.minConsumerQueueCount) && (this.availableCorporateAgents() >= this.minAvailableCorporateAgents))
                return this.corporateQueue;
        }

        if (tme > 22 * 3600 && tme < 24 * 3600) {
            if ((this.productInConsumerQueue() > 2) && (this.availableCorporateAgents() >= 1))
                return this.corporateQueue;
        }

        return consumerQueue;
    }
}
