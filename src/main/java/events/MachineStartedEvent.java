package events;

import abstracts.AbstractEvent;
import models.Machine;

public class MachineStartedEvent extends AbstractEvent {

    protected Machine machine;

    public MachineStartedEvent(double time, Machine machine) {
        super(time);
        this.machine = machine;
    }

    public Machine getMachine() {
        return this.machine;
    }
}
