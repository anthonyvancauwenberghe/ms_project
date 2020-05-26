import analysis.SinkAnalysisAggregator;
import calculators.AgentDailyCostCalculator;
import configs.DefaultSimConfig;
import configs.SimulationConfig;
import contracts.ISimulationConfig;

import java.text.DecimalFormat;

public class Simulation {

    protected ISimulationConfig config;

    protected boolean queueWarmup = true;

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

    public Simulation(ISimulationConfig config, boolean analysis, boolean warmupQueue) {
        this.config = config;
        this.analysis = analysis;
        this.queueWarmup = warmupQueue;
    }

    public void run() {

        //log start time of simulation
        this.simulationStartTime = System.nanoTime();

        if (this.queueWarmup)
            this.warmUpQueue();

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

    public void warmUpQueue() {

        System.out.println("Warming up queue ..");
        ISimulationConfig warmupConfig = this.config;

        Simulator sim = new Simulator(config);

        int maxConsumerQueue = 0;
        int maxCorporateQueue = 0;

        int countConsumerQueueMaxLastChanged = 0;
        int countCorporateQueueMaxLastChanged = 0;

        int currentConsumerQueue, currentCorporateQueue;

        int counter = 0;
        boolean reachedSteadyState = false;
        for (int i = 0; i < this.config.getQueueWarmupIterations(); i++) {

            //Start a new simulation and run it
            sim = new Simulator(config);
            sim.run();

            counter++;

            currentConsumerQueue = sim.getProcessor().getQueues()[0].count();
            currentCorporateQueue = sim.getProcessor().getQueues()[0].count();

            if (currentConsumerQueue > maxConsumerQueue) {
                maxConsumerQueue = currentConsumerQueue;
                countConsumerQueueMaxLastChanged = 0;
            } else {
                countConsumerQueueMaxLastChanged++;
            }

            if (currentCorporateQueue > maxCorporateQueue) {
                maxCorporateQueue = currentCorporateQueue;
                countCorporateQueueMaxLastChanged = 0;
            } else {
                countCorporateQueueMaxLastChanged++;
            }

            if (countConsumerQueueMaxLastChanged > 50 && countCorporateQueueMaxLastChanged > 50) {
                reachedSteadyState = true;
                break;
            }


            //Transfer over the queue from the last iteration to the new simulation
            warmupConfig.setQueues(sim.getProcessor().getQueues());
        }
        if (sim.isExecuted())
            this.config.setQueues(sim.getProcessor().getQueues());

        System.out.println("Warming up queue finished. ");

        if (reachedSteadyState)
            System.out.println("Ran " + counter + " warmup iterations. Reached a steady state after " + (counter - 50) + " iterations");
        else
            System.out.println("Could not reach a steady state. Stopped after " + counter + " iterations");

        System.out.println("Starting simulation..");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void analysis() {
        System.out.println("");
        System.out.println("Analyzing Data...");

        //log startime of analysis
        long startTime = System.nanoTime();

        consumer.plotAvgMinutelyQueueTimesWithConfidence(0.99);
        corporate.plotAvgMinutelyQueueTimesWithConfidence(0.99);

        consumer.plotAvgMinutelyArrivals();
        corporate.plotAvgMinutelyArrivals();
/*
        consumer.plotAvgMinutelyQueueTimes();
        corporate.plotAvgMinutelyQueueTimes();*/



/*        consumer.plotAvgMinutelyQueueTimes();
        corporate.plotAvgMinutelyQueueTimes();*/

/*        consumer.plotAvgMinutelyArrivals();
        corporate.plotAvgMinutelyArrivals();*/

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

        double confidence = 0.99;
        String confidenceString = confidence * 100 + "% ";
        double[] fiveMinuteProbability = consumer.avgProbabilityQueueTimeLessThanWithConfidence(5.0 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);
        double[] tenMinuteProbability = consumer.avgProbabilityQueueTimeLessThanWithConfidence(10.0 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);

        System.out.println("");
        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("CONSUMER QUEUE TIME PROBABILITIES");
        System.out.println("--------------------------");
        System.out.println("sample mean 5 min: " + new DecimalFormat("##.00").format(fiveMinuteProbability[2] * 100) + " % - " + (fiveMinuteProbability[2] >= 0.9 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "lower 5 min: " + new DecimalFormat("##.00").format(fiveMinuteProbability[0] * 100) + " % - " + (fiveMinuteProbability[0] >= 0.9 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "upper 5 min: " + new DecimalFormat("##.00").format(fiveMinuteProbability[1] * 100) + " % - " + (fiveMinuteProbability[1] >= 0.9 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");

        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("sample mean 10 min: " + new DecimalFormat("##.00").format(tenMinuteProbability[2] * 100) + " % - " + (tenMinuteProbability[2] >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "lower 10 min: " + new DecimalFormat("##.00").format(tenMinuteProbability[0] * 100) + " % - " + (tenMinuteProbability[0] >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "upper 10 min: " + new DecimalFormat("##.00").format(tenMinuteProbability[1] * 100) + " % - " + (tenMinuteProbability[1] >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");
        System.out.println("");
        System.out.println("");

        double[] threeMinuteProbability = corporate.avgProbabilityQueueTimeLessThanWithConfidence(3.0 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);
        double[] sevenMinuteProbability = corporate.avgProbabilityQueueTimeLessThanWithConfidence(7.0 * 60, 0, SimulationConfig.SIMULATION_RUNTIME, confidence);

        System.out.println("--------------------------");
        System.out.println("CORPORATE QUEUE TIME PROBABILITIES");
        System.out.println("--------------------------");
        System.out.println("sample mean 3 min: " + new DecimalFormat("##.00").format(threeMinuteProbability[2] * 100) + " % - " + (threeMinuteProbability[2] >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "lower 3 min: " + new DecimalFormat("##.00").format(threeMinuteProbability[0] * 100) + " % - " + (threeMinuteProbability[0] >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "upper 3 min: " + new DecimalFormat("##.00").format(threeMinuteProbability[1] * 100) + " % - " + (threeMinuteProbability[1] >= 0.95 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");

        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("sample mean 7 min: " + new DecimalFormat("##.00").format(sevenMinuteProbability[2] * 100) + " % - " + (sevenMinuteProbability[2] >= 0.99 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "lower 7 min: " + new DecimalFormat("##.00").format(sevenMinuteProbability[0] * 100) + " % - " + (sevenMinuteProbability[0] >= 0.99 ? "SATISFIED" : "FAILED"));
        System.out.println(confidenceString + "upper 7 min: " + new DecimalFormat("##.00").format(sevenMinuteProbability[1] * 100) + " % - " + (sevenMinuteProbability[1] >= 0.99 ? "SATISFIED" : "FAILED"));
        System.out.println("--------------------------");
        System.out.println("");
        System.out.println("");
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
