/**
 * Example program for using eventlists
 *
 * @author Joel Karel
 * @version %I%, %G%
 */

package simulation2;

import configs.SimulationConfig;
import contracts.Distribution;
import models.ArrivalRate;
import simulation2.abstracts.AgentFactory;
import simulation2.factories.ConsumerCSAFactory;
import simulation2.factories.CorporateCSAFactory;
import simulation2.models.*;

public class Simulation {

    protected ArrivalRate consumerArrivalRate;
    protected ArrivalRate corporateArrivalRate;

    protected Distribution<Double> consumerServiceTimeDistribution;
    protected Distribution<Double> corporateServiceTimeDistribution;

    public Simulation(ArrivalRate consumerArrivalRate, ArrivalRate corporateArrivalRate, Distribution<Double> consumerServiceTimeDistribution, Distribution<Double> corporateServiceTimeDistribution) {
        this.consumerArrivalRate = consumerArrivalRate;
        this.corporateArrivalRate = corporateArrivalRate;
        this.consumerServiceTimeDistribution = consumerServiceTimeDistribution;
        this.corporateServiceTimeDistribution = corporateServiceTimeDistribution;
    }

    public void run() {

        // Create an eventlist
        CEventList l = new CEventList();

        // A queue for the machine
        Queue q = new Queue();

        double[] iaConsumers = this.consumerArrivalRate.sampleInterArrivalTimes();
        double[] iaCorporate= this.corporateArrivalRate.sampleInterArrivalTimes();

        new Source(
                q,
                l,
                "consumer_call_generator",
                iaConsumers,
                ProductType.CONSUMER
        );

        new Source(
                q,
                l,
                "corporate_call_generator",
                iaCorporate,
                ProductType.CORPORATE
        );

        // A sink
        Sink si = new Sink("Sink 1");

        AgentFactory corporateCSAFactory = new CorporateCSAFactory(q, si, l);
        corporateCSAFactory.build(0);

        AgentFactory consumerCSAFactory = new ConsumerCSAFactory(q, si, l);
        consumerCSAFactory.build(1000);


        // start the eventlist
        l.start(24 * 60 * 60);

        //  si.getTimes()
        System.out.println("------------------------------");
        System.out.println(q.remaining(ProductType.CORPORATE) + " corporate calls left in queue");
        System.out.println(q.remaining(ProductType.CONSUMER) + " consumer calls left in queue");
        System.out.println(q.remaining() + " total calls left in queue");

        String t = "test";
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Simulation sim = new Simulation(
                SimulationConfig.CONSUMER_ARRIVAL_RATE,
                SimulationConfig.CORPORATE_ARRIVAL_RATE,
                SimulationConfig.CONSUMER_SERVICE_DISTRIBUTION,
                SimulationConfig.CORPORATE_SERVICE_DISTRIBUTION
        );

        sim.run();
    }
}
