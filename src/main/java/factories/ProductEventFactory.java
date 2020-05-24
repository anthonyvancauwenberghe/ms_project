package factories;

import configs.SimulationConfig;
import abstracts.AbstractEvent;
import abstracts.AbstractEventFactory;
import enums.ProductType;
import events.ProductCreatedEvent;
import models.Product;

public class ProductEventFactory extends AbstractEventFactory {

    /**
     * Interarrival times
     */
    protected final double[] iaTimes;

    /**
     * Distribution from which the the duration to process the product is sampled
     */
    protected ProductType productType;


    public ProductEventFactory(String name, double[] interarrivalTimes, ProductType productType) {
        super(name);
        this.iaTimes = interarrivalTimes;
        this.productType = productType;
    }

    @Override
    public AbstractEvent[] build() {

        double time = 0;
        int counter = 0;
        AbstractEvent[] events = new ProductCreatedEvent[iaTimes.length];

        for (double iaTime : this.iaTimes) {
            time += iaTime;

            // add a new product created event with a certain time to the eventlist
            Product p = new Product(this.productType);

            events[counter] = new ProductCreatedEvent(time, this.name, p);

/*            if (SimulationConfig.DEBUG)
                System.out.println(this.productType.toString() + " product event generated at: " + time);*/

            counter++;
        }

        return events;
    }

}
