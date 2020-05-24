package simulation2.models;

import simulation2.abstracts.AbstractEvent;
import simulation2.events.SimulationStoppedEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Event processing mechanism
 * Events are created here and it is ensured that they are processed in the proper order
 * The simulation clock is located here.
 *
 * @author Joel Karel
 * @version %I%, %G%
 */
public class CEventList {
    /**
     * List of events that have to be executed
     */
    private final LinkedList<AbstractEvent> events = new LinkedList<>();

    public List<AbstractEvent> all() {
        return events;
    }

    public int count() {
        return this.events.size();
    }

    public boolean empty() {
        return this.events.isEmpty();
    }

    public AbstractEvent get(int index) {
        return this.events.get(index);
    }

    public AbstractEvent remove(int index) {
        return this.events.remove(index);
    }

    public AbstractEvent removeFirst() {
        try{
            return this.events.removeFirst();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    /**
     * Method for the construction of a new event.
     */
    public void add(AbstractEvent evnt) {
        // First create a new event using the parameters
        // Now it is examined where the event has to be inserted in the list
        // Loop through it with a counter because the eventlist is a linked list.

        int counter = 0;
        for (AbstractEvent event : this.events) {

            if (event.getExecutionTime() > evnt.getExecutionTime()) {
                // If an event is found in the list that has to be executed after the current event

                // Then the new event is inserted before that element
                this.events.add(counter, evnt);
                return;
            }
            counter++;
        }


        // Else the new event is appended to the list
        events.addLast(evnt);

    }

    public double getTime(){
        //TODO DELETE THIS METHOD
         return 0;
    }

}