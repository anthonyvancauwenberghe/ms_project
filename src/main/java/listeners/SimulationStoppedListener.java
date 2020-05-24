package listeners;

import contracts.IEventProcessor;
import contracts.IListener;
import events.SimulationStoppedEvent;

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
