package simulation2.abstracts;

import simulation2.contracts.ProductAcceptor;
import simulation2.models.CEventList;
import simulation2.models.Machine;
import simulation2.models.Queue;

public abstract class AgentFactory {

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

    public AgentFactory(Queue q, ProductAcceptor s, CEventList e) {
        queue = q;
        sink = s;
        eventlist = e;
    }

    public Machine[] build(int agents) {
        Machine[] machines = new Machine[agents];

        for (int i = 0; i < agents; i++) {
            machines[i] = this.agent(i);
        }
        return machines;
    }

    protected abstract Machine agent(int agentId);
}
