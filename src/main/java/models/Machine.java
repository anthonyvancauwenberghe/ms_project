package models;

public class Machine {
    /**
     * Product that is being handled
     */
    protected Product product;

    /**
     * Status of the machine (idle | busy)
     */
    protected boolean idle = true;

    /**
     * Determines if the machine is operational
     * if the machine is not operational it
     * will be unassigned from all queues it's currently working on
     */
    protected boolean operational = true;

    /**
     * Machine name
     */
    protected final String name;


    public Machine(String n) {
        name = n;
    }


    /**
     * Give a product blueprint to a machine and let it produce it.
     */
    public boolean give(Product p) {

        if (this.isIdle() && this.isOperational()) {

            // accept the product
            this.product = p;
            this.setBusy();

            // Flag that the product has been accepted by the agent
            return true;
        }

        return false;
    }

    public void setIdle() {
        this.product = null;
        this.idle = true;
    }

    public void setBusy() {
        this.idle = false;
    }

    protected boolean isIdle() {
        return this.idle;
    }

    protected boolean isBusy() {
        return !this.idle;
    }

    public String getName() {
        return name;
    }

    public boolean isOperational() {
        return operational;
    }

    public void disable() {
        this.operational = false;
    }

    public void enable() {
        this.operational = true;
    }
}
