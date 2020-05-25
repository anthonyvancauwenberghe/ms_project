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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private final List<AbstractEventFactory> sources = new ArrayList<>();

    protected final IEventProcessor processor;

    protected ISimulationConfig config;

    protected boolean executed = false;

    public Simulator(ISimulationConfig config) {
        this.config = config;
        this.processor = new EventProcessor();
    }

    public Simulator(ISimulationConfig config, IEventProcessor processor) {
        this.config = config;
        this.processor = processor;
    }

    public void run() {
        long startTime = System.nanoTime();

        if (this.executed)
            throw new RuntimeException("cannot run the same simulation more than once. Create a new instance to run multiple iterations.");

        this.initConfig();
        this.bootSources();
        this.processor.start();

        if (SimulationConfig.DEBUG && this.processor.getQueues().length>1 ) {
            double duration = (((double) (System.nanoTime() - startTime)) / 1000000000);
            DecimalFormat f = new DecimalFormat("##.0000");
            this.printQueueInfo(f.format(duration));
        }

        this.executed = true;
    }

    public void printQueueInfo(String duration) {
        System.out.println("------------------------------");
        System.out.println("Ran simulation in " + duration + " seconds");
        System.out.println(this.processor.getQueues()[0].count() + " consumer calls left in queue");
        System.out.println(this.processor.getQueues()[1].count() + " corporate calls left in queue");
        System.out.println(this.processor.getQueues()[0].count() + this.processor.getQueues()[1].count() + " total calls left in queue");
        System.out.println("------------------------------");
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
}
