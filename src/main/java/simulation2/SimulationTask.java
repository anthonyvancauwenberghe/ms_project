/**
 * Example program for using eventlists
 *
 * @author Joel Karel
 * @version %I%, %G%
 */

package simulation2;

import configs.SimulationConfig;
import simulation2.enums.AgentShift;
import simulation2.enums.AgentType;
import simulation2.enums.ProductType;
import simulation2.factories.AgentFactory;
import simulation2.models.*;

public class SimulationTask {

    protected Queue consumerQueue = new Queue();
    protected Queue corporateQueue = new Queue();

    protected final Source consumerSource;
    protected final Source corporateSource;

    protected Sink sink =  new Sink("sink");

    protected final CEventList eventList =  new CEventList();

    public SimulationTask() {
        this.consumerSource = new Source(
                this.consumerQueue,
                this.eventList,
                "CONSUMER_CALL_SOURCE",
                SimulationConfig.CONSUMER_ARRIVAL_RATE.sampleInterArrivalTimes(),
                ProductType.CONSUMER
        );

        this.corporateSource = new Source(
                this.corporateQueue,
                this.eventList,
                "CORPORATE_CALL_SOURCE",
                SimulationConfig.CORPORATE_ARRIVAL_RATE.sampleInterArrivalTimes(),
                ProductType.CORPORATE
        );


    }

    public SimulationTask(Queue previousConsumerQueue, Queue previousCorporateQueue) {
        this.consumerSource = new Source(
                this.consumerQueue,
                this.eventList,
                "CONSUMER_CALL_SOURCE",
                this.buildIAFromOldQueue(previousConsumerQueue, SimulationConfig.CONSUMER_ARRIVAL_RATE.sampleInterArrivalTimes()),
                ProductType.CONSUMER
        );

        this.corporateSource = new Source(
                this.corporateQueue,
                this.eventList,
                "CORPORATE_CALL_SOURCE",
                this.buildIAFromOldQueue(previousCorporateQueue, SimulationConfig.CORPORATE_ARRIVAL_RATE.sampleInterArrivalTimes()),
                ProductType.CORPORATE
        );
    }

    public void run() {
        new HourlyQueueRefreshSource(this.consumerQueue, this.eventList);
        new HourlyQueueRefreshSource(this.corporateQueue, this.eventList);

        this.initMorningShift();
        this.initNoonShift();
        this.initNightShift();

        // start the eventlist
        this.eventList.start();
    }

    protected double[] buildIAFromOldQueue(Queue oldQueue, double[] interArrivalTimes) {
        int oldQueueSize = oldQueue.getProducts().size();
        double[] finalTimes = new double[interArrivalTimes.length + oldQueueSize];

        for (int i = 0; i < oldQueueSize; i++) {
            finalTimes[i] = 0;
        }

        for (int i = oldQueueSize; i < finalTimes.length; i++) {
            finalTimes[i] = interArrivalTimes[i-oldQueueSize];
        }
        return finalTimes;
    }

    public void initMorningShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.MORNING)).build(SimulationConfig.MORNING_CORPORATE_AGENTS);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.MORNING)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CONSUMER, AgentShift.MORNING)).build(SimulationConfig.MORNING_CONSUMER_AGENTS);
    }


    public void initNoonShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NOON)).build(SimulationConfig.NOON_CORPORATE_AGENTS);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NOON)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CONSUMER, AgentShift.NOON)).build(SimulationConfig.NOON_CONSUMER_AGENTS);
    }


    public void initNightShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NIGHT)).build(SimulationConfig.NIGHT_CORPORATE_AGENTS);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NIGHT)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CONSUMER, AgentShift.NIGHT)).build(SimulationConfig.NIGHT_CONSUMER_AGENTS);
    }

    public Queue getConsumerQueue() {
        return consumerQueue;
    }

    public Queue getCorporateQueue() {
        return corporateQueue;
    }

    public Source getConsumerSource() {
        return consumerSource;
    }

    public Source getCorporateSource() {
        return corporateSource;
    }

    public Sink getSink() {
        return sink;
    }

    public CEventList getEventList() {
        return eventList;
    }
}
