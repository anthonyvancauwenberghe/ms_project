package simulation2.abstracts;

public abstract class AbstractEvent {

    /** The time on which the event will be executed */
    private final double executionTime;

    /**
     *	Constructor for objects
     *    @param tme    The time on which the event will be executed
     */
    public AbstractEvent(double tme) {
        this.executionTime = tme;
    }

    /**
     *	Method to ask the event at which time it will be executed
     *    @return The time at which the event will be executed
     */
    public double getExecutionTime() {
        return this.executionTime;
    }
}
