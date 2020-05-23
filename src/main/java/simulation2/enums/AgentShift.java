package simulation2.enums;

import configs.SimulationConfig;

public enum AgentShift {
    MORNING(0),
    NOON(1),
    NIGHT(2);

    protected int id;

    AgentShift(int id) {
        this.id = id;
    }

    public boolean isNoon() {
        return this.id == 1;
    }

    public boolean isMorning() {
        return this.id == 0;
    }

    public boolean isNight() {
        return this.id == 2;
    }

    @Override
    public String toString() {
        switch (this.id) {
            case 0:
                return "MORNING";
            case 1:
                return "NOON";
            case 2:
                return "NIGHT";
            default:
                throw new RuntimeException("unsupported agent shift type");
        }
    }

    public boolean[] getRoster() {
        switch (this.id) {
            case 0:
                return SimulationConfig.MORNING_SHIFT;
            case 1:
                return SimulationConfig.NOON_SHIFT;
            case 2:
                return SimulationConfig.NIGHT_SHIFT;
            default:
                throw new RuntimeException("unsupported agent shift type");
        }
    }
}
