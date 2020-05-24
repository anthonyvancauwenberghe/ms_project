package models;

import abstracts.AbstractEvent;

import java.util.*;

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
    protected final LinkedList<AbstractEvent> events = new LinkedList<>();

    /**
     * Decides if the eventlist needs to be resorted
     */
    protected boolean dirty = false;

    public List<AbstractEvent> all() {
        this.checkIfDirty();
        return events;
    }

    public int count() {
        return this.events.size();
    }

    public boolean empty() {
        return this.events.isEmpty();
    }

    public AbstractEvent get(int index) {
        this.checkIfDirty();
        return this.events.get(index);
    }

    public AbstractEvent remove(int index) {
        this.checkIfDirty();
        return this.events.remove(index);
    }

    public AbstractEvent removeFirst() {
        this.checkIfDirty();

        try {
            return this.events.removeFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void addUnsorted(AbstractEvent evnt){
        this.dirty = true;

        // Else the new event is appended to the list
        events.addLast(evnt);
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

    protected void checkIfDirty() {
        if (this.dirty && !this.empty()) {
            this.sortEventList();
            this.dirty = false;
        }
    }

    protected void sortEventList() {
        Collections.sort(this.events, new Comparator<AbstractEvent>() {
            @Override
            public int compare(AbstractEvent e1, AbstractEvent e2) {
                if (e1.getExecutionTime() < e2.getExecutionTime()) return -1;
                if (e1.getExecutionTime() > e2.getExecutionTime()) return 1;
                return 0;
            }
        });
    }
}