package strategies;

import abstracts.AbstractStrategy;
import contracts.IQueue;
import models.Product;

public class NoStrategy extends AbstractStrategy {

    @Override
    public IQueue execute(double tme, Product product) {
        return product.type().isConsumer() ? this.consumerQueue : this.corporateQueue;
    }
}
