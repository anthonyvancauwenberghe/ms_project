package simulation2.models;

import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;

public class CallAgent extends Machine {

    protected AgentType type;
    protected AgentShift shift;

    public CallAgent(AgentType type, AgentShift shift, int id) {
        super(shift.toString() + "_" + type.toString() + "_" + id);
        this.type = type;
        this.shift = shift;
    }

    @Override
    public boolean give(Product p) {
        if (this.isIdle() && this.type.canAccept(p))
            return super.give(p);

        return false;
    }

    public AgentType getType() {
        return type;
    }

    public AgentShift getShift() {
        return shift;
    }
}
