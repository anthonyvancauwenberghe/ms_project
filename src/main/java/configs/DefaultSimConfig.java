package configs;

import abstracts.AbstractEventFactory;
import contracts.IQueue;
import contracts.ISimulationConfig;
import contracts.IStrategy;
import enums.AgentShift;
import enums.MachineType;
import enums.ProductType;
import factories.AgentFactory;
import factories.InterArrivalTimesFactory;
import factories.ProductEventFactory;

/**
 * This config file initiates the simulation with the standard settings found in all the configs.
 * You can make your own config file to run simulations with custom configuration parameters
 * that need to be initialized on runtime (e.g when you are running an optimization algorithm)
 */
public class DefaultSimConfig implements ISimulationConfig {

    protected IQueue[] queues = new IQueue[0];

    protected int iterations = SimulationConfig.SIMULATION_COUNT;

    public DefaultSimConfig() {
    }

    public DefaultSimConfig(int iterations) {
        this.iterations = iterations;
    }

    public DefaultSimConfig(IQueue[] queues) {
        this.queues = queues;
    }

    @Override
    public AbstractEventFactory[] getSources() {
        AbstractEventFactory[] sources = new AbstractEventFactory[8];

        double[] consumerInterArrivalTimes = new InterArrivalTimesFactory(SimulationConfig.CONSUMER_ARRIVAL_RATE.sampleArrivalRates()).build();
        double[] corporateInterArrivalTimes = new InterArrivalTimesFactory(SimulationConfig.CORPORATE_ARRIVAL_RATE.sampleArrivalRates()).build();

        /**
         * The sources that will generate the arrival time events
         */
        sources[0] = new ProductEventFactory("CONSUMER_CALL_SOURCE", consumerInterArrivalTimes, ProductType.CONSUMER);
        sources[1] = new ProductEventFactory("CORPORATE_CALL_SOURCE", corporateInterArrivalTimes, ProductType.CORPORATE);

        /**
         * The sources that will generate the machine creation events
         */
        sources[2] = new AgentFactory(MachineType.CONSUMER, AgentShift.MORNING, ScheduleConfig.MORNING_CONSUMER_AGENTS);
        sources[3] = new AgentFactory(MachineType.CORPORATE, AgentShift.MORNING, ScheduleConfig.MORNING_CORPORATE_AGENTS);
        sources[4] = new AgentFactory(MachineType.CONSUMER, AgentShift.NOON, ScheduleConfig.NOON_CONSUMER_AGENTS);
        sources[5] = new AgentFactory(MachineType.CORPORATE, AgentShift.NOON, ScheduleConfig.NOON_CORPORATE_AGENTS);
        sources[6] = new AgentFactory(MachineType.CONSUMER, AgentShift.NIGHT, ScheduleConfig.NIGHT_CONSUMER_AGENTS);
        sources[7] = new AgentFactory(MachineType.CORPORATE, AgentShift.NIGHT, ScheduleConfig.NIGHT_CORPORATE_AGENTS);

        return sources;
    }

    @Override
    public IQueue[] getQueues() {
        return this.queues;
    }

    @Override
    public void setQueues(IQueue[] queues) {
        this.queues = queues;
    }

    public int getIterations() {
        return this.iterations;
    }

    @Override
    public IStrategy getStrategy() {
        return SimulationConfig.strategy;
    }

    public int getQueueWarmupIterations(){
        return SimulationConfig.MAX_QUEUE_WARM_UP_ITERATIONS;
    }
}
