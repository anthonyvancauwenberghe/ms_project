package simulation2.events;

import simulation2.abstracts.AbstractEvent;

public class SimulationStoppedEvent extends AbstractEvent {

    public SimulationStoppedEvent(double tme) {
        super(tme);
    }

}
