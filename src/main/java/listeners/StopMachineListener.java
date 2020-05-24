package listeners;

import contracts.IListener;
import events.MachineStoppedEvent;

public class StopMachineListener implements IListener<MachineStoppedEvent> {

    @Override
    public void handle(MachineStoppedEvent event) {
        event.getMachine().disable();
    }
}
