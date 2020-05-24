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

    protected Sink consumerSink = new Sink("CONSUMER_DATA");
    protected Sink corporateSink = new Sink("CORPORATE_DATA");

    protected final CEventList eventList = new CEventList();

    public SimulationTask() {

    }




    protected void transferQueues(Queue prevConsumerQueue, Queue prevCorporateQueue) {

        for (Product aProduct : prevConsumerQueue.getProducts()) {
            Product p = new Product(aProduct.type());
            p.stamp(aProduct.getTimes().get(0) - 86400.0, "Creation", "OLD_QUEUE_TRANSFER");
            this.consumerQueue.giveProduct(p);
        }

        for (Product aProduct : prevCorporateQueue.getProducts()) {
            Product p = new Product(aProduct.type());
            p.stamp(aProduct.getTimes().get(0) - 86400.0, "Creation", "OLD_QUEUE_TRANSFER");
            this.corporateQueue.giveProduct(p);
        }
    }


    public void run() {
        this.initSources();
        this.initShifts();

        // start the eventlist
        this.eventList.start();
    }

    protected void initSources(){
         new Source(
                this.consumerQueue,
                this.eventList,
                "CONSUMER_CALL_SOURCE",
                SimulationConfig.CONSUMER_ARRIVAL_RATE.sampleInterArrivalTimes(),
                ProductType.CONSUMER
        );

        new Source(
                this.corporateQueue,
                this.eventList,
                "CORPORATE_CALL_SOURCE",
                SimulationConfig.CORPORATE_ARRIVAL_RATE.sampleInterArrivalTimes(),
                ProductType.CORPORATE
        );

        new HourlyQueueRefreshSource(this.consumerQueue, this.eventList);
        new HourlyQueueRefreshSource(this.corporateQueue, this.eventList);
    }

    public void initShifts(){
        this.initMorningShift();
        this.initNoonShift();
        this.initNightShift();
    }

    public void initMorningShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.corporateSink, this.eventList, AgentType.CORPORATE, AgentShift.MORNING)).build(SimulationConfig.MORNING_CORPORATE_AGENTS);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.consumerSink, this.eventList, AgentType.CORPORATE, AgentShift.MORNING)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.consumerSink, this.eventList, AgentType.CONSUMER, AgentShift.MORNING)).build(SimulationConfig.MORNING_CONSUMER_AGENTS);

        String tesst = "";
    }


    public void initNoonShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.corporateSink, this.eventList, AgentType.CORPORATE, AgentShift.NOON)).build(SimulationConfig.NOON_CORPORATE_AGENTS);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.consumerSink, this.eventList, AgentType.CORPORATE, AgentShift.NOON)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.consumerSink, this.eventList, AgentType.CONSUMER, AgentShift.NOON)).build(SimulationConfig.NOON_CONSUMER_AGENTS);
    }


    public void initNightShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.corporateSink, this.eventList, AgentType.CORPORATE, AgentShift.NIGHT)).build(SimulationConfig.NIGHT_CORPORATE_AGENTS);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.consumerSink, this.eventList, AgentType.CORPORATE, AgentShift.NIGHT)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.consumerSink, this.eventList, AgentType.CONSUMER, AgentShift.NIGHT)).build(SimulationConfig.NIGHT_CONSUMER_AGENTS);
    }

    public Queue getConsumerQueue() {
        return consumerQueue;
    }

    public Queue getCorporateQueue() {
        return corporateQueue;
    }

    public Sink getConsumerSink() {
        return consumerSink;
    }

    public Sink getCorporateSink() {
        return corporateSink;
    }

    public CEventList getEventList() {
        return eventList;
    }
}
