import configs.SimulationConfig;
import contracts.IStrategy;
import org.junit.jupiter.api.Test;
import abstracts.AbstractEvent;
import abstracts.AbstractEventFactory;
import abstracts.AbstractEventProcessor;
import contracts.IQueue;
import contracts.ISimulationConfig;
import enums.AgentShift;
import enums.MachineType;
import enums.ProductType;
import events.ProductCreatedEvent;
import factories.AgentFactory;
import factories.ProductEventFactory;
import models.Sink;
import simulation.Simulator;
import strategies.NoStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimulator {

    @Test
    void testEventGeneration() {

        final double[] consumerTimes = {2.0, 5.0};
        final double[] corporateTimes = {3.0, 7.0, SimulationConfig.SIMULATION_RUNTIME};

        final double[] times = new double[5];

        ISimulationConfig config = new ISimulationConfig() {

            @Override
            public ISimulationConfig clone() {
                return null;
            }

            @Override
            public int getQueueWarmupIterations() {
                return 0;
            }

            @Override
            public AbstractEventFactory[] getSources() {
                return new AbstractEventFactory[]{
                        new ProductEventFactory("CONSUMER_CALL_SOURCE", consumerTimes, ProductType.CONSUMER),
                        new ProductEventFactory("CORPORATE_CALL_SOURCE", corporateTimes, ProductType.CORPORATE)
                };
            }

            @Override
            public void setQueues(IQueue[] queues) {

            }

            @Override
            public int getIterations() {
                return 1;
            }

            @Override
            public IStrategy getStrategy() {
                return new NoStrategy();
            }

            @Override
            public IQueue[] getQueues() {
                return new IQueue[0];
            }
        };

        Simulator sim = new Simulator(config, new AbstractEventProcessor() {
            protected int counter = 0;

            @Override
            public void process(AbstractEvent event) {
                super.process(event);

                if (event instanceof ProductCreatedEvent) {
                    times[counter] = event.getExecutionTime();
                    counter++;
                }
            }

            @Override
            public IQueue[] getQueues() {
                return new IQueue[0];
            }

            @Override
            public Sink[] getSinks() {
                return new Sink[0];
            }
        });

        sim.run();

        assertEquals(2.0, times[0]);
        assertEquals(3.0, times[1]);
        assertEquals(7.0, times[2]);
        assertEquals(10.0, times[3]);

        //asserts the simulation is stopped on time
        assertEquals(0.0, times[4]);

    }

    @Test
    void testAgentFactory(){
        AgentFactory factory = new AgentFactory(MachineType.CONSUMER, AgentShift.NIGHT, 1);
        AbstractEvent[] events = factory.build();

        assertEquals(0,events[0].getExecutionTime());
        assertEquals(79200,events[1].getExecutionTime());
        assertEquals(21600,events[2].getExecutionTime());
    }

}
