package simulation2.models;

import simulation2.contracts.CProcess;
import simulation2.contracts.ProductAcceptor;
import simulation2.enums.ProductType;

import java.util.ArrayList;

/**
 * Queue that stores products until they can be handled on a machine machine
 *
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Queue implements ProductAcceptor, CProcess {
    /**
     * List in which the products are kept
     */
    private ArrayList<Product> products;

    /**
     * Requests from machine that will be handling the products
     */
    private ArrayList<ProductAcceptor> processors;

    /**
     * Initializes the queue and introduces a dummy machine
     * the machine has to be specified later
     */
    public Queue(CEventList eventList) {
        this.products = new ArrayList<>();
        this.processors = new ArrayList<>();

        this.addHourlyRequeueEvents(eventList);
    }

    protected void addHourlyRequeueEvents(CEventList eventList) {
        for (int i = 1; i < 24; i++) {
            eventList.add(this, 20, 3600 * i + 0.1);
        }
    }

    /**
     * Asks a queue to give a product to a machine
     * True is returned if a product could be delivered; false if the request is queued
     */
    public boolean askProduct(ProductAcceptor machine) {
        if (!products.isEmpty()) {

            // Let the machine immediately process a product in the queue that it can accept
            for (int i = 0; i < this.products.size(); i++) {
                if (machine.giveProduct(products.get(i))) {
                    products.remove(i);
                    return true;
                }
            }
        }

        // If there are no products the machine can process place it on idle ready for work.
        processors.add(machine);
        return false;
    }

    @Override
    public void execute(int type, double tme) {
        this.reAcceptMachines();
    }

    /**
     * Every hour the roster for the agents potentially changes so the queue needs to be reaccepted to check if they can handle current calls
     * This implementation comes with a slight performance bump (
     */
    public void reAcceptMachines() {
        ProductAcceptor[] machines = new ProductAcceptor[this.processors.size()];

        //REMOVE ALL MACHINES FROM THE PROCESSOR LIST AND REACCEPT THEM
        for (int i = 0; i < this.processors.size(); i++) {
            machines[i] = processors.get(i);
        }

        this.processors = new ArrayList<>();

        for (int i = 0; i < machines.length; i++) {
            this.askProduct(machines[i]);
        }
    }

    /**
     * Offer a product to the queue
     * It is investigated whether a machine wants the product, otherwise it is stored
     */
    public boolean giveProduct(Product p) {
        int someSize = processors.size();
        for (int i = 0; i < processors.size(); i++) {

            if (processors.get(i) == null)
                System.out.println("machine is null");

            // MAJOR CHANGE: ONLY REMOVE THE PROCESSOR WHEN THE PRODUCT IS DELIVERED
            if (processors.get(i).giveProduct(p)) {
                processors.remove(i);
                return true;
            }
        }

        products.add(p); // if the product couldn't be processed add it to the queue
        return true;
    }

    public int remaining() {
        return this.products.size();
    }

    public int remaining(ProductType type) {
        int count = 0;
        for (Product p : this.products) {
            if (p.type().equals(type))
                count++;
        }
        return count;
    }
}