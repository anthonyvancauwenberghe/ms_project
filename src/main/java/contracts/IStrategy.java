package contracts;

import models.Product;

public interface IStrategy {

    public IQueue execute(double tme, Product product);

    public void setQueues(IQueue consumerQueue, IQueue corporateQueue);
}
