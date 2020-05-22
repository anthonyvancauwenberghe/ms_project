package simulation2.models;

import simulation2.contracts.CProcess;
import simulation2.contracts.ProductAcceptor;

/**
 * A source of products
 * This class implements CProcess so that it can execute events.
 * By continuously creating new events, the source keeps busy.
 *
 * @author Joel Karel
 * @version %I%, %G%
 */
public class Source implements CProcess {

    /**
     * Eventlist that will be requested to construct events
     */
    protected final CEventList list;

    /**
     * Queue that buffers products for the machine
     */
    protected final ProductAcceptor queue;

    /**
     * Name of the source
     */
    protected final String name;

    /**
     * Interarrival times (in case pre-specified)
     */
    protected final double[] interarrivalTimes;

    /**
     * Interarrival time iterator
     */
    private int interArrCnt = 0;

    /**
     * Distribution from which the the duration to process the product is sampled
     */
    protected ProductType productType;

    /**
     * Constructor, creates objects
     * Interarrival times are prespecified
     *
     * @param q  The receiver of the products
     * @param l  The eventlist that is requested to construct events
     * @param n  Name of object
     * @param ia interarrival times
     */
    public Source(ProductAcceptor q, CEventList l, String n, double[] ia, ProductType type) {
        this.list = l;
        this.queue = q;
        this.name = n;
        this.interarrivalTimes = ia;

        this.productType = type;

        // put first event in list for initialization
        this.list.add(this, 0, interarrivalTimes[0]); //target,type,time
    }

    @Override
    public void execute(int type, double tme) {

        if (interArrCnt >= interarrivalTimes.length - 1) {
            System.out.println("No more arrivals.. Reached end of arrival time list at source " + this.name);
            return;
        }

        // show arrival
        System.out.println(this.productType.toString() +" call came in at time: " + tme);

        // give arrived product to queue
        Product p = new Product(this.productType);

        p.stamp(tme, "Creation", name);
        queue.giveProduct(p);

        this.interArrCnt++;

        list.add(this, 0, tme + interarrivalTimes[interArrCnt]);
    }
}