package simulation;

import contracts.ISimulationConfig;

public class QueueWarmup {
    protected ISimulationConfig config;

    public QueueWarmup(ISimulationConfig config) {
        this.config = config;
    }

    public void process(){

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
    }
}
