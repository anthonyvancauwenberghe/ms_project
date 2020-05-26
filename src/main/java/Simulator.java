import analysis.SinkAnalysis;
import configs.SimulationConfig;
import abstracts.AbstractEvent;
import abstracts.AbstractEventFactory;
import contracts.IEventProcessor;
import contracts.IQueue;
import contracts.ISimulationConfig;
import events.ProductCreatedEvent;
import models.Machine;
import models.Product;
import processor.EventProcessor;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private final List<AbstractEventFactory> sources = new ArrayList<>();

    protected final IEventProcessor processor;

    protected ISimulationConfig config;

    protected boolean executed = false;

    public Simulator(ISimulationConfig config) {
        this.config = config;
        this.processor = config.getStrategy() == null ? new EventProcessor() : new EventProcessor(config.getStrategy());
    }

    public Simulator(ISimulationConfig config, IEventProcessor processor) {
        this.config = config;
        this.processor = processor;
    }

    public void run() {

        if (this.executed)
            throw new RuntimeException("cannot run the same simulation more than once. Create a new instance to run multiple iterations.");

        this.initConfig();
        this.bootSources();
        this.processor.start();

        this.executed = true;
    }

    public SinkAnalysis consumerAnalysis() {
        return new SinkAnalysis(this.processor.getSinks()[0], this.processor.getQueues()[0], "Consumer");
    }

    public SinkAnalysis corporateAnalysis() {
        return new SinkAnalysis(this.processor.getSinks()[1], this.processor.getQueues()[1], "Corporate");
    }

    /**
     * initializes the config file
     */
    protected void initConfig() {
        for (AbstractEventFactory source : this.config.getSources()) {
            this.source(source);
        }

        if (this.config.getQueues().length > 0)
            for (IQueue queue : this.config.getQueues()) {
                this.queue(queue);
            }
    }

    /**
     * adds sources to the simulation
     */
    public void source(AbstractEventFactory source) {
        this.sources.add(source);
    }

    /**
     * transfers over products from an existing queue
     */
    public void queue(IQueue queue) {
        // Transfer over the products are waiting in the queue
        for (Product product : queue.getQueue()) {
            AbstractEvent event = new ProductCreatedEvent(product.getTimes().get(0) - SimulationConfig.SIMULATION_RUNTIME, "OLD_QUEUE", new Product(product.type()));
            this.processor.addEvent(event);
        }

        // Here we keep the workers that were busy in the previous queue busy until they have finished the production of their product
        // In other words we transfer over the products that are in production.
        for (Machine machine : queue.getMachines()) {
            if (machine.isBusy()) {
                Product product = machine.getProduct();
                Product newProduct = new Product(product.type());
                newProduct.setProductionTime(product.getTimes().get(1) + product.getProductionTime() - SimulationConfig.SIMULATION_RUNTIME);
                AbstractEvent event = new ProductCreatedEvent(product.getTimes().get(0) - SimulationConfig.SIMULATION_RUNTIME, "OLD_QUEUE", newProduct);
                this.processor.addEvent(event);
            }
        }
    }

    protected void bootSources() {
        //Start generating all the events from the sources
        for (AbstractEventFactory source : this.sources) {
            for (AbstractEvent event : source.build()) {
                this.processor.addEvent(event);
            }
        }
    }

    public IEventProcessor getProcessor() {
        return processor;
    }

    public boolean isExecuted() {
        return executed;
    }
}
