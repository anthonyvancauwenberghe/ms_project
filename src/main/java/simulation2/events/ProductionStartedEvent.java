package simulation2.events;

import simulation2.abstracts.AbstractProductEvent;
import simulation2.models.Machine;
import simulation2.models.Product;

public class ProductionStartedEvent extends AbstractProductEvent {

    protected Machine machine;

    public ProductionStartedEvent(double time, Product product, Machine machine) {
        super(time, machine.getName(), product);
        this.machine = machine;
    }

    public Machine getMachine() {
        return machine;
    }

}
