package simulation2.contracts;

import simulation2.abstracts.AbstractEvent;

public interface IEventFactory {
    public AbstractEvent[] build();
}
