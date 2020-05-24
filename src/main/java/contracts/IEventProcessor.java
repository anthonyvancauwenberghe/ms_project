package contracts;

import abstracts.AbstractEvent;
import models.Sink;

public interface IEventProcessor {

    public void addEvent(AbstractEvent event);

    public void start();

    public void stop();

    public IQueue[] getQueues();

    public Sink[] getSinks();

}
