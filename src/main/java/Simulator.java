import configs.SimulationConfig;
import abstracts.AbstractEvent;
import abstracts.AbstractEventFactory;
import contracts.IEventProcessor;
import contracts.IQueue;
import contracts.ISimulationConfig;
import events.ProductCreatedEvent;
import models.Product;
import processor.EventProcessor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private final List<AbstractEventFactory> sources = new ArrayList<>();

    protected final IEventProcessor processor;

    protected ISimulationConfig config;

    protected boolean ran = false;

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

        if (this.ran)
            throw new RuntimeException("cannot run the same simulation more than once. Create a new instance to run multiple iterations.");

        this.initConfig();
        this.bootSources();
        this.processor.start();

        long endTime = System.nanoTime();
        double duration = (((double) (endTime - startTime)) / 1000000000);

        if(SimulationConfig.DEBUG){
            DecimalFormat f = new DecimalFormat("##.0000");
            this.printQueueInfo(f.format(duration));
        }


        this.ran = true;
    }

    public void printQueueInfo(String duration) {
        System.out.println("------------------------------");
        System.out.println("Ran simulation in " + duration  + " seconds");
        System.out.println(this.processor.getQueues()[0].count() + " consumer calls left in queue");
        System.out.println(this.processor.getQueues()[1].count() + " corporate calls left in queue");
        System.out.println(this.processor.getQueues()[0].count()  + this.processor.getQueues()[1].count()  + " total calls left in queue");
        System.out.println("------------------------------");
    }

    public void initConfig() {
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
        for (Product product : queue.getQueue()) {
            AbstractEvent event = new ProductCreatedEvent(product.getTimes().get(0) - SimulationConfig.SIMULATION_RUNTIME, "OLD_QUEUE", new Product(product.type()));
            this.processor.addEvent(event);
        }
    }

    protected void bootSources() {
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
