package listeners;

import contracts.IListener;
import contracts.IQueue;
import events.ProductCreatedEvent;
import events.ProductionStartedEvent;
import models.Machine;
import models.CEventList;

public class ProductCreatedListener implements IListener<ProductCreatedEvent> {

    protected final IQueue queue;

    protected final CEventList events;

    public ProductCreatedListener(IQueue queue, CEventList events) {
        this.queue = queue;
        this.events = events;
    }

    @Override
    public void handle(ProductCreatedEvent event) {
        event.getProduct().stamp(event.getExecutionTime(), "CREATED", event.getStation());
        event.getProduct().setArrivalTime(event.getExecutionTime());
        Machine machine = this.queue.add(event.getProduct());

        if (machine != null)
            this.events.add(new ProductionStartedEvent(event.getExecutionTime(), event.getProduct(), machine));
    }
}
