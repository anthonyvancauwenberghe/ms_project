package simulation2.factories;

import simulation2.contracts.ProductAcceptor;
import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;
import simulation2.models.Agent;
import simulation2.models.CEventList;
import simulation2.models.Machine;
import simulation2.models.Queue;

public class AgentFactory {

    /**
     * Eventlist that will manage events
     */
    protected final CEventList eventlist;

    /**
     * Queue from which the machine has to take products
     */
    protected final Queue queue;

    /**
     * Sink to dump products
     */
    protected final ProductAcceptor sink;

    /**
     * What type of agent is it CORPORATE|CONSUMER
     */
    protected final AgentType type;

    /**
     * What type of shift is it MORNING|NOON|NIGHT
     */
    protected final AgentShift shift;


    public AgentFactory(Queue q, ProductAcceptor s, CEventList e, AgentType type, AgentShift shift) {
        this.queue = q;
        this.sink = s;
        this.eventlist = e;
        this.type = type;
        this.shift = shift;
    }

    public Machine[] build(int agents) {
        Machine[] machines = new Machine[agents];

        for (int i = 0; i < agents; i++) {
            machines[i] = new Agent(this.queue, this.sink, this.eventlist, this.type, this.shift, i);
        }
        return machines;
    }

}
