package enums;

import models.Product;

public enum MachineType {
    CORPORATE(0),
    CONSUMER(1);

    protected int id;

    MachineType(int id) {
        this.id = id;
    }

    public boolean isCorporate() {
        return this.id == 0;
    }

    public boolean isConsumer() {
        return this.id == 1;
    }

    @Override
    public String toString() {
        if(this.isConsumer())
            return "CONSUMER";
        else
            return "CORPORATE";
    }

    public boolean canAccept(Product product){
        if (product.type().isCorporate() && this.isConsumer())
            return false;

        return true;
    }
}
