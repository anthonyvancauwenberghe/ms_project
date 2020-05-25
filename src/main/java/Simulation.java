import analysis.SinkAnalysisAggregator;
import calculators.AgentDailyCostCalculator;
import charts.LineChart;
import configs.DefaultSimConfig;
import configs.SimulationConfig;
import contracts.ISimulationConfig;

import java.text.DecimalFormat;

public class Simulation {

    protected ISimulationConfig config;

    protected SinkAnalysisAggregator consumer = new SinkAnalysisAggregator("Consumer");
    protected SinkAnalysisAggregator corporate = new SinkAnalysisAggregator("Corporate");

    protected long simulationStartTime;
    protected long simulationEndTime;

    protected boolean analysis = false;

    public Simulation(ISimulationConfig config) {
        this.config = config;
    }

    public Simulation(ISimulationConfig config, boolean analysis) {
        this.config = config;
        this.analysis = analysis;
    }

    public void run() {

        //log start time of simulation
        this.simulationStartTime = System.nanoTime();

        for (int i = 0; i < this.config.getIterations(); i++) {

            //Start a new simulation and run it
            Simulator sim = new Simulator(config);
            sim.run();

            //Transfer over the queue from the last iteration to the new simulation
            this.config.setQueues(sim.getProcessor().getQueues());

            //Adding the iteration data to the analysis aggregator
            this.consumer.add(sim.consumerAnalysis());
            this.corporate.add(sim.corporateAnalysis());

            System.out.println("Finished iteration: " + i);
        }

        //log end time of simulation
        this.simulationEndTime = System.nanoTime();
        this.printSimulationInfo();

        if (this.analysis)
            this.analysis();

        System.out.println("Total daily call center cost: " + AgentDailyCostCalculator.totalCost() + "€");
    }


    public void analysis() {
        System.out.println("");
        System.out.println("Analyzing Data...");

        //log startime of analysis
        long startTime = System.nanoTime();


        consumer.plotAvgMinutelyQueueTimes();
        corporate.plotAvgMinutelyQueueTimes();

/*
        consumer.plotAvgMinutelyArrivals();
        corporate.plotAvgMinutelyArrivals();



        consumer.plotQueueTimeProbabilities();
        corporate.plotQueueTimeProbabilities();

        consumer.plotAvgHourlyArrivals();
        corporate.plotAvgHourlyQueueTimes();
        corporate.plotAvgHourlyArrivals()

        */

        //print total analysis time
        System.out.println("");
        System.out.println("Analysis took " +
                new DecimalFormat("##.00").format((((double) (System.nanoTime() - startTime)) / 1000000000)) +
                " seconds");

        double fiveMinuteProbability = consumer.avgProbabilityQueueTimeLessThan(5.0 * 60);
        double tenMinuteProbability = consumer.avgProbabilityQueueTimeLessThan(10.0 * 60);
        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("CONSUMER QUEUE TIME PROBABILITIES");
        System.out.println("5 min: " + new DecimalFormat("##.00").format(fiveMinuteProbability * 100) + " % - " + (fiveMinuteProbability >= 0.9 ? "SATISFIED" : "FAILED"));
        System.out.println("10 min: " + new DecimalFormat("##.00").format(tenMinuteProbability * 100) + " % - " + (tenMinuteProbability >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");
        System.out.println("");


        double threeMinuteProbability = corporate.avgProbabilityQueueTimeLessThan(3.0 * 60);
        double sevenMinuteProbability = corporate.avgProbabilityQueueTimeLessThan(7.0 * 60);
        System.out.println("--------------------------");
        System.out.println("CORPORATE QUEUE TIME PROBABILITIES");
        System.out.println("3 min: " + new DecimalFormat("##.00").format(threeMinuteProbability * 100) + " % - " + (threeMinuteProbability >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println("7 min: " + new DecimalFormat("##.00").format(sevenMinuteProbability * 100) + " % - " + (sevenMinuteProbability >= 0.99 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");
    }

    public void printSimulationInfo() {
        //print total simulation time
        System.out.println("");
        System.out.println("Ran " +
                SimulationConfig.SIMULATION_COUNT + " simulations in " +
                new DecimalFormat("##.00").format((((double) (this.simulationEndTime - this.simulationStartTime)) / 1000000000)) +
                " seconds");
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //initialize a default simulation config (the simulation will run with the parameters defined in the config files)
        (new Simulation(new DefaultSimConfig(), true)).run();
    }


}
