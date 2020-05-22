package simulation2.factories;

import simulation2.abstracts.AgentFactory;
import simulation2.contracts.ProductAcceptor;
import simulation2.models.CEventList;
import simulation2.models.CorporateAgent;
import simulation2.models.Machine;
import simulation2.models.Queue;

public class CorporateCSAFactory extends AgentFactory {

    public CorporateCSAFactory(Queue q, ProductAcceptor s, CEventList e) {
        super(q, s, e);
    }

    @Override
    protected Machine agent(int agentId) {
        return new CorporateAgent(this.queue, this.sink, this.eventlist, "corporate Worker "+agentId);
    }
}
