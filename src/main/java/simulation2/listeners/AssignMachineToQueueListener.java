package simulation2.listeners;

import simulation2.contracts.IListener;
import simulation2.contracts.IQueue;
import simulation2.events.MachineCreatedEvent;
import simulation2.events.ProductionStartedEvent;
import simulation2.models.CEventList;
import simulation2.models.Product;

public class AssignMachineToQueueListener implements IListener<MachineCreatedEvent> {

    protected IQueue queue;

    protected final CEventList events;

    public AssignMachineToQueueListener(IQueue queue, CEventList events) {
        this.queue = queue;
        this.events = events;
    }

    @Override
    public void handle(MachineCreatedEvent event) {
        Product product = this.queue.assign(event.getMachine());

        if (product != null)
            this.events.add(new ProductionStartedEvent(event.getExecutionTime(), product, event.getMachine()));
    }
}
