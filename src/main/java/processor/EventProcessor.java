package processor;

import abstracts.AbstractEvent;
import abstracts.AbstractEventProcessor;
import abstracts.AbstractProductEvent;
import contracts.IQueue;
import contracts.IStrategy;
import events.*;
import listeners.*;
import models.CallAgent;
import models.Product;
import models.Queue;
import models.Sink;
import strategies.NoStrategy;

public class EventProcessor extends AbstractEventProcessor {

    protected IStrategy strategy;

    protected IQueue consumerQueue = new Queue();

    protected IQueue corporateQueue = new Queue();

    protected Sink consumerSink = new Sink("CONSUMER_SINK");

    protected Sink corporateSink = new Sink("CORPORATE_SINK");

    public EventProcessor() {
        this.strategy = new NoStrategy();
        this.strategy.setQueues(consumerQueue, corporateQueue);
    }

    public EventProcessor(IStrategy strategy) {
        this.strategy = strategy;
        this.strategy.setQueues(consumerQueue, corporateQueue);
    }

    /**
     * All the events are caught and processed here.
     */
    @Override
    protected void process(AbstractEvent event) {
        super.process(event);

        if (event instanceof AbstractProductEvent) {
            this.processProductEvents((AbstractProductEvent) event);
        } else if (event instanceof MachineStartedEvent) {
            IQueue queue = this.selectQueue((CallAgent) ((MachineStartedEvent) event).getMachine());
            new AssignMachineToQueueListener(queue, this.getEvents()).handle((MachineStartedEvent) event);
        } else if (event instanceof MachineStoppedEvent) {
            new StopMachineListener().handle((MachineStoppedEvent) event);
        }
    }

    protected void processProductEvents(AbstractProductEvent event) {
        //select the right queue according to the product type
        IQueue queue = this.selectQueue(event.getProduct());
        Sink sink = this.selectSink(event.getProduct());

        if (event instanceof ProductCreatedEvent)
            //SELECT QUEUE FOR THIS LISTENER BASED ON A STRATEGY
            new ProductCreatedListener(this.strategy.execute(event.getExecutionTime(),event.getProduct()), this.getEvents()).handle((ProductCreatedEvent) event);
        else if (event instanceof ProductionStartedEvent)
            new StartProductionListener(queue, this.getEvents()).handle((ProductionStartedEvent) event);
        else if (event instanceof ProductionFinishedEvent)
            new FinishProductionListener(queue, this.getEvents(), sink).handle((ProductionFinishedEvent) event);
    }

    /**
     * Select the right queue according to the product type
     */
    protected IQueue selectQueue(Product product) {
        return product.type().isConsumer() ? this.consumerQueue : this.corporateQueue;
    }

    /**
     * Select the right queue according to the agent type
     */
    protected IQueue selectQueue(CallAgent machine) {
        return machine.getType().isConsumer() ? this.consumerQueue : this.corporateQueue;
    }

    /**
     * Select the right sink according to the product type
     */
    protected Sink selectSink(Product product) {
        return product.type().isConsumer() ? this.consumerSink : this.corporateSink;
    }


    @Override
    public IQueue[] getQueues() {
        return new IQueue[]{this.consumerQueue, this.corporateQueue};
    }

    @Override
    public Sink[] getSinks() {
        return new Sink[]{this.consumerSink, this.corporateSink};
    }
}
