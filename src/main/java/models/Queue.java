package models;

import contracts.IQueue;

import java.util.ArrayList;
import java.util.List;

public class Queue implements IQueue {
    /**
     * List in which the products are kept
     */
    protected final ArrayList<Product> queue = new ArrayList<>();

    /**
     * List of agents (machines)
     */
    protected final ArrayList<Machine> machines = new ArrayList<>();

    /**
     * Add a new product to the queue
     * Try to process it immediately and return the agent processing it
     * return null if no agent can process it
     */
    @Override
    public Machine add(Product product) {
        this.removeInOperationalMachines();

        for (Machine machine : this.machines) {
            if (machine.give(product)) {
                return machine;
            }
        }
        this.queue.add(product);

        return null;
    }

    /**
     * An agent is asking for a new product
     * returns null if the agent can process none of the products
     */
    @Override
    public Product ask(Machine machine) {
        int counter = 0;

        if (!machine.isOperational())
            this.removeInOperationalMachines();

        for (Product product : this.queue) {
            if (machine.give(product)) {
                this.queue.remove(counter);
                return product;
            }
            counter++;
        }

        return null;
    }

    protected void removeInOperationalMachines() {
        int counter = 0;

        // Remove like this to avoid a concurrentmodificationexception
        List<Machine> toRemove = new ArrayList<>();

        for (Machine machine : this.machines) {
            if (!machine.isOperational()) {
                toRemove.add(this.machines.get(counter));
            }
            counter++;
        }
        if (!toRemove.isEmpty())
            this.machines.removeAll(toRemove);
    }

    @Override
    public Product assign(Machine machine) {
        this.machines.add(machine);
        return this.ask(machine);
    }

    public ArrayList<Product> getQueue() {
        return queue;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    @Override
    public int count() {
        return this.queue.size();
    }
}
