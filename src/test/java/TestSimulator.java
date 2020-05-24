import configs.SimulationConfig;
import org.junit.jupiter.api.Test;
import simulation2.Simulator;
import simulation2.abstracts.AbstractEvent;
import simulation2.abstracts.AbstractEventProcessor;
import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;
import simulation2.enums.ProductType;
import simulation2.events.ProductCreatedEvent;
import simulation2.factories.AgentFactory;
import simulation2.factories.ProductEventFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimulator {

    @Test
    void testEventGeneration() {

        double[] consumerTimes = {2.0, 5.0};
        double[] corporateTimes = {3.0, 7.0, SimulationConfig.SIMULATION_RUNTIME};

        final double[] times = new double[5];

        Simulator sim = new Simulator(new AbstractEventProcessor() {
            protected int counter = 0;

            @Override
            public void process(AbstractEvent event) {
                super.process(event);

                if (event instanceof ProductCreatedEvent) {
                    times[counter] = event.getExecutionTime();
                    counter++;
                }
            }

        });
        sim.source(new ProductEventFactory("CONSUMER_CALL_SOURCE", consumerTimes, ProductType.CONSUMER));
        sim.source(new ProductEventFactory("CORPORATE_CALL_SOURCE", corporateTimes, ProductType.CORPORATE));

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
        AgentFactory factory = new AgentFactory(AgentType.CONSUMER, AgentShift.NIGHT, 1);
        AbstractEvent[] events = factory.build();

        assertEquals(0,events[0].getExecutionTime());
        assertEquals(79200,events[1].getExecutionTime());
        assertEquals(21600,events[2].getExecutionTime());
    }

}
