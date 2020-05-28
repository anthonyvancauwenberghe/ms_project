package listeners;

import contracts.IListener;
import contracts.IQueue;
import events.ProductionFinishedEvent;
import events.ProductionStartedEvent;
import models.CEventList;
import models.Product;
import models.Sink;

import java.util.Random;

public class FinishProductionListener implements IListener<ProductionFinishedEvent> {

    protected final IQueue queue;

    protected final CEventList events;

    protected final Sink sink;

    public FinishProductionListener(IQueue queue, CEventList events, Sink sink) {
        this.queue = queue;
        this.events = events;
        this.sink = sink;
    }

    @Override
    public void handle(ProductionFinishedEvent event) {

        event.getProduct().stamp(event.getExecutionTime(), "FINISHED_PRODUCTION", event.getStation());

            this.sink.giveProduct(event.getProduct());


        //ONCE A MACHINE IS FINISHED SET IT TO IDLE AND ASSIGN A NEW PRODUCT TO IT

        event.getMachine().setIdle();

        Product product = this.queue.ask(event.getMachine());

        if (product != null)
            this.events.add(new ProductionStartedEvent(event.getExecutionTime(), product, event.getMachine()));

    }
}
