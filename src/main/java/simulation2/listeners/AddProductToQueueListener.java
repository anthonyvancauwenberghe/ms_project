package simulation2.listeners;

import simulation2.contracts.IListener;
import simulation2.contracts.IQueue;
import simulation2.events.ProductCreatedEvent;
import simulation2.events.ProductionStartedEvent;
import simulation2.models.Machine;
import simulation2.models.CEventList;

public class AddProductToQueueListener implements IListener<ProductCreatedEvent> {

    protected final IQueue queue;

    protected final CEventList events;

    public AddProductToQueueListener(IQueue queue, CEventList events) {
        this.queue = queue;
        this.events = events;
    }

    @Override
    public void handle(ProductCreatedEvent event) {
        event.getProduct().stamp(event.getExecutionTime(), "CREATED", event.getStation());

        Machine machine = this.queue.add(event.getProduct());

        if (machine != null)
            this.events.add(new ProductionStartedEvent(event.getExecutionTime(), event.getProduct(), machine));
    }
}
