package events;

import abstracts.AbstractProductEvent;
import models.Product;

public class ProductCreatedEvent extends AbstractProductEvent {

    public ProductCreatedEvent(double time, String station, Product product) {
        super(time,station, product);
    }
}
