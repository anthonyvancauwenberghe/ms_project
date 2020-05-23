package simulation2.models;

import configs.SimulationConfig;
import simulation2.contracts.CProcess;
import simulation2.contracts.ProductAcceptor;
import simulation2.enums.ProductType;

/**
 * A source of products
 * This class implements CProcess so that it can execute events.
 * By continuously creating new events, the source keeps busy.
 *
 * @author Joel Karel
 * @version %I%, %G%
 */
public class OldQueueSource implements CProcess {

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
     * Constructor, creates objects
     * Interarrival times are prespecified
     *
     * @param q The receiver of the products
     * @param l The eventlist that is requested to construct events
     * @param n Name of object
     */
    public OldQueueSource(ProductAcceptor q, CEventList l, Queue oldQueue) {
        this.list = l;
        this.queue = q;
        this.name = "OLD_QUEUE_SOURCE";

        this.init(oldQueue);
    }

    public void init(Queue oldQueue) {

        for (int i = 0; i < oldQueue.getProducts().size(); i++) {
            // give arrived product to queue
            Product p = new Product(oldQueue.getProducts().get(i).type());

            p.stamp(0, "Creation", name);
            this.queue.giveProduct(p);

            list.add(this, 0, 0);
        }
    }

    public void execute(int type, double tme) {
        return;
    }
}