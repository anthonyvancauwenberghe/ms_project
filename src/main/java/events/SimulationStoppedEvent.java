package events;

import abstracts.AbstractEvent;

public class SimulationStoppedEvent extends AbstractEvent {

    public SimulationStoppedEvent(double tme) {
        super(tme);
    }

}
