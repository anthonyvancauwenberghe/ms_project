package simulation2.events;

import simulation2.abstracts.AbstractProductEvent;
import simulation2.models.Machine;
import simulation2.models.Product;

public class ProductionFinishedEvent extends AbstractProductEvent {

    protected Machine machine;

    public ProductionFinishedEvent(double time, Product product, Machine machine) {
        super(time, machine.getName(),product);
        this.machine = machine;
    }

    public Machine getMachine() {
        return machine;
    }

}
