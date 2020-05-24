package listeners;

import contracts.IListener;
import contracts.IQueue;
import events.MachineStartedEvent;
import events.ProductionStartedEvent;
import models.CEventList;
import models.Machine;
import models.Product;

public class AssignMachineToQueueListener implements IListener<MachineStartedEvent> {

    protected IQueue queue;

    protected final CEventList events;

    public AssignMachineToQueueListener(IQueue queue, CEventList events) {
        this.queue = queue;
        this.events = events;
    }

    @Override
    public void handle(MachineStartedEvent event) {
        Machine machine = event.getMachine();

        //Make sure the machine is available for work.
        machine.setIdle();
        machine.enable();

        Product product = this.queue.assign(machine);

        if (product != null)
            this.events.add(new ProductionStartedEvent(event.getExecutionTime(), product, machine));
    }
}
