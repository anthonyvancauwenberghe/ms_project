package simulation2.events;

import simulation2.abstracts.AbstractEvent;
import simulation2.models.Machine;

public class MachineCreatedEvent extends AbstractEvent {

    protected Machine machine;

    public MachineCreatedEvent(double time, Machine machine) {
        super(time);
        this.machine = machine;
    }

    public Machine getMachine() {
        return this.machine;
    }
}
