package models;

import enums.AgentShift;
import enums.MachineType;

public class CallAgent extends Machine {
    protected AgentShift shift;

    public CallAgent(MachineType type, AgentShift shift, int id) {
        super(shift.toString() + "_" + type.toString() + "_" + id, type);
        this.shift = shift;
    }

    @Override
    public boolean give(Product p) {
        if (this.isIdle() && this.type.canAccept(p))
            return super.give(p);

        return false;
    }

}
