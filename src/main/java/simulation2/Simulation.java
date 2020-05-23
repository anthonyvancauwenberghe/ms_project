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

public class Simulation {

    protected final Queue consumerQueue;
    protected final Queue corporateQueue;

    protected Sink sink;

    protected final CEventList eventList;

    public Simulation() {
        this.eventList = new CEventList();
        this.consumerQueue  = new Queue(this.eventList);
        this.corporateQueue = new Queue(this.eventList);
        this.sink = new Sink("data");
    }

    public void run() {
       this.initSources();

        this.initMorningShift();
        this.initNoonShift();
        this.initNightShift();

        this.warmUpQueue();


        // start the eventlist
        this.eventList.start();

        //  si.getTimes()
        System.out.println("------------------------------");
        System.out.println(corporateQueue.remaining() + " corporate calls left in queue");
        System.out.println(consumerQueue.remaining() + " consumer calls left in queue");
        System.out.println(consumerQueue.remaining() + corporateQueue.remaining() + " total calls left in queue");

        String t = "test";
    }

    public void initSources() {
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
    }

    public void warmUpQueue(){
        for(int i=0; i<100; i++){
            this.eventList.start();
            this.eventList.clear();
        }

        this.sink = new Sink("data");

    }


    public void initMorningShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.MORNING)).build(2);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.MORNING)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CONSUMER, AgentShift.MORNING)).build(4);
    }


    public void initNoonShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NOON)).build(2);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NOON)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CONSUMER, AgentShift.NOON)).build(4);
    }


    public void initNightShift() {
        //CORPORATE WORKERS ASSIGNED TO THE CORPORATE QUEUE
        (new AgentFactory(this.corporateQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NIGHT)).build(2);

        //CORPORATE WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CORPORATE, AgentShift.NIGHT)).build(0);

        //CONSUMER WORKERS ASSIGNED TO THE CONSUMER QUEUE
        (new AgentFactory(this.consumerQueue, this.sink, this.eventList, AgentType.CONSUMER, AgentShift.NIGHT)).build(4);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Simulation sim = new Simulation(

        );

        sim.run();
    }
}
