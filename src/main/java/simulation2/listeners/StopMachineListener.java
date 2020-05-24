package simulation2.listeners;

import simulation2.contracts.IListener;
import simulation2.events.MachineStoppedEvent;

public class StopMachineListener implements IListener<MachineStoppedEvent> {

    @Override
    public void handle(MachineStoppedEvent event) {
        event.getMachine().stop();
    }
}
