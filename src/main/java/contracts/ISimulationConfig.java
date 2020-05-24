package contracts;

import abstracts.AbstractEventFactory;

public interface ISimulationConfig {
    public AbstractEventFactory[] getSources();

    public IQueue[] getQueues();
}
