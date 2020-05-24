package simulation2;

import configs.SimulationConfig;
import simulation2.abstracts.AbstractEvent;
import simulation2.abstracts.AbstractEventFactory;
import simulation2.contracts.IEventProcessor;
import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;
import simulation2.enums.ProductType;
import simulation2.factories.AgentFactory;
import simulation2.factories.ProductEventFactory;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private final List<AbstractEventFactory> sources = new ArrayList<>();

    protected final IEventProcessor processor;

    public Simulator(IEventProcessor processor) {
        this.processor = processor;
    }

    public Simulator source(AbstractEventFactory source) {
        this.sources.add(source);
        return this;
    }

    public void run() {
        this.bootSources();
        this.processor.start();

        String test = "";
    }

    protected void bootSources() {
        for (AbstractEventFactory source : this.sources) {
            for (AbstractEvent event : source.build()) {
                this.processor.addEvent(event);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Simulator sim = new Simulator(new EventProcessor());

        //double[] consumerTimes = {2.0, 5.0};
        //double[] corporateTimes = {3.0, 7.0};

        double[] consumerTimes = SimulationConfig.CONSUMER_ARRIVAL_RATE.sampleInterArrivalTimes();
        double[] corporateTimes = SimulationConfig.CORPORATE_ARRIVAL_RATE.sampleInterArrivalTimes();

        sim.source(new ProductEventFactory("CONSUMER_CALL_SOURCE", consumerTimes, ProductType.CONSUMER));
        sim.source(new ProductEventFactory("CORPORATE_CALL_SOURCE", corporateTimes, ProductType.CORPORATE));

        sim.source(new AgentFactory(AgentType.CONSUMER, AgentShift.MORNING, SimulationConfig.MORNING_CONSUMER_AGENTS));
        sim.source(new AgentFactory(AgentType.CORPORATE, AgentShift.MORNING, SimulationConfig.MORNING_CORPORATE_AGENTS));

        sim.source(new AgentFactory(AgentType.CONSUMER, AgentShift.NOON, SimulationConfig.NOON_CONSUMER_AGENTS));
        sim.source(new AgentFactory(AgentType.CORPORATE, AgentShift.NOON, SimulationConfig.NOON_CORPORATE_AGENTS));

        sim.source(new AgentFactory(AgentType.CONSUMER, AgentShift.NIGHT, SimulationConfig.NIGHT_CONSUMER_AGENTS));
        sim.source(new AgentFactory(AgentType.CORPORATE, AgentShift.NIGHT, SimulationConfig.NIGHT_CORPORATE_AGENTS));

        sim.run();
    }
}
