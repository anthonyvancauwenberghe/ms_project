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
    protected final ArrayList<Product> products = new ArrayList<>();

    /**
     * Requests from machine that will be handling the products
     */
    protected ArrayList<ProductAcceptor> idleProcessors = new ArrayList<>();

    /**
     * Requests from machine that will be handling the products
     */
    protected ArrayList<ProductAcceptor> busyProcessors = new ArrayList<>();


    public ArrayList<Product> getProducts() {
        return this.products;
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
        idleProcessors.add(machine);
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
        ProductAcceptor[] machines = new ProductAcceptor[this.idleProcessors.size()];

        //REMOVE ALL MACHINES FROM THE PROCESSOR LIST AND REACCEPT THEM
        for (int i = 0; i < this.idleProcessors.size(); i++) {
            machines[i] = idleProcessors.get(i);
        }

        this.idleProcessors = new ArrayList<>();

        for (int i = 0; i < machines.length; i++) {
            this.askProduct(machines[i]);
        }
    }

    /**
     * Offer a product to the queue
     * It is investigated whether a machine wants the product, otherwise it is stored
     */
    public boolean giveProduct(Product p) {
        for (int i = 0; i < idleProcessors.size(); i++) {

            if (idleProcessors.get(i) == null)
                System.out.println("machine is null");

            // MAJOR CHANGE: ONLY REMOVE THE PROCESSOR WHEN THE PRODUCT IS DELIVERED
            if (idleProcessors.get(i).giveProduct(p)) {
                ProductAcceptor machine = idleProcessors.remove(i);
                busyProcessors.add(machine);
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