import analysis.SimulationAnalysis;
import analysis.SinkAnalysisAggregator;
import calculators.AgentDailyCostCalculator;
import configs.AnalysisConfig;
import configs.DefaultSimConfig;
import configs.SimulationConfig;
import contracts.ISimulationConfig;
import simulation.QueueWarmup;
import simulation.Simulator;

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
        if (this.queueWarmup) {
            this.warmUpQueue();
        }

        System.out.println("Starting simulation..");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //log start time of simulation
        this.simulationStartTime = System.nanoTime();

        for (int i = 0; i < this.config.getIterations(); i++) {

            //Start a new simulation and run it
            Simulator sim = new Simulator(config);
            sim.run();

            //Transfer over the queue from the last iteration to the new simulation
            this.config.setQueues(sim.getProcessor().getQueues());

            //Adding the data for a simulation day to the analysis aggregator
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
        new QueueWarmup(this.config).process();
    }

    public void analysis() {
        System.out.println("");
        System.out.println("Analyzing Data...");

        //log startime of analysis
        long startTime = System.nanoTime();

        new SimulationAnalysis(this.consumer, this.corporate).execute();

        //print total analysis time
        System.out.println("");
        System.out.println("Analysis took " +
                new DecimalFormat("##.00").format((((double) (System.nanoTime() - startTime)) / 1000000000)) +
                " seconds");
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
        (new Simulation(new DefaultSimConfig(), AnalysisConfig.PERFORM_ANALYSIS, SimulationConfig.WAMRUP_QUEUE)).run();
    }

}
