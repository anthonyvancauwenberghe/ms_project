package simulation2.models;

import simulation2.contracts.CProcess;

public class HourlyQueueRefreshSource {

    /**
     * Eventlist that will be requested to construct events
     */
    protected final CEventList list;

    /**
     * Queue that buffers products for the machine
     */
    protected final CProcess queue;

    /**
     * Constructor, creates objects
     * Interarrival times are prespecified
     *
     * @param q The receiver of the products
     * @param l The eventlist that is requested to construct events
     */
    public HourlyQueueRefreshSource(CProcess q, CEventList l) {
        this.list = l;
        this.queue = q;
        this.init();
    }

    public void init() {
        for (int i = 0; i < 24; i++) {
            this.list.add(this.queue, 20, 3600 * i + 0.1);
        }
    }

}

