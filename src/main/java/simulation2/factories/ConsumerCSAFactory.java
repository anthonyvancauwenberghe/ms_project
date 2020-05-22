package simulation2.factories;

import simulation2.abstracts.AgentFactory;
import simulation2.contracts.ProductAcceptor;
import simulation2.models.*;

public class ConsumerCSAFactory extends AgentFactory {

    public ConsumerCSAFactory(Queue q, ProductAcceptor s, CEventList e) {
        super(q, s, e);
    }

    @Override
    protected Machine agent(int agentId) {
        return new ConsumerAgent(this.queue, this.sink, this.eventlist, "consumer Worker " + agentId);
    }
}
