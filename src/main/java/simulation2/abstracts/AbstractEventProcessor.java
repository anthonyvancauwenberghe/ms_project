package simulation2.abstracts;

import configs.SimulationConfig;
import simulation2.contracts.IEventProcessor;
import simulation2.events.SimulationStoppedEvent;
import simulation2.listeners.SimulationStoppedListener;
import simulation2.models.CEventList;

public abstract class AbstractEventProcessor implements IEventProcessor {

    /**
     * Stop flag
     */
    private boolean stopFlag = false;

    /**
     * The time in the simulation
     */
    private double currentTime = 0;

    /**
     * The list object with events
     */
    protected final CEventList events = new CEventList();

    /**
     * IMPORTANT METHOD!
     * All the events are caught and processed here.
     */
    protected void process(AbstractEvent event) {
        if (event instanceof SimulationStoppedEvent) {
            (new SimulationStoppedListener(this)).handle((SimulationStoppedEvent) event);
        }
    }

    @Override
    public void addEvent(AbstractEvent event) {
        this.getEvents().add(event);
    }

    protected CEventList getEvents() {
        return this.events;
    }

    public void stop() {
        this.stopFlag = true;
    }

    public void start() {
        this.stopFlag = false;

        //This will stop the simulation after 24 hours
        this.getEvents().add(new SimulationStoppedEvent(SimulationConfig.SIMULATION_RUNTIME));

        // stop criterion
        while ((this.getEvents().count() > 0) && (!this.stopFlag)) {

            AbstractEvent event = this.getEvents().removeFirst();

            //event equal null means no events are left
            if (event == null) {
                return;
            }

            // Make the simulation time equal to the execution time of the first event in the list that has to be processed
            this.currentTime = event.getExecutionTime();

            // Let the event be processed by the eventprocessor and remove it from the eventlist
            this.process(event);
        }

        if (!this.getEvents().empty())
            System.out.println("Stopped simulation with " + this.getEvents().count() + " event(s) remaining. ");
    }

    public double getCurrentTime() {
        return this.currentTime;
    }
}
