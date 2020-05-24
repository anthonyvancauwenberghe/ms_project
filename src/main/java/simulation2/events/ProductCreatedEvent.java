package simulation2.events;

import simulation2.abstracts.AbstractProductEvent;
import simulation2.models.Product;

public class ProductCreatedEvent extends AbstractProductEvent {

    public ProductCreatedEvent(double time, String station, Product product) {
        super(time,station, product);
    }
}
