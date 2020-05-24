package abstracts;

import contracts.IEventFactory;

public abstract class AbstractEventFactory implements IEventFactory {

    /**
     * Name of the source
     */
    protected final String name;

    public AbstractEventFactory(String name) {
        this.name = name;
    }
}
