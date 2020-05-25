package calculators;

import configs.SimulationConfig;

public class AgentDailyCostCalculator {

    public static int corporateAgentsCost() {
        int cost = 0;
        for (boolean isOnShift : SimulationConfig.MORNING_SHIFT) {
            if (isOnShift)
                cost += SimulationConfig.costPerHourCorporateAgent * SimulationConfig.MORNING_CORPORATE_AGENTS;
        }

        for (boolean isOnShift : SimulationConfig.NOON_SHIFT) {
            if (isOnShift)
                cost += SimulationConfig.costPerHourCorporateAgent * SimulationConfig.NOON_CORPORATE_AGENTS;
        }

        for (boolean isOnShift : SimulationConfig.NIGHT_SHIFT) {
            if (isOnShift)
                cost += SimulationConfig.costPerHourCorporateAgent * SimulationConfig.NIGHT_CORPORATE_AGENTS;
        }
        return cost;
    }

    public static int consumerAgentsCost() {
        int cost = 0;
        for (boolean isOnShift : SimulationConfig.MORNING_SHIFT) {
            if (isOnShift)
                cost += SimulationConfig.costPerHourConsumerAgent * SimulationConfig.MORNING_CONSUMER_AGENTS;
        }

        for (boolean isOnShift : SimulationConfig.NOON_SHIFT) {
            if (isOnShift)
                cost += SimulationConfig.costPerHourConsumerAgent * SimulationConfig.NOON_CONSUMER_AGENTS;
        }

        for (boolean isOnShift : SimulationConfig.NIGHT_SHIFT) {
            if (isOnShift)
                cost += SimulationConfig.costPerHourConsumerAgent * SimulationConfig.NIGHT_CONSUMER_AGENTS;
        }
        return cost;
    }

    public static int totalCost() {
        return consumerAgentsCost() + corporateAgentsCost();
    }
}
