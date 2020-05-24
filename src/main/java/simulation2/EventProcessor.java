package simulation2;

import simulation2.abstracts.AbstractEvent;
import simulation2.abstracts.AbstractEventProcessor;
import simulation2.abstracts.AbstractProductEvent;
import simulation2.contracts.IQueue;
import simulation2.events.*;
import simulation2.listeners.*;
import simulation2.models.*;

public class EventProcessor extends AbstractEventProcessor {

    protected IQueue consumerQueue = new Queue();

    protected IQueue corporateQueue = new Queue();

    protected Sink consumerSink = new Sink("CONSUMER_SINK");

    protected Sink corporateSink = new Sink("CORPORATE_SINK");

    /**
     * All the events are caught and processed here.
     */
    @Override
    protected void process(AbstractEvent event) {
        super.process(event);

        if (event instanceof AbstractProductEvent) {
            this.processProductEvents((AbstractProductEvent) event);
        } else if (event instanceof MachineCreatedEvent) {
            IQueue queue = this.getQueue((CallAgent) ((MachineCreatedEvent) event).getMachine());
            new AssignMachineToQueueListener(queue, this.getEvents()).handle((MachineCreatedEvent) event);
        } else if (event instanceof MachineStoppedEvent) {
            new StopMachineListener().handle((MachineStoppedEvent) event);
        }
    }

    protected void processProductEvents(AbstractProductEvent event) {
        //select the right queue according to the product type
        IQueue queue = this.getQueue(event.getProduct());
        Sink sink = this.getSink(event.getProduct());

        if (event instanceof ProductCreatedEvent)
            new AddProductToQueueListener(queue, this.getEvents()).handle((ProductCreatedEvent) event);
        else if (event instanceof ProductionStartedEvent)
            new StartProductionListener(queue, this.getEvents()).handle((ProductionStartedEvent) event);
        else if (event instanceof ProductionFinishedEvent)
            new FinishProductionListener(queue, this.getEvents(), sink).handle((ProductionFinishedEvent) event);
    }

    /**
     * Select the right queue according to the product type
     */
    protected IQueue getQueue(Product product) {
        return product.type().isConsumer() ? this.consumerQueue : this.corporateQueue;
    }

    /**
     * Select the right queue acoording to the product type
     */
    protected IQueue getQueue(CallAgent machine) {
        return machine.getType().isConsumer() ? this.consumerQueue : this.corporateQueue;
    }

    /**
     * Select the right sink according to the product type
     */
    protected Sink getSink(Product product) {
        return product.type().isConsumer() ? this.consumerSink : this.corporateSink;
    }


}
