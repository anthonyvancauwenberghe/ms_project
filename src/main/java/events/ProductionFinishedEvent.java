package events;

import abstracts.AbstractProductEvent;
import models.Machine;
import models.Product;

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
