package strategies;

import abstracts.AbstractStrategy;
import contracts.IQueue;
import models.Product;

public class CorporateTakeoverStrategy extends AbstractStrategy {

    @Override
    public IQueue execute(double tme, Product product) {
        if(product.type().isCorporate())
            return this.corporateQueue;

        if(this.consumerAgentsBusy() && !this.corporateAgentsBusy())
            return corporateQueue;

        return consumerQueue;
    }
}
