package simulation2.models;

import configs.SimulationConfig;
import simulation2.contracts.CProcess;
import simulation2.contracts.ProductAcceptor;


/**
 * Cleaned up implementation of the Machine model.
 *
 * @author Joel Karel
 */
public abstract class Machine implements CProcess, ProductAcceptor {

    /**
     * Product that is being handled
     */
    private Product product;

    /**
     * Eventlist that will manage events
     */
    protected final CEventList eventlist;

    /**
     * Queue from which the machine has to take products
     */
    protected final Queue queue;

    /**
     * Sink to dump products
     */
    protected final ProductAcceptor sink;

    /**
     * Status of the machine (idle | busy)
     */
    protected boolean idle = true;

    protected double busyUntil = -1;

    /**
     * Machine name
     */
    protected final String name;


    public Machine(Queue q, ProductAcceptor s, CEventList e, String n) {
        queue = q;
        sink = s;
        eventlist = e;
        name = n;
    }

    protected void init() {
        queue.askProduct(this);
    }

    /**
     * Production is finished. Machine is ready to accept new work.
     */
    @Override
    public void execute(int type, double tme) {

        // print arrival
        if (SimulationConfig.DEBUG)
            System.out.println("Arrival at time = " + tme);

        switch (type) {
            case 50:

                break;
            default:
                this.finishProduction(tme);
        }

        // set machine status to idle
        this.setIdle();

        // Ask the queue for more products
        queue.askProduct(this);
    }

    public void finishProduction(double tme) {
        // Remove product from system
        this.product.stamp(tme, "Production complete", name);
        this.sink.giveProduct(product);
        this.product = null;
    }

    public void finishProductionFromPreviousQueue() {

    }

    public void transferProductInProduction(Product p) {
        // accept the product
        this.product = p;

        if(this.product.getTimes().size() != 2)
            throw new RuntimeException("can only accept products that are already in production");

        this.product.getTimes().set(0, this.product.getTimes().get(0) - 86400);
        this.product.getTimes().set(1, this.product.getTimes().get(1) - 86400);

        // start production
        startProduction(product);
    }

    /**
     * Give a product blueprint to a machine and let it produce it.
     */
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

        // calculate the production duration
        double duration = product.type().getServiceTimeDistribution().sample();

        // set status to busy
        this.setBusy(eventlist.getTime() + duration, 0);
    }

    public double getBusyUntil() {
        return busyUntil;
    }

    protected void setIdle() {
        this.busyUntil = -1;
        this.idle = true;
    }

    protected void setBusy(double busyUntil, int type) {
        eventlist.add(this, type, busyUntil);
        this.busyUntil = busyUntil;
        this.idle = false;
    }


    protected boolean isIdle() {
        return this.idle;
    }

    protected boolean isBusy() {
        return !this.idle;
    }
}
