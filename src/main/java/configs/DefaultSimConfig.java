package configs;

import abstracts.AbstractEventFactory;
import contracts.IQueue;
import contracts.ISimulationConfig;
import enums.AgentShift;
import enums.AgentType;
import enums.ProductType;
import factories.AgentFactory;
import factories.ProductEventFactory;

public class DefaultSimConfig implements ISimulationConfig {

    protected IQueue[] queues = new IQueue[0];

    public DefaultSimConfig() {
    }

    public DefaultSimConfig(IQueue[] queues) {
        this.queues = queues;
    }

    @Override
    public AbstractEventFactory[] getSources() {
        AbstractEventFactory[] sources = new AbstractEventFactory[8];

        double[] consumerTimes = SimulationConfig.CONSUMER_ARRIVAL_RATE.sampleInterArrivalTimes();
        double[] corporateTimes = SimulationConfig.CORPORATE_ARRIVAL_RATE.sampleInterArrivalTimes();

        //INIT THE CALL SOURCES
        sources[0] = new ProductEventFactory("CONSUMER_CALL_SOURCE", consumerTimes, ProductType.CONSUMER);
        sources[1] = new ProductEventFactory("CORPORATE_CALL_SOURCE", corporateTimes, ProductType.CORPORATE);

        sources[2] = new AgentFactory(AgentType.CONSUMER, AgentShift.MORNING, SimulationConfig.MORNING_CONSUMER_AGENTS);
        sources[3] = new AgentFactory(AgentType.CORPORATE, AgentShift.MORNING, SimulationConfig.MORNING_CORPORATE_AGENTS);
        sources[4] = new AgentFactory(AgentType.CONSUMER, AgentShift.NOON, SimulationConfig.NOON_CONSUMER_AGENTS);
        sources[5] = new AgentFactory(AgentType.CORPORATE, AgentShift.NOON, SimulationConfig.NOON_CORPORATE_AGENTS);
        sources[6] = new AgentFactory(AgentType.CONSUMER, AgentShift.NIGHT, SimulationConfig.NIGHT_CONSUMER_AGENTS);
        sources[7] = new AgentFactory(AgentType.CORPORATE, AgentShift.NIGHT, SimulationConfig.NIGHT_CORPORATE_AGENTS);


        return sources;
    }

    @Override
    public IQueue[] getQueues() {
        return this.queues;
    }
}
