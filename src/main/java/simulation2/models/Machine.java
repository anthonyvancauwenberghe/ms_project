package simulation2.models;

import simulation2.contracts.CProcess;
import simulation2.contracts.ProductAcceptor;


/**
 * Cleaned up implementation of the Machine model.
 *	@author Joel Karel
 */
public abstract class Machine implements CProcess, ProductAcceptor {

    /**
     * Product that is being handled
     */
    private Product product;

    /**
     * Eventlist that will manage events
     */
    private final CEventList eventlist;

    /**
     * Queue from which the machine has to take products
     */
    private final Queue queue;

    /**
     * Sink to dump products
     */
    private final ProductAcceptor sink;

    /**
     * Status of the machine (idle | busy)
     */
    protected boolean idle = true;

    /**
     * Machine name
     */
    private final String name;



    public Machine(Queue q, ProductAcceptor s, CEventList e, String n) {
        queue = q;
        sink = s;
        eventlist = e;
        name = n;
        queue.askProduct(this);
    }

    @Override
    public void execute(int type, double tme) {
        // show arrival
        System.out.println("Product finished at time = " + tme);

        // Remove product from system
        product.stamp(tme, "Production complete", name);

        sink.giveProduct(product);
        product = null;

        // set machine status to idle
        this.idle = true;

        // Ask the queue for products
        queue.askProduct(this);
    }

    @Override
    public boolean giveProduct(Product p) {

        if (this.isIdle()) {

            // accept the product
            this.product = p;

            // mark starting time
            this.product.stamp(eventlist.getTime(), "Production started", name);

            // start production
            startProduction(product);

            // Flag that the product has arrived
            return true;
        }

        return false;
    }

    /**
     * Starting routine for the production
     * Start the handling of the current product with the time sampled from a given distribution
     * This time is placed in the eventlist
     */
    private void startProduction(Product product) {

        // generate duration
        double duration = product.type().getServiceTimeDistribution().sample();

        eventlist.add(this, 0, eventlist.getTime() + duration);

        // set status to busy
        this.idle = false;
    }


    protected boolean isIdle() {
        return this.idle;
    }

    protected boolean isBusy() {
        return !this.idle;
    }
}
