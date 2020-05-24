package listeners;

import contracts.IListener;
import contracts.IQueue;
import events.ProductionFinishedEvent;
import events.ProductionStartedEvent;
import models.CEventList;

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
