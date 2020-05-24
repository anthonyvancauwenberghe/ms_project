package events;

import abstracts.AbstractEvent;
import models.Machine;

public class MachineStoppedEvent extends AbstractEvent {

    protected Machine machine;

    public MachineStoppedEvent(double time, Machine machine) {
        super(time);
        this.machine = machine;
    }

    public Machine getMachine() {
        return machine;
    }
}
