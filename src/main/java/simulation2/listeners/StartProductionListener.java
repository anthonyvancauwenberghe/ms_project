package simulation2.listeners;

import simulation2.contracts.IListener;
import simulation2.contracts.IQueue;
import simulation2.events.ProductionFinishedEvent;
import simulation2.events.ProductionStartedEvent;
import simulation2.models.CEventList;

public class StartProductionListener implements IListener<ProductionStartedEvent> {

    protected final IQueue queue;

    protected final CEventList events;

    public StartProductionListener(IQueue queue, CEventList events) {
        this.queue = queue;
        this.events = events;
    }

    @Override
    public void handle(ProductionStartedEvent event) {
        event.getProduct().stamp(event.getExecutionTime(), "IN_PRODUCTION", event.getStation());
        this.events.add(new ProductionFinishedEvent(event.getExecutionTime() + event.getProduct().getProductionTime(), event.getProduct(), event.getMachine()));
    }
}
