package simulation2.enums;

import configs.SimulationConfig;
import contracts.Distribution;
import simulation2.models.Product;

public enum AgentType {
    CORPORATE(0),
    CONSUMER(1);

    protected int id;

    AgentType(int id) {
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
            return "Consumer";
        else
            return "Corporate";
    }

    public boolean canAccept(Product product){
        if (product.type().isCorporate() && this.isConsumer())
            return false;

        return true;
    }
}
