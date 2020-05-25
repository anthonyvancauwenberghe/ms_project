package enums;

import configs.SimulationConfig;
import contracts.Distribution;

public enum ProductType {
    CORPORATE(0),
    CONSUMER(1);

    protected int id;

    ProductType(int id) {
        this.id = id;
    }

    public boolean isCorporate() {
        return this.id == 0;
    }

    public boolean isConsumer() {
        return this.id == 1;
    }

    public Distribution<Double> getServiceTimeDistribution() {
        if (this.isConsumer())
            return SimulationConfig.CONSUMER_SERVICE_DISTRIBUTION;

        return SimulationConfig.CORPORATE_SERVICE_DISTRIBUTION;
    }

    @Override
    public String toString() {
        if (this.isConsumer())
            return "CONSUMER";
        else
            return "CORPORATE";
    }
}
