package simulation2.models;

import simulation2.contracts.ProductAcceptor;
import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;

public class Agent extends Machine {

    protected AgentType type;
    protected AgentShift shift;

    public Agent(Queue q, ProductAcceptor s, CEventList e, AgentType type, AgentShift shift, int id) {
        super(q, s, e, shift.toString() + "_" + type.toString() + "_" + id);
        this.type = type;
        this.shift = shift;
        this.init();
    }

    protected boolean isWorkingShift() {
        int hour = (int) (this.eventlist.getTime() - (this.eventlist.getTime() % 3600)) / 3600;

        if (hour > 23 || hour<0)
            throw new RuntimeException("simulation time is larger than 24h. not allowed");

        return this.shift.getRoster()[hour];
    }

    @Override
    public boolean giveProduct(Product p) {
        if (this.isWorkingShift() && this.type.canAccept(p))
            return super.giveProduct(p);

        return false;
    }
}
