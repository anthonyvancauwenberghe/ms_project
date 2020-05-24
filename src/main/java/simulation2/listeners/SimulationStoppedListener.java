package simulation2.listeners;

import simulation2.contracts.IEventProcessor;
import simulation2.contracts.IListener;
import simulation2.Simulator;
import simulation2.events.SimulationStoppedEvent;

public class SimulationStoppedListener implements IListener<SimulationStoppedEvent> {

    protected final IEventProcessor eventProcessor;

    public SimulationStoppedListener(IEventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void handle(SimulationStoppedEvent event) {
        this.eventProcessor.stop();
    }
}
