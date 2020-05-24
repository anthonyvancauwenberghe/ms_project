package simulation2.abstracts;

import simulation2.models.Product;

public class AbstractProductEvent extends AbstractEvent {

    protected Product product;

    protected String station;

    public AbstractProductEvent(double time, String station, Product product) {
        super(time);

        this.station = station;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public String getStation() {
        return station;
    }
}
