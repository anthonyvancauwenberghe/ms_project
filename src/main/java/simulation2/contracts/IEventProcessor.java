package simulation2.contracts;

import simulation2.abstracts.AbstractEvent;

public interface IEventProcessor {

    public void addEvent(AbstractEvent event);

    public void start();

    public void stop();

}
