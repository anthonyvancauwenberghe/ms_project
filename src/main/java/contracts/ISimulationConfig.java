package contracts;

import abstracts.AbstractEventFactory;

public interface ISimulationConfig {
    public AbstractEventFactory[] getSources();

    public IQueue[] getQueues();

    public void setQueues(IQueue[] queues);

    public int getIterations();

    public IStrategy getStrategy();
}
